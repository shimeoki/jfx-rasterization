package io.github.shimeoki.jfx.rasterization.triangle.color;

import io.github.shimeoki.jfx.rasterization.color.Colorf;

/**
 * An interface that represents a gradient for the potential
 * {@link TriangleColorer} implementations.
 *
 * <p>
 * Basically, it's just a container for three color values
 * <p>
 * Intended for usage with gradients or textures.
 * <p>
 * For gradients, each color represents the color at each vertex, and the
 * colors are changed based on the distance from each vertex.
 * <p>
 * For textures, some kind of UV mapping conversion can be made, if the texture
 * is interpolated between the vertices.
 *
 * @since 1.0.0
 *
 * @see TriangleColorer
 * @see StaticTriangleGradient
 * @see DynamicTriangleGradient
 */
public interface TriangleGradient {

    /**
     * Gets the first color of the gradient.
     *
     * @return the first color of the gradient
     *
     * @since 1.0.0
     */
    public Colorf color1();

    /**
     * Gets the second color of the gradient.
     *
     * @return the second color of the gradient
     *
     * @since 1.0.0
     */
    public Colorf color2();

    /**
     * Gets the third color of the gradient.
     *
     * @return the third color of the gradient
     *
     * @since 1.0.0
     */
    public Colorf color3();
}
