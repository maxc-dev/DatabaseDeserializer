package requirements;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public @interface Requirement {
    char code();
    int position();
    String desc();
}
