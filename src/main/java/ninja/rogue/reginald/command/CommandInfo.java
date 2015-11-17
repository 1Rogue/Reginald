package ninja.rogue.reginald.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Spencer on 4/24/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String name() default "null";

}
