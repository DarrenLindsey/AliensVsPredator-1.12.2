package org.avp.block.conveyor.spline;

import net.minecraft.world.phys.Vec3;

/**
 * A cubic Bezier from a start point leaving along one direction to an end point arriving along
 * another, with an arc-length table so things can move along it at a steady speed.
 */
public final class Spline
{
    private static final int SAMPLES = 48;
    private final Vec3 p0, p1, p2, p3;
    private final double[] lengths = new double[SAMPLES + 1];
    public final double length;

    public Spline(Vec3 start, Vec3 startDir, Vec3 end, Vec3 endDir)
    {
        double d = start.distanceTo(end);
        double pull = Math.max(0.5, d / 3);
        this.p0 = start;
        this.p1 = start.add(startDir.normalize().scale(pull));
        this.p2 = end.subtract(endDir.normalize().scale(pull));
        this.p3 = end;
        Vec3 last = p0;
        double total = 0;
        for (int i = 1; i <= SAMPLES; i++)
        {
            Vec3 p = at((double) i / SAMPLES);
            total += p.distanceTo(last);
            lengths[i] = total;
            last = p;
        }
        this.length = total;
    }

    private Spline(Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, double[] lengths, double length)
    {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        System.arraycopy(lengths, 0, this.lengths, 0, lengths.length);
        this.length = length;
    }

    /** The same curve, moved up or down. */
    public Spline offset(double dy)
    {
        Vec3 d = new Vec3(0, dy, 0);
        return new Spline(p0.add(d), p1.add(d), p2.add(d), p3.add(d), lengths, length);
    }

    /** The point at parameter t (0..1). */
    public Vec3 at(double t)
    {
        double u = 1 - t;
        return p0.scale(u * u * u).add(p1.scale(3 * u * u * t)).add(p2.scale(3 * u * t * t)).add(p3.scale(t * t * t));
    }

    /** The direction of travel at parameter t. */
    public Vec3 tangent(double t)
    {
        double u = 1 - t;
        Vec3 d = p1.subtract(p0).scale(3 * u * u).add(p2.subtract(p1).scale(6 * u * t)).add(p3.subtract(p2).scale(3 * t * t));
        return d.lengthSqr() < 1e-8 ? p3.subtract(p0).normalize() : d.normalize();
    }

    /** The parameter for a distance along the spline. */
    public double param(double distance)
    {
        if (distance <= 0)
        {
            return 0;
        }
        if (distance >= length)
        {
            return 1;
        }
        int lo = 0, hi = SAMPLES;
        while (hi - lo > 1)
        {
            int mid = (lo + hi) / 2;
            if (lengths[mid] < distance)
            {
                lo = mid;
            }
            else
            {
                hi = mid;
            }
        }
        double seg = lengths[hi] - lengths[lo];
        double f = seg <= 0 ? 0 : (distance - lengths[lo]) / seg;
        return (lo + f) / SAMPLES;
    }

    public Vec3 pointAt(double distance)
    {
        return at(param(distance));
    }

    /** The steepest climb along it, in degrees. */
    public double steepest()
    {
        double max = 0;
        for (int i = 0; i <= 16; i++)
        {
            Vec3 t = tangent(i / 16.0);
            max = Math.max(max, Math.toDegrees(Math.asin(Math.min(1, Math.abs(t.y)))));
        }
        return max;
    }
}
