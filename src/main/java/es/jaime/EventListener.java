package es.jaime;

import java.lang.annotation.*;
import java.util.function.Consumer;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface EventListener {
    /**
     * If you want to listen certain events that needs to implement an interface
     * you can declare it in this array.
     */
    Class<?>[] value() default {};

    /**
     * The orden which the event listeners will be called. If you want
     * your event listener to be called first you would set this value
     * to highest
     */
    Priority pritority() default Priority.NORMAL;
}
