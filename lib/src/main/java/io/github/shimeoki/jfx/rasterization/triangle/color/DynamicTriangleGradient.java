package io.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.color.Colorf;

/**
 * One of the default {@link TriangleGradient} implementations with mutable
 * colors.
 *
 * <p>
 * It's called "dynamic", because the gradient will change, if the
 * original colors, passed to the constructor, will change.
 * <p>
 * Does store the references to the original colors on creation. Getters of the
 * colors return the same reference everytime.
 * <p>
 * If you need an opposite behaviour (gradient is "frozen" in place), take a
 * look at the {@link StaticTriangleGradient}.
 *
 * @since 1.0.0
 *
 * @see TriangleGradient
 * @see StaticTriangleGradient
 */
public final class DynamicTriangleGradient implements TriangleGradient {

    private final Colorf color1;
    private final Colorf color2;
    private final Colorf color3;

    /**
     * Creates a new {@code DynamicTriangleGradient} instance.
     *
     * <p>
     * All principles of the internal usage are described in the javadoc of the
     * class. It's advised to read it before use.
     *
     * @param c1 first color of the gradient
     * @param c2 second color of the gradient
     * @param c3 third color of the gradient
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 1.0.0
     */
    public DynamicTriangleGradient(
            final Colorf c1, final Colorf c2, final Colorf c3) {

        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        color1 = c1;
        color2 = c2;
        color3 = c3;
    }

    @Override
    public Colorf color1() {
        return color1;
    }

    @Override
    public Colorf color2() {
        return color2;
    }

    @Override
    public Colorf color3() {
        return color3;
    }
}
