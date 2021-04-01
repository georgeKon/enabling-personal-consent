package dbconsent.experiments.experiments;

import dbconsent.experiments.HippocraticExperiment;

public class HippocraticKeyOnly {

    public static void main(String[] args) {
        HippocraticExperiment hippocraticExperiment = new HippocraticExperiment();
        String choiceColumns[] = {"C_CUSTKEY"};
        String selectedAttributes[] = {"C_CUSTKEY","C_NAME", "C_ADDRESS", "C_NATIONKEY", "C_PHONE","C_ACCTBAL","C_MKTSEGMENT","C_COMMENT"};
        String fileSuffix = "-keyonly";
        hippocraticExperiment.experiment(choiceColumns, selectedAttributes, fileSuffix);
    }

}
