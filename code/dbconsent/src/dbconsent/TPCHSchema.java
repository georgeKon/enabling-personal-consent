package dbconsent;

import java.util.*;

/**
 * The Database Schema for the TPC-H benchmark dataset.
 *
 */
public class TPCHSchema implements Schema{

    private static List<String> tables;
    private static Map<String, List<String>> columns;

    /* Singleton Pattern ensures there is only one instance */
    private static TPCHSchema instance;
    private TPCHSchema() {
        tables = Arrays.asList("LINEITEM", "PARTSUPP", "PART", "SUPPLIER", "NATION", "REGION", "ORDERS", "CUSTOMER");
        columns = new HashMap<>();
        columns.put("LINEITEM", Arrays.asList("L_ORDERKEY", "L_PARTKEY", "L_SUPPKEY", "L_LINENUMBER",
                "L_QUANTITY", "L_EXTENDEDPRICE", "L_DISCOUNT", "L_TAX", "L_RETURNFLAG", "L_LINESTATUS",
                "L_SHIPDATE", "L_COMMITDATE", "L_RECEIPTDATE", "L_SHIPINSTRUCT", "L_SHIPMODE", "L_COMMENT"));
        columns.put("PARTSUPP", Arrays.asList("PS_PARTKEY", "PS_SUPPKEY", "PS_AVAILQTY", "PS_SUPPLYCOST", "PS_COMMENT"));
        columns.put("PART", Arrays.asList("P_PARTKEY", "P_NAME", "P_MFGR", "P_BRAND", "P_TYPE", "P_SIZE",
                "P_CONTAINER", "P_RETAILPRICE", "P_COMMENT"));
        columns.put("SUPPLIER", Arrays.asList("S_SUPPKEY", "S_NAME", "S_ADDRESS", "S_NATIONKEY", "S_PHONE",
                "S_ACCTBAL", "S_COMMENT"));
        columns.put("NATION", Arrays.asList("N_NATIONKEY", "N_NAME", "N_REGIONKEY", "N_COMMENT"));
        columns.put("REGION", Arrays.asList("R_REGIONKEY", "R_NAME", "R_COMMENT"));
        columns.put("ORDERS", Arrays.asList("O_ORDERKEY", "O_CUSTKEY", "O_ORDERSTATUS", "O_TOTALPRICE",
                "O_ORDERDATE", "O_ORDERPRIORITY", "O_CLERK", "O_SHIPPRIORITY", "O_COMMENT"));
        columns.put("CUSTOMER", Arrays.asList("C_CUSTKEY", "C_NAME", "C_ADDRESS", "C_NATIONKEY", "C_PHONE",
                "C_ACCTBAL", "C_MKTSEGMENT", "C_COMMENT"));

        //PROPAGATION: Benchmark queries when used in propagation.
        columns.put("Q10", Arrays.asList("O_CUSTKEY", "C_NAME", "C_ACCTBAL", "N_NAME", "C_ADDRESS", "C_PHONE", "C_COMMENT"));
        columns.put("Q13", Arrays.asList("O_CUSTKEY", "O_ORDERKEY"));
        columns.put("Q18", Arrays.asList("C_NAME", "O_CUSTKEY", "O_ORDERKEY", "O_ORDERDATE", "O_TOTALPRICE", "L_QUANTITY"));
        columns.put("Q4", Arrays.asList("O_ORDERPRIORITY"));
        columns.put("Q21", Arrays.asList("S_NAME"));
        columns.put("Q7", Arrays.asList("L_SHIPDATE", "L_EXTENDEDPRICE"));
        columns.put("Q5", Arrays.asList("N_NAME"));
        columns.put("Q3", Arrays.asList("L_ORDERKEY", "L_EXTENDEDPRICE", "O_ORDERDATE", "O_SHIPPRIORITY"));

    }
    static {
        instance = new TPCHSchema();
    }
    public static TPCHSchema getInstance() {
        return instance;
    }

    public List<String> getTables() {
        return tables;
    }

    public List<String> getColumnsForTable(String tableName) {
        return columns.get(tableName.toUpperCase());
    }

}

// Schema as Datalog:
// LINEITEM(L_ORDERKEY,L_PARTKEY,L_SUPPKEY,L_LINENUMBER,L_QUANTITY,L_EXTENDEDPRICE,L_DISCOUNT,L_TAX,L_RETURNFLAG,L_LINESTATUS,L_SHIPDATE,L_COMMITDATE,L_RECEIPTDATE,L_COMMITDATE,L_RECEIPTDATE,L_SHIPINSTRUCT,L_SHIPMODE,L_COMMENT)
// PARTSUPP(PS_PARTKEY,PS_SUPPKEY,PS_AVAILQTY,PS_SUPPLYCOST,PS_COMMENT)
// PART(P_PARTKEY,P_NAME,P_MFGR,P_BRAND,P_TYPE,P_SIZE,P_CONTAINER,P_RETAILPRICE,P_COMMENT)
// SUPPLIER(S_SUPPKEY,S_NAME,S_ADDRESS,S_NATIONKEY,S_PHONE,S_ACCTBAL,S_COMMENT)
// NATION(N_NATIONKEY,N_NAME,N_REGIONKEY,N_COMMENT)
// REGION(R_REGIONKEY,R_NAME,R_COMMENT)
// ORDERS(O_ORDERKEY,O_CUSTKEY,O_ORDERSTATUS,O_TOTALPRICE,O_ORDERDATE,O_ORDERPRIORITY,O_CLERK,O_SHIPPRIORITY,O_COMMENT)
// CUSTOMER(C_CUSTKEY,C_NAME,C_ADDRESS,C_NATIONKEY,C_PHONE,C_ACCTBAL,C_MKTSEGMENT,C_COMMENT)

// Benchmark query heads as Datalog:
// Q10(O_CUSTKEY,C_NAME,C_ACCTBAL,N_NAME,C_ADDRESS,C_PHONE,C_COMMENT)
// Q13(O_CUSTKEY,O_ORDERKEY)
// Q18(C_NAME,O_CUSTKEY,O_ORDERKEY,O_ORDERDATE,O_TOTALPRICE,L_QUANTITY)
// Q4(O_ORDERPRIORITY)
// Q21(S_NAME)
// Q7(L_SHIPDATE,L_EXTENDEDPRICE)
// Q5(N_NAME)
// Q3(L_ORDERKEY,L_EXTENDEDPRICE,O_ORDERDATE,O_SHIPPRIORITY)
