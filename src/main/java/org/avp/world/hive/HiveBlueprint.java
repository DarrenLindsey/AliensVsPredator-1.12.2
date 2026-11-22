package org.avp.world.hive;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

/** Top-down, one-block-per-pixel floor plan, anchored by its shaft pixel. */
public final class HiveBlueprint
{
    private static final int MAX_SIDE = 48;
    private static final int ROOM_HEIGHT = 5;
    private static final int MIGRATION_HEIGHT = 8;
    private final int width;
    private final String cells;
    private final BlockPos shaft;
    private final BlockPos core;
    private final List<Tile> tiles;

    private HiveBlueprint(int width, String cells)
    {
        this.width = width;
        this.cells = cells;
        shaft = at(cells.indexOf('S'));
        core = at(cells.indexOf('C'));
        tiles = IntStream.range(0, cells.length()).filter(i -> cells.charAt(i) != '.')
                .mapToObj(i -> new Tile(at(i).subtract(shaft), cells.charAt(i))).toList();
    }

    public static HiveBlueprint read(InputStream png) throws IOException
    {
        try (MemoryCacheImageInputStream image = new MemoryCacheImageInputStream(png))
        {
            var readers = ImageIO.getImageReadersByFormatName("png");
            var reader = readers.next();
            try
            {
                reader.setInput(image);
                int w = reader.getWidth(0);
                int h = reader.getHeight(0);
                if (w < 5 || h < 5 || w > MAX_SIDE || h > MAX_SIDE)
                {
                    throw new IllegalArgumentException("Blueprint PNGs must be 5–48 pixels on each side.");
                }
                var pixels = reader.read(0);
                StringBuilder mask = new StringBuilder(w * h);
                for (int z = 0; z < h; z++)
                {
                    for (int x = 0; x < w; x++)
                    {
                        int rgb = pixels.getRGB(x, z);
                        char cell = switch (rgb)
                        {
                            case 0xff000000 -> '.';
                            case 0xffffffff -> 'P';
                            case 0xff800080 -> 'Q';
                            case 0xff008000 -> 'H';
                            case 0xff804000 -> 'L';
                            case 0xffff0000 -> 'C';
                            case 0xff0000ff -> 'S';
                            default -> throw new IllegalArgumentException("Unknown or transparent color at pixel " + x + ", " + z + ".");
                        };
                        mask.append(cell);
                    }
                }
                String data = mask.toString();
                if (data.indexOf('S') < 0 || data.indexOf('S') != data.lastIndexOf('S')
                        || data.indexOf('C') < 0 || data.indexOf('C') != data.lastIndexOf('C'))
                {
                    throw new IllegalArgumentException("Use exactly one blue shaft pixel and one red Core pixel.");
                }
                HiveBlueprint plan = new HiveBlueprint(w, data);
                plan.validate();
                return plan;
            }
            finally
            {
                reader.dispose();
            }
        }
    }

    public static HiveBlueprint named(String name) throws IOException
    {
        if (!validName(name))
        {
            throw new IllegalArgumentException("Use a blueprint name with letters, numbers, underscores or hyphens.");
        }
        Path path = HiveBlueprintLibrary.folder().resolve(name + ".png");
        if (Files.exists(path))
        {
            return readFile(path);
        }
        try (InputStream png = HiveBlueprintLibrary.bundled(name))
        {
            return read(png);
        }
    }

    static boolean validName(String name)
    {
        return name.matches("[a-zA-Z0-9_-]{1,48}");
    }

    static HiveBlueprint readFile(Path path) throws IOException
    {
        if (!Files.isRegularFile(path) || Files.size(path) > 262144)
        {
            throw new IllegalArgumentException("Expected a PNG file no larger than 256 KB.");
        }
        try (InputStream png = Files.newInputStream(path))
        {
            return read(png);
        }
    }

    private void validate()
    {
        Set<BlockPos> connected = flood(false, shaft);
        long open = cells.chars().filter(c -> c != '.').count();
        if (connected.size() != open)
        {
            throw new IllegalArgumentException("Every colored pixel must connect to the shaft by a shared edge.");
        }
        if (!wide(shaft) || !flood(true, shaft).contains(core))
        {
            throw new IllegalArgumentException("Keep a five-block-wide route from the shaft to the Core, including bends.");
        }
        if (connected.stream().noneMatch(p -> cell(p) == 'Q')
                || Direction.Plane.HORIZONTAL.stream().noneMatch(d -> cell(core.relative(d)) == 'Q'))
        {
            throw new IllegalArgumentException("Put the red Core pixel inside the purple Queen chamber.");
        }
    }

