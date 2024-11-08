package com.github.shimeoki.jfx.rasterization.color;

/**
 * Static (non-instantiable) class with some colors as {@code Colorf} from the
 * HTML specification as constants.
 *
 * <p>
 * Data for the colors is from
 * <a href="https://htmlcolorcodes.com/color-names/">HTML Color Codes</a>.
 *
 * <p>
 * All constants use {@code RGBColorf} implementation.
 *
 * @author shimeoki
 * @since 1.0.0
 *
 * @see Colorf
 * @see RGBColorf
 */
public final class HTMLColorf {

    // special

    /**
     * Equivalent to rgb(255, 255, 255 / 0) or #FFFFFF00.
     */
    public static final Colorf TRANSPARENT_WHITE = new RGBColorf(1f, 1f, 1f, 0f);

    /**
     * Equivalent to rgb(0, 0, 0 / 0) or #00000000.
     */
    public static final Colorf TRANSPARENT_BLACK = new RGBColorf(0f, 0f, 0f, 0f);

    // red

    /**
     * Equivalent to rgb(255, 0, 0) or #FF0000.
     */
    public static final Colorf RED = new RGBColorf(1f, 0f, 0f, 1f);

    // pink

    // ...

    // orange

    /**
     * Equivalent to rgb(255, 165, 0) or #FFA500.
     */
    public static final Colorf ORANGE = new RGBColorf(1f, 0.65f, 0f, 1f);

    // yellow

    /**
     * Equivalent to rgb(255, 255, 0) or #FFFF00.
     */
    public static final Colorf YELLOW = new RGBColorf(1f, 1f, 0f, 1f);

    // purple

    /**
     * Equivalent to rgb(255, 0, 255) or #FF00FF.
     */
    public static final Colorf FUCHSIA = new RGBColorf(1f, 0f, 1f, 1f);

    /**
     * Equivalent to rgb(128, 0, 128) or #800080.
     */
    public static final Colorf PURPLE = new RGBColorf(0.5f, 0f, 0.5f, 1f);

    // green

    /**
     * Equivalent to rgb(0, 255, 0) or #00FF00.
     */
    public static final Colorf LIME = new RGBColorf(0f, 0.5f, 0f, 1f);

    /**
     * Equivalent to rgb(0, 128, 0) or #008000.
     */
    public static final Colorf GREEN = new RGBColorf(0f, 1f, 0f, 1f);

    // blue

    /**
     * Equivalent to rgb(0, 255, 255) or #00FFFF.
     */
    public static final Colorf AQUA = new RGBColorf(0f, 1f, 1f, 1f);

    /**
     * Equivalent to rgb(0, 255, 255) or #00FFFF.
     */
    public static final Colorf CYAN = new RGBColorf(0f, 1f, 1f, 1f);

    /**
     * Equivalent to rgb(0, 0, 255) or #0000FF.
     */
    public static final Colorf BLUE = new RGBColorf(0f, 0f, 1f, 1f);

    // brown

    /**
     * Equivalent to rgb(128, 0, 0) or #800000.
     */
    public static final Colorf MAROON = new RGBColorf(0.5f, 0f, 0f, 1f);

    // white

    /**
     * Equivalent to rgb(255, 255, 255) or #FFFFFF.
     */
    public static final Colorf WHITE = new RGBColorf(1f, 1f, 1f, 1f);

    // gray

    /**
     * Equivalent to rgb(0, 0, 0) or #000000.
     */
    public static final Colorf BLACK = new RGBColorf(0f, 0f, 0f, 1f);

    private HTMLColorf() {
    }
}
