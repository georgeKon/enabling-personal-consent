package dbconsent.generation;

import dbconsent.*;
import dbconsent.experiments.Properties;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ConstraintGenerator {

    public static void main(String[] args) {
        generateCustomerConstraints(500,7);
        generateOrderConstraints(500,7);
    }


    /**
     * Writes the constraints to the file queries/*number*-tpch*scale*-customer-constraint.dl for constraints without joins
     * and queries/*number*-tpch*scale*-joined-customer-constraint.dl for constraints with joins, respectively
     * @param number the number of constraints to generate (eg '500')
     * @param tpchscale the scale of tpch these constraints are being used with (eg '7')
     */
    public static void generateCustomerConstraints(int number, int tpchscale){
        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();

        //number constraints that are just customer relations with various different heads
        Set<DatalogStatement> constraints = generateUnjoinedCustomerConstraints(number,tpchscale);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(projectDir + "queries/"+number+"-tpch" + tpchscale + "-customer-constraint.dl","UTF-8");
            for(DatalogStatement query : constraints) {
                writer.write(query.toString() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //number constraints that are similar to the above number, but also include joins to Nation and Orders
        Atom nation = new Atom("NATION");
        for (String column : TPCHSchema.getInstance().getColumnsForTable("NATION")) {
            nation.addTerm(new Variable(column));
        }
        Atom orders = new Atom("ORDERS");
        for (String column : TPCHSchema.getInstance().getColumnsForTable("ORDERS")) {
            orders.addTerm(new Variable(column));
        }
        Random random = new Random();
        Set<DatalogStatement> constraintsPlusJoin = generateUnjoinedCustomerConstraints(number,tpchscale);
        List<String> orderPriority = Arrays.asList("'1-URGENT'","'2-HIGH'","'3-MEDIUM'","'4-NOT SPECIFIED'","'5-LOW'");
        List<String> orderStatus = Arrays.asList("'F'","'O'","'P'");
        for (DatalogStatement query : constraintsPlusJoin) {
            Atom customer = query.getAtoms().get(0);
            if(random.nextBoolean()) {
                //We'll join with NATION
                Atom newNation = new Atom(nation);
                //Make the join
                newNation.replaceTerm(0,customer.getTerm(3));
                query.addAtom(newNation);
                //Maybe make some restriction to the regionkey
                if(random.nextBoolean()) {
                    newNation.replaceTerm(2, new IntegerConstant(random.nextInt(5)));
                }
            } else {
                //We'll join with ORDERS
                Atom newOrders = new Atom(orders);
                //Make the join
                newOrders.replaceTerm(1, customer.getTerm(0));
                query.addAtom(newOrders);
                //Maybe make some restriction to the orderpriority
                if(random.nextBoolean()) {
                    newOrders.replaceTerm(5, new StringConstant(orderPriority.get(random.nextInt(5))));
                }
                //Maybe make some restriction to the orderstatus
                if(random.nextBoolean()) {
                    newOrders.replaceTerm(2, new StringConstant(orderStatus.get(random.nextInt(3))));
                }
            }
        }
        PrintWriter writer2 = null;
        try {
            writer2 = new PrintWriter(projectDir + "queries/"+number+"-tpch" + tpchscale + "-joined-customer-constraint.dl","UTF-8");
            for(DatalogStatement query : constraintsPlusJoin) {
                writer2.write(query.toString() + "\n");
            }
            writer2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the constraints to the file queries/*number*-tpch*scale*-order-constraint.dl for constraints without joins
     * and queries/*number*-tpch*scale*-joined-order-constraint.dl for constraints with joins, respectively
     * @param number the number of constraints to generate (eg '500')
     * @param tpchscale the scale of tpch these constraints are being used with (eg '7')
     */
    public static void generateOrderConstraints(int number, int tpchscale) {
        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();

        //number queries that are just order relations with various different heads
        Set<DatalogStatement> queries = generateUnjoinedOrderConstraints(number,tpchscale);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(projectDir + "queries/"+number+"-tpch" + tpchscale + "-order-constraint.dl","UTF-8");
            for(DatalogStatement query : queries) {
                writer.write(query.toString() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Atom customer = new Atom("CUSTOMER");
        for (String column : TPCHSchema.getInstance().getColumnsForTable("CUSTOMER")) {
            customer.addTerm(new Variable(column));
        }
        Atom lineitem = new Atom("LINEITEM");
        for (String column : TPCHSchema.getInstance().getColumnsForTable("LINEITEM")) {
            lineitem.addTerm(new Variable(column));
        }

        Random random = new Random();
        Set<DatalogStatement> queriesPlusJoin = generateUnjoinedOrderConstraints(number,tpchscale);
        List<String> marketsegment = Arrays.asList("'AUTOMOBILE'","'BUILDING'","'FURNITURE'","'HOUSEHOLD'","'MACHINERY'");
        List<Integer> linenumber = Arrays.asList(1,2,3,4,5,6,7);
        List<String> linestatus = Arrays.asList("'F'","'O'");
        List<String> returnflag = Arrays.asList("'A'","'N'","'R'");
        List<Double> tax = Arrays.asList(0.00,0.01,0.02,0.03,0.04,0.05,0.06,0.07,0.08);
        List<String> shipinstruct = Arrays.asList("'COLLECT COD'", "'DELIVER IN PERSON'", "'NONE'", "'TAKE BACK RETURN'");
        List<String> shipmode = Arrays.asList("'AIR'","'FOB'","'MAIL'","'MAIL'","'RAIL'","'REG AIR'","'SHIP'","'TRUCK'");
        for (DatalogStatement query : queriesPlusJoin) {
            Atom order = query.getAtoms().get(0);
            if(random.nextBoolean()) {
                //We'll join with CUSTOMER
                Atom newCustomer = new Atom(customer);
                //Make the join
                newCustomer.replaceTerm(0,order.getTerm(1));
                query.addAtom(newCustomer);
                //Maybe make some restriction to the marketsegment
                if(random.nextBoolean()) {
                    newCustomer.replaceTerm(6, new StringConstant(marketsegment.get(random.nextInt(marketsegment.size()))));
                }
            } else {
                //We'll join with LINEITEM
                Atom newLineitem = new Atom(lineitem);
                //Make the join
                newLineitem.replaceTerm(0, order.getTerm(0));
                query.addAtom(newLineitem);
                //Maybe make some restriction to the linenumber
                if(random.nextBoolean()) {
                    newLineitem.replaceTerm(3, new IntegerConstant(linenumber.get(random.nextInt(linenumber.size()))));
                }
                //Maybe make some restriction to the linestatus
                if(random.nextBoolean()) {
                    newLineitem.replaceTerm(9, new StringConstant(linestatus.get(random.nextInt(linestatus.size()))));
                }
                //Maybe make some restriction to the returnflag
                if(random.nextBoolean()) {
                    newLineitem.replaceTerm(8, new StringConstant(returnflag.get(random.nextInt(returnflag.size()))));
                }
                //Maybe make some restriction to the tax
                if(random.nextBoolean()) {
                    newLineitem.replaceTerm(7, new FloatConstant(tax.get(random.nextInt(tax.size()))));
                }
                //Maybe make some restriction to the shipinstruct
                if(random.nextBoolean()) {
                    newLineitem.replaceTerm(13, new StringConstant(shipinstruct.get(random.nextInt(shipinstruct.size()))));
                }
                //Maybe make some restriction to the shipmode
                if(random.nextBoolean()) {
                    newLineitem.replaceTerm(14, new StringConstant(shipmode.get(random.nextInt(shipmode.size()))));
                }
            }
        }
        PrintWriter writer2 = null;
        try {
            writer2 = new PrintWriter(projectDir + "queries/"+number+"-tpch" + tpchscale + "-joined-order-constraint.dl","UTF-8");
            for(DatalogStatement query : queriesPlusJoin) {
                writer2.write(query.toString() + "\n");
            }
            writer2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static Set<DatalogStatement> generateUnjoinedCustomerConstraints(int count, int tpchscale) {
        Random random = new Random();
        Atom customerAtom = new Atom("CUSTOMER");
        List<Variable> allPredicateArguments = new ArrayList<>();
        for (String column : TPCHSchema.getInstance().getColumnsForTable("CUSTOMER")) {
            Variable variable = new Variable(column);
            customerAtom.addTerm(variable);
            allPredicateArguments.add(variable);
        }
        //Remove c_custkey
        allPredicateArguments.remove(0);
        //Generate number negative constraints that are customers associated with something
        Set<Integer> hadIDs = new HashSet<>();
        Set<DatalogStatement> queries = new HashSet<>();
        int i = 0;
        while(i < count) {
            DatalogStatement query = new DatalogStatement("N");
            Atom newAtom = new Atom(customerAtom);
            query.addAtom(newAtom);
            int customerId = random.nextInt(tpchscale*150000);
            if(!hadIDs.contains(customerId)) {
                i++;
                hadIDs.add(customerId);
                newAtom.replaceTerm(0, new IntegerConstant(customerId));
            } else {
                continue;
            }
            int numberOfHeadVariables = Math.max(random.nextInt(7),1);
            Collections.shuffle(allPredicateArguments);
            for (int j = 0; j < numberOfHeadVariables; j++) {
                query.addHeadVariable(allPredicateArguments.get(j));
            }
            queries.add(query);
        }
        return queries;
    }

    private static Set<DatalogStatement> generateUnjoinedOrderConstraints(int count, int tpchscale) {
        Random random = new Random();
        Atom ordersAtom = new Atom("ORDERS");
        List<Variable> allPredicateArguments = new ArrayList<>();
        for (String column : TPCHSchema.getInstance().getColumnsForTable("ORDERS")) {
            Variable variable = new Variable(column);
            ordersAtom.addTerm(variable);
            allPredicateArguments.add(variable);
        }
        //Remove o_orderkey
        allPredicateArguments.remove(0);
        //Generate number negative constraints that are customers associated with something
        Set<Integer> hadIDs = new HashSet<>();
        Set<DatalogStatement> queries = new HashSet<>();
        int i = 0;
        while(i < count) {
            DatalogStatement query = new DatalogStatement("N");
            Atom newAtom = new Atom(ordersAtom);
            query.addAtom(newAtom);
            int ordersId = random.nextInt(tpchscale*1500000);
            if(!hadIDs.contains(ordersId)) {
                i++;
                hadIDs.add(ordersId);
                newAtom.replaceTerm(0, new IntegerConstant(ordersId));
            } else {
                continue;
            }
            int numberOfHeadVariables = Math.max(random.nextInt(8),0);
            Collections.shuffle(allPredicateArguments);
            for (int j = 0; j < numberOfHeadVariables; j++) {
                query.addHeadVariable(allPredicateArguments.get(j));
            }
            queries.add(query);
        }
        return queries;
    }

}
