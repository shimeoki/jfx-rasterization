package io.github.shimeoki.jfx.rasterization.demo.test;

/**
 * A functional interface for a function that can be timed with
 * {@link Timekeeper}.
 *
 * <p>
 * It's just a function with no parameters and no return value.
 * <p>
 * It's supposed to be used as a lambda expression, which acts as a wrapper of a
 * function or method call.
 *
 * @since 1.0.0
 *
 * @see Timekeeper
 */
@FunctionalInterface
public interface Timeable {

    /**
     * Executes this function.
     *
     * @since 1.0.0
     */
    public void exec();
}
