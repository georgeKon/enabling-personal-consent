package dbconsent;

import java.util.*;

/**
 * The Test Schema for JUnit tests.
 *
 * @author Ross Viljoen
 */
public class TestSchema implements Schema{

    private static List<String> tables;
    private static Map<String, List<String>> columns;

    /* Singleton Pattern ensures there is only one instance */
    private static TestSchema instance;
    private TestSchema() {
        tables = Arrays.asList("A", "B", "P");
        columns = new HashMap<>();
        columns.put("A", Arrays.asList("a1", "a2"));
        columns.put("B", Arrays.asList("b1", "b2"));
        columns.put("P", Arrays.asList("p1", "p2", "p3"));
    }
    static {
        instance = new TestSchema();
    }
    public static TestSchema getInstance() {
        return instance;
    }

    public List<String> getTables() {
        return tables;
    }

    public List<String> getColumnsForTable(String tableName) {
        return columns.get(tableName.toUpperCase());
    }

}