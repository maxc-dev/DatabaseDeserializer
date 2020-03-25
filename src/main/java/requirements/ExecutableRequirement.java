package requirements;

import io.DBUtils;
import io.Deserializer;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public interface ExecutableRequirement<T> {
    T execute(Deserializer deserializer, DBUtils dbUtils);
}
