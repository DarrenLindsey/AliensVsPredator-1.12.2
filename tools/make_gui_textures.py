"""Builds the 1.21 menu backgrounds for the locker and supply crate from the 1.12.2 images.

The old menus had an 8x8 storage grid but only your hotbar below it. These keep the old
header and grid, and add a full player inventory (3 rows + hotbar) underneath in the same style.
Layout (must match org.avp.menu.StorageMenu): storage slots at x=14+18*col, y=26+18*row;
player inventory at x=8+18*col, y=184+18*row; hotbar at y=242. Size 176 x 266.

Run from the repo root:  python tools/make_gui_textures.py
"""
from PIL import Image

OLD = "legacy-1.12.2/src/main/resources/assets/avp/textures/gui"
NEW = "src/main/resources/assets/avp/textures/gui"
W, H = 176, 266


def build(src_name, dst_name):
    old = Image.open(f"{OLD}/{src_name}").convert("RGBA")
    out = Image.new("RGBA", (256, 512), (0, 0, 0, 0))
    out.paste(old.crop((0, 0, W, 178)), (0, 0))            # header, border and 8x8 grid
    filler = old.crop((0, 170, W, 178))                     # plain panel rows (with side borders)
    for y in range(178, H - 3, filler.height):
        out.paste(filler, (0, y))
    out.paste(old.crop((0, 199, W, 202)), (0, H - 3))       # bottom border
    cell = old.crop((13, 25, 31, 43))                       # one 18x18 slot graphic from the old grid
    for row in range(3):
        for col in range(9):
            out.paste(cell, (8 + 18 * col - 1, 184 + 18 * row - 1))
    for col in range(9):
        out.paste(cell, (8 + 18 * col - 1, 242 - 1))
    out.save(f"{NEW}/{dst_name}")


if __name__ == "__main__":
    import os
    os.makedirs(NEW, exist_ok=True)
    build("locker.png", "locker.png")
    build("supplycrate.png", "supplycrate.png")
    print("menu textures written")
