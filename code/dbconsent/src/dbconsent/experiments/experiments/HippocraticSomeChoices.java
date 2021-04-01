package dbconsent.experiments.experiments;

import dbconsent.experiments.HippocraticExperiment;

public class HippocraticSomeChoices {

    public static void main(String[] args) {
        HippocraticExperiment hippocraticExperiment = new HippocraticExperiment();
        String choiceColumns[] = {"C_NAME", "C_ADDRESS", "C_NATIONKEY", "C_PHONE"};
        String selectedAttributes[] = {"C_NAME", "C_ADDRESS", "C_NATIONKEY", "C_PHONE"};
        String fileSuffix = "-somechoices";
        hippocraticExperiment.experiment(choiceColumns, selectedAttributes, fileSuffix);
    }

}
