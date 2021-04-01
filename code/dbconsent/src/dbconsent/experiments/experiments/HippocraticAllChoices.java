package dbconsent.experiments.experiments;

import dbconsent.experiments.HippocraticExperiment;

public class HippocraticAllChoices {

    public static void main(String[] args) {
        HippocraticExperiment hippocraticExperiment = new HippocraticExperiment();
        String choiceColumns[] = {"C_CUSTKEY","C_NAME", "C_ADDRESS", "C_NATIONKEY", "C_PHONE","C_ACCTBAL","C_MKTSEGMENT","C_COMMENT"};
        String selectedAttributes[] = {"C_CUSTKEY","C_NAME", "C_ADDRESS", "C_NATIONKEY", "C_PHONE","C_ACCTBAL","C_MKTSEGMENT","C_COMMENT"};
        String fileSuffix = "";
        hippocraticExperiment.experiment(choiceColumns, selectedAttributes, fileSuffix);
    }

}
