package dbconsent.generation;

import java.util.HashMap;
import java.util.Map;

public class Restriction<T> {
    String columnName = "";
    int columnIndex = 0;
    Map<T, Double> valuesToRestrictivity = new HashMap<>();
    public Restriction(String columnName, int columnIndex, Map<T, Double> valuesToRestrictivity) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.valuesToRestrictivity = valuesToRestrictivity;
    }
}
