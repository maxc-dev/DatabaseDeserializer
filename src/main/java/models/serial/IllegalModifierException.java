package models.serial;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class IllegalModifierException extends Exception {
    public IllegalModifierException(String modifier) {
        super("Public modifier is required, cannot have [" + modifier + "] modifier.");
    }

    public IllegalModifierException() {
        super("Public modifier is required.");
    }
}
