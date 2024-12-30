package io.github.shimeoki.jfx.rasterization;

/**
 * Non-instantiable class with some {@link Colorf colors} from the
 * HTML specification as constants.
 *
 * <p>
 * Data for the colors is from
 * <a href="https://htmlcolorcodes.com/color-names/">HTML Color Codes</a>.
 *
 * @since 2.0.0
 *
 * @see Colorf
 */
public final class HTMLColorf {

    /**
     * Equivalent to {@code rgb(255, 255, 255 / 0)} or {@code #FFFFFF00}.
     *
     * @since 2.0.0
     */
    public static final Colorf TRANSPARENT_WHITE = new Colorf(1f, 1f, 1f, 0f);

    /**
     * Equivalent to {@code rgb(0, 0, 0 / 0)} or {@code #00000000}.
     *
     * @since 2.0.0
     */
    public static final Colorf TRANSPARENT_BLACK = new Colorf(0f, 0f, 0f, 0f);

    /**
     * Equivalent to {@code rgb(255, 0, 0)} or {@code #FF0000}.
     *
     * @since 2.0.0
     */
    public static final Colorf RED = new Colorf(1f, 0f, 0f, 1f);

    /**
     * Equivalent to {@code rgb(255, 165, 0)} or {@code #FFA500}.
     *
     * @since 2.0.0
     */
    public static final Colorf ORANGE = new Colorf(1f, 0.65f, 0f, 1f);

    /**
     * Equivalent to {@code rgb(255, 255, 0)} or {@code #FFFF00}.
     *
     * @since 2.0.0
     */
    public static final Colorf YELLOW = new Colorf(1f, 1f, 0f, 1f);

    /**
     * Equivalent to {@code rgb(255, 0, 255)} or {@code #FF00FF}.
     *
     * @since 2.0.0
     */
    public static final Colorf FUCHSIA = new Colorf(1f, 0f, 1f, 1f);

    /**
     * Equivalent to {@code rgb(128, 0, 128)} or {@code #800080}.
     *
     * @since 2.0.0
     */
    public static final Colorf PURPLE = new Colorf(0.5f, 0f, 0.5f, 1f);

    /**
     * Equivalent to {@code rgb(0, 255, 0)} or {@code #00FF00}.
     *
     * @since 2.0.0
     */
    public static final Colorf LIME = new Colorf(0f, 1f, 0f, 1f);

    /**
     * Equivalent to {@code rgb(0, 128, 0)} or {@code #008000}.
     *
     * @since 2.0.0
     */
    public static final Colorf GREEN = new Colorf(0f, 0.5f, 0f, 1f);

    /**
     * Equivalent to {@code rgb(0, 255, 255)} or {@code #00FFFF}.
     *
     * @since 2.0.0
     */
    public static final Colorf AQUA = new Colorf(0f, 1f, 1f, 1f);

    /**
     * Equivalent to {@code rgb(0, 255, 255)} or {@code #00FFFF}.
     *
     * @since 2.0.0
     */
    public static final Colorf CYAN = new Colorf(0f, 1f, 1f, 1f);

    /**
     * Equivalent to {@code rgb(0, 0, 255)} or {@code #0000FF}.
     *
     * @since 2.0.0
     */
    public static final Colorf BLUE = new Colorf(0f, 0f, 1f, 1f);

    /**
     * Equivalent to {@code rgb(128, 0, 0)} or {@code #800000}.
     *
     * @since 2.0.0
     */
    public static final Colorf MAROON = new Colorf(0.5f, 0f, 0f, 1f);

    /**
     * Equivalent to {@code rgb(255, 255, 255)} or {@code #FFFFFF}.
     *
     * @since 2.0.0
     */
    public static final Colorf WHITE = new Colorf(1f, 1f, 1f, 1f);

    /**
     * Equivalent to {@code rgb(0, 0, 0)} or {@code #000000}.
     *
     * @since 2.0.0
     */
    public static final Colorf BLACK = new Colorf(0f, 0f, 0f, 1f);

    private HTMLColorf() {
        // prevents instantiation
    }
}