    private Set<BlockPos> flood(boolean wide, BlockPos start)
    {
        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty())
        {
            BlockPos point = queue.removeFirst();
            for (Direction d : Direction.Plane.HORIZONTAL)
            {
                BlockPos next = point.relative(d);
                if (cell(next) != '.' && (!wide || wide(next)) && visited.add(next))
                {
                    queue.addLast(next);
                }
            }
        }
        return visited;
    }

    private boolean wide(BlockPos p)
    {
        return HiveShaft.layer(p).stream().noneMatch(pos -> cell(pos) == '.' || height(cell(pos)) < MIGRATION_HEIGHT);
    }

    private BlockPos at(int index)
    {
        return new BlockPos(index % width, 0, index / width);
    }

    private char cell(BlockPos p)
    {
        return p.getX() >= 0 && p.getX() < width && p.getZ() >= 0 && p.getZ() < cells.length() / width
                ? cells.charAt(p.getZ() * width + p.getX()) : '.';
    }

    public BlockPos toWorld(BlockPos point, BlockPos shaftFloor)
    {
        return shaftFloor.offset(point.subtract(shaft));
    }

    public BlockPos core(BlockPos shaftFloor)
    {
        return toWorld(core, shaftFloor);
    }

    boolean contains(BlockPos worldPos, BlockPos shaftFloor)
    {
        BlockPos column = worldPos.subtract(shaftFloor).offset(shaft);
        char kind = cell(column);
        return kind != '.' && column.getY() >= 0 && column.getY() < height(kind);
    }

    public record Tile(BlockPos offset, char kind)
    {
        public int color()
        {
            return switch (kind)
            {
                case 'S' -> 0x3377ff;
                case 'C' -> 0xff2222;
                case 'Q' -> 0xbb55dd;
                case 'H' -> 0x33cc66;
                case 'L' -> 0xb87838;
                default -> 0xffffff;
            };
        }
    }

    private static int height(char kind)
    {
        return kind == 'H' || kind == 'L' ? ROOM_HEIGHT : MIGRATION_HEIGHT;
    }

    public List<Tile> tiles()
    {
        return tiles;
    }

    private Stream<BlockPos> excavation(BlockPos floor)
    {
        return tiles.stream().flatMap(tile -> {
            BlockPos column = floor.offset(tile.offset());
            return IntStream.range(0, height(tile.kind())).mapToObj(column::above);
        });
    }

    Iterator<BlockPos> blocksAt(BlockPos floor)
    {
        return excavation(floor).iterator();
    }

    boolean withinBudget(int depth)
    {
        int shaftBlocks = depth * 25;
        return shaftBlocks + tiles.stream().mapToInt(tile -> height(tile.kind())).sum() <= HivePlanner.maxDug();
    }

    static boolean canCarve(ServerLevel level, BlockPos pos)
    {
        return HiveShaft.canExcavate(level, pos)
                && (level.getBlockState(pos).isAir() || HivePlanner.diggable(level, pos));
    }

    boolean hasCoreFloor(ServerLevel level, BlockPos floor)
    {
        BlockPos pos = core(floor).below();
        return level.isLoaded(pos) && Hive.isCoreFloor(level, pos, level.getBlockState(pos));
    }

    public void checkSite(ServerLevel level, BlockPos mouth, int depth)
    {
        checkDepth(level, mouth, depth);
        BlockPos floor = mouth.below(depth);
        if (!withinBudget(depth))
        {
            throw new IllegalArgumentException("The plan plus shaft exceeds the hive excavation limit.");
        }
        for (Iterator<BlockPos> cut = blocksAt(floor); cut.hasNext();)
        {
            verifyCarvable(level, cut.next());
        }
        for (int y = 1; y <= depth; y++)
        {
            for (BlockPos pos : HiveShaft.layer(mouth.below(y)))
            {
                verifyCarvable(level, pos);
            }
        }
        if (!hasCoreFloor(level, floor))
        {
            throw new IllegalArgumentException("The Core needs solid terrain below its marked floor.");
        }
    }

    private static void verifyCarvable(ServerLevel level, BlockPos pos)
    {
        if (!canCarve(level, pos))
        {
            throw new IllegalArgumentException("Unloaded, protected or flooded terrain at " + pos.toShortString() + ".");
        }
    }

    public static int defaultDepth(ServerLevel level, BlockPos mouth)
    {
        return mouth.getY() - HiveShaft.targetFloorY(level, mouth.getY() - 12, MIGRATION_HEIGHT);
    }

    public static void checkDepth(ServerLevel level, BlockPos mouth, int depth)
    {
        int floorY = mouth.getY() - depth;
        if (!HiveShaft.inHiveLayer(level, floorY, MIGRATION_HEIGHT))
        {
            throw new IllegalArgumentException("New Overworld hives must fit below Y 0. Omit the depth to use the deepslate layer automatically.");
        }
        if (floorY <= level.getMinBuildHeight())
        {
            throw new IllegalArgumentException("This depth leaves no ground below the hive floor.");
        }
    }

    List<Room> rooms(BlockPos floor)
    {
        List<Room> rooms = new ArrayList<>();
        for (char kind : new char[] { 'Q', 'H', 'L' })
        {
            Set<BlockPos> columns = new HashSet<>();
            for (Tile tile : tiles())
            {
                if (tile.kind() == kind || kind == 'Q' && tile.kind() == 'C')
                {
                    columns.add(floor.offset(tile.offset()));
                }
            }
            if (!columns.isEmpty())
            {
                double x = columns.stream().mapToInt(BlockPos::getX).average().orElseThrow();
                double z = columns.stream().mapToInt(BlockPos::getZ).average().orElseThrow();
                BlockPos centre = kind == 'Q' ? core(floor) : columns.stream().min(java.util.Comparator.comparingDouble(
                        p -> p.distToCenterSqr(x + 0.5, floor.getY() + 0.5, z + 0.5))).orElseThrow();
                Room.Type type = kind == 'Q' ? Room.Type.QUEEN : kind == 'H' ? Room.Type.HATCHERY : Room.Type.LARDER;
                rooms.add(Room.blueprint(type, centre, height(kind), columns));
            }
        }
        return rooms;
    }

    CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Width", width);
        tag.putString("Cells", cells);
        return tag;
    }

    static HiveBlueprint load(CompoundTag tag)
    {
        return new HiveBlueprint(tag.getInt("Width"), tag.getString("Cells"));
    }
}
