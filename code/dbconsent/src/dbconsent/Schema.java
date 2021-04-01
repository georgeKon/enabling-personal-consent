package dbconsent;

import java.util.List;

/**
 * Parsing and Serialization of PostgreSQL depends on knowing the database schema
 *
 */
public interface Schema {

    List<String> getTables();
    List<String> getColumnsForTable(String tableName);

}
