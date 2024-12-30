package io.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.Colorf;
import io.github.shimeoki.jfx.rasterization.Floats;
import io.github.shimeoki.jfx.rasterization.triangle.TriangleBarycentrics;

/**
 * One of the standard implementations of {@link TriangleFiller} to fill the
 * triangles with a gradient from three colors.
 *
 * <p>
 * Linearly interpolates the color based on the barycentric coordinates. The
 * colors are numbered and mapped to the corresponding numbered coordinates of
 * the barycentrics.
 *
 * @since 2.0.0
 *
 * @see TriangleFiller
 * @see Colorf
 */
public final class GradientTriangleFiller implements TriangleFiller {

    private final Colorf color1, color2, color3;

    private float lambda1, lambda2, lambda3;

    private float red1, red2, red3;
    private float green1, green2, green3;
    private float blue1, blue2, blue3;
    private float alpha1, alpha2, alpha3;

    /**
     * Creates a new {@link GradientTriangleFiller} instance.
     *
     * @param c1 first color of the gradient
     * @param c2 second color of the gradient
     * @param c3 third color of the gradient
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 2.0.0
     */
    public GradientTriangleFiller(
            final Colorf c1, final Colorf c2, final Colorf c3) {

        color1 = Objects.requireNonNull(c1);
        color2 = Objects.requireNonNull(c2);
        color3 = Objects.requireNonNull(c3);
    }

    private float red() {
        red1 = lambda1 * color1.red();
        red2 = lambda2 * color2.red();
        red3 = lambda3 * color3.red();

        return Floats.confined(0, red1 + red2 + red3, 1);
    }

    private float green() {
        green1 = lambda1 * color1.green();
        green2 = lambda2 * color2.green();
        green3 = lambda3 * color3.green();

        return Floats.confined(0, green1 + green2 + green3, 1);
    }

    private float blue() {
        blue1 = lambda1 * color1.blue();
        blue2 = lambda2 * color2.blue();
        blue3 = lambda3 * color3.blue();

        return Floats.confined(0, blue1 + blue2 + blue3, 1);
    }

    private float alpha() {
        alpha1 = lambda1 * color1.alpha();
        alpha2 = lambda2 * color2.alpha();
        alpha3 = lambda3 * color3.alpha();

        return Floats.confined(0, alpha1 + alpha2 + alpha3, 1);
    }

    @Override
    public Colorf color(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        lambda1 = b.lambda1();
        lambda2 = b.lambda2();
        lambda3 = b.lambda3();

        return new Colorf(red(), green(), blue(), alpha());
    }
}
