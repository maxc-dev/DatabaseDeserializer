package models.serial;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Max Carter
 * @since 19/03/2020
 *
 * The purpose of the @Serial annotation is to ensure that only
 * the fields that are specified
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Serializable {
}
