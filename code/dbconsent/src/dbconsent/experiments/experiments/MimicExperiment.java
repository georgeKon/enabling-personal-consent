package dbconsent.experiments.experiments;

import dbconsent.*;
import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;
import dbconsent.experiments.Results;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class MimicExperiment {
    static ParseDatalog parseDatalog;
    private static Set<DatalogStatement> constraints;

    static Schema mimic_schema = new Schema() {
        @Override
        public List<String> getTables() {
            return Arrays.asList("chartevents", "patients", "diangnosis_icd", "d_icd_diagnoses", "admissions", "icustays", "services");
        }

        @Override
        public List<String> getColumnsForTable(String tableName) {
            switch (tableName){
                case "CHARTEVENTS":
                    return Arrays.asList("ROW_ID", "SUBJECT_ID", "HADM_ID", "ICUSTAY_ID", "ITEMID", "CHARTTIME", "STORETIME", "CGID", "VALUE", "VALUENUM", "VALUEUOM", "WARNING", "ERROR", "RESULTSTATUS", "STOPPED");
                case "PATIENTS":
                    return Arrays.asList("ROW_ID", "SUBJECT_ID", "GENDER", "DOB", "DOD", "DOD_HOSP", "DOD_SSN", "EXPIRE_FLAG");
                case "DIAGNOSESICD":
                    return Arrays.asList("ROW_ID", "SUBJECT_ID", "HADM_ID", "SEQ_NUM", "ICD9_CODE");
                case "DICDDIAGNOSES":
                    return Arrays.asList("ROW_ID", "ICD9_CODE", "SHORT_TITLE", "LONG_TITLE");
                case "ADMISSIONS":
                    return Arrays.asList("ROW_ID", "SUBJECT_ID", "HADM_ID", "ADMITTIME", "DISCHTIME", "DEATHTIME", "ADMISSION_TYPE", "ADMISSION_LOCATION", "DISCHARGE_LOCATION", "INSURANCE", "LANGUAGE", "RELIGION", "MARITAL_STATUS", "ETHNICITY", "EDREGTIME", "EDOUTTIME", "DIAGNOSIS", "HOSPITAL_EXPIRE_FLAG", "HAS_CHARTEVENTS_DATA");
                case "ICUSTAYS":
                    return Arrays.asList("ROW_ID", "SUBJECT_ID", "HADM_ID", "ICUSTAY_ID", "DBSOURCE", "FIRST_CAREUNIT", "LAST_CAREUNIT", "FIRST_WARDID", "LAST_WARDID", "INTIME", "OUTTIME", "LOS");
                case "SERVICES":
                    return Arrays.asList("ROW_ID", "SUBJECT_ID", "HADM_ID", "TRANSFERTIME", "PREV_SERVICE", "CURR_SERVICE");
            }
            return null;
        }
    };

    public static void main(String[] args) {
        runExperiment();
    }

    public static void runExperiment(){
        Properties properties = new Properties();
        String projectDir = properties.getProjectDir();

        Experiment experiment = new Experiment(properties, "mimic3");
        parseDatalog = new ParseDatalog();

        // Queries -
        int[] queries = {1, 2, 3, 4, 5, 6, 7};
        int[] noConstraints = {100};//, 200, 300, 400, 500, 600, 700, 800, 900, 1000};

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(projectDir + "results/mimic/timings.txt", "UTF-8");
            writer.write("queryid,noconstraints,querytime\n");
        } catch (FileNotFoundException | UnsupportedEncodingException e){
            e.printStackTrace();
        }

        for (Integer queryID : queries) {
            Results results = experiment.timeQuery(toPostgreSQLString(getQuery(queryID), mimic_schema));
            float executiontime = results.execution;

            writer.write(queryID + ",0," + executiontime + "\n");
            writer.flush();
        }

        for (int noCons : noConstraints){

            for (Integer queryID : queries) {
                System.out.println("qID: " + queryID);

                DatalogStatement query = getQuery(queryID);
                System.out.println(toPostgreSQLString(query, mimic_schema));
                System.out.println(query);

                constraints = new HashSet<>();
                switch (queryID){
                    case 1:{
                        for (int i = 0; i < noCons; i++){
                            constraints.add(randomNonJoinedPatientConstraint());
                        }
                        break;
                    }
                    case 2:{
                        for (int i = 0; i < noCons/2; i++){
                            constraints.add(randomNonJoinedPatientConstraint());
                            constraints.add(randomNonJoinedCharteventConstraint());
                        }
                        break;
                    }
                    case 3:{
                        for (int i = 0; i < noCons/2; i++){
                            constraints.add(randomNonJoinedPatientConstraint());
                            constraints.add(randomNonJoinedAdmissionConstraint());
                        }
                        break;
                    }
                    case 4:{
                        for (int i = 0; i < noCons; i++){
                            constraints.add(randomNonJoinedCharteventConstraint());
                        }
                        break;
                    }

                    case 5:{
                        for (int i = 0; i < noCons; i++){
                            constraints.add(randomDiagnosisConstraintJoined());
                        }
                        break;
                    }
                    case 6:{
                        for (int i = 0; i < noCons; i++){
                            constraints.add(randomServicesConstraintJoined());
                        }
                        break;
                    }
                    case 7:{
                        for (int i = 0; i < noCons; i++){
                            constraints.add(randomICUConstraintJoined());
                        }
                        break;
                    }
                }
                DatalogStatement rwquery = RewritingAlgorithm.rewriteQuery(query, constraints);
//                System.out.println("RWQUERY");
//                System.out.println(toPostgreSQLString(rwquery, mimic_schema));
//                System.out.println(toPostgreSQLString(rwquery, mimic_schema).split("AND ").length);
                try {
                    Results results = experiment.timeQuery(toPostgreSQLString(rwquery, mimic_schema));
                    float executiontime = results.execution;

                    writer.write(queryID + "," + noCons + "," + executiontime + "\n");
                    writer.flush();
                } catch (Exception e){
                    writer.write(queryID + "," + noCons + ","  + "\n");
                }
            }
        }

        writer.close();
//        randomNonJoinedAdmissionConstraint(); //q1, q3
//        randomNonJoinedPatientConstraint(); //q1, q2, q3
//        randomNonJoinedCharteventConstraint();  // q4
//
//        randomDiagnosisConstraintJoined(); // q5
//        randomICUConstraintJoined(); //q7
//        randomServicesConstraintJoined(); //q6
    }

    public static DatalogStatement getQuery(int id){
        DatalogStatement query = null;
        query = new DatalogStatement("Q");
        switch (id) {
            case 1: {
                query = parseDatalog.safeParseString("Q(a,b,c,d,e,f,g,h) :- PATIENTS(a,b,c,d,e,f,g,h)");
                break;
            }
            case 2: {
                query = parseDatalog.safeParseString("Q(sid,p_gender) :- PATIENTS(a,sid,p_gender,d,e,f,g,h),CHARTEVENTS(i,sid,j,k,211,m,n,o,p,q,r,s,t,t,v)");
                break;
            }
            case 3:{
                query = parseDatalog.safeParseString("Q(u,v,j,l,m,n,q) :- ADMISSIONS(a,sid,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s),PATIENTS(t,sid,u,v,w,x,y,z)");
                break;
            }
            case 4:{
                query = parseDatalog.safeParseString("Q(sid,hadm_id,itemid,value,result) :- CHARTEVENTS(m,sid,hadm_id,o,itemid,q,r,s,value,u,v,1,x,result,z)");
                break;
            }
            case 5:{
                query = parseDatalog.safeParseString("Q(sid, short_title) :- DICDDIAGNOSES(a,ICD9_CODE,short_title,d),DIAGNOSESICD(e,sid,f,g,ICD9_CODE)");
                break;
            }
            case 6:{
                query = parseDatalog.safeParseString("Q(sid,q,x) :- ADMISSIONS(a,sid,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s),SERVICES(t,sid,u,v,w,x)");
                break;
            }
            case 7:{
                query = parseDatalog.safeParseString("Q(sid,l,bb) :- ICUSTAYS(a,sid,c,d,e,f,g,h,i,j,k,l),ADMISSIONS(m,sid,n,o,p,q,r,s,t,u,v,w,x,y,z,aa,bb,cc,dd)");
            }
        }
        return query;
    }

    // Join on "ICD9_CODE", fix subject_id (can also change to fix hadm_id), project short_name
    private static DatalogStatement randomDiagnosisConstraintJoined (){
        int sid = (int) (Math.random() * (50000 - 2) + 2);

        String relation1 = String.format("DIAGNOSESICD(e,%d,hadm_id,f,ICD9_CODE)", sid);
        String relation2 = "DICDDIAGNOSES(a,ICD9_CODE,c,d)";

        String head = "Q(c) :- "; // "" // hadm_id

        return parseDatalog.safeParseString(head + relation1 + "," + relation2);
    }

    // Join on and fix "subject_id" (can also change to fix hadm_id)
    private static DatalogStatement randomICUConstraintJoined (){
        int sid = (int) (Math.random() * (50000 - 2) + 2);

        String relation1 = String.format("ICUSTAYS(a,%d,c,d,e,f,g,h,i,j,k,l)", sid);
        String relation2 = String.format("ADMISSIONS(m,%d,n,o,p,q,r,s,t,u,v,w,x,y,z,aa,bb,cc,dd)", sid);

        String head = "Q() :- ";
        return parseDatalog.safeParseString(head + relation1 + "," + relation2);
    }

    // Join on and fix "subject_id"(can also change to fix hadm_id)
    private static DatalogStatement randomServicesConstraintJoined (){
        int sid = (int) (Math.random() * (50000 - 2) + 2);

        String relation1 = String.format("SERVICES(t,%d,u,v,w,x)", sid);
        String relation2 = String.format("ADMISSIONS(a,%d,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s)", sid);

        String head = "Q() :- ";
        return parseDatalog.safeParseString(head + relation1 + "," + relation2);
    }

    // Fix "subject_id", project random from: Ethnicity, religion, insurance, diagnosis & marital_status
        // Can change to project a random number of the attributes
    private static DatalogStatement randomNonJoinedAdmissionConstraint(){
        int sid = (int) (Math.random() * (50000 - 2) + 2);
        String admission = String.format("ADMISSIONS(a,%d,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s)", sid);

        ArrayList<String> protectAttributes = new ArrayList<>();
        protectAttributes.addAll(Arrays.asList("j", "l", "m", "n", "q"));  // Ethnicity, religion, insurance, diagnosis & marital_status
        String randomAttribute = protectAttributes.get((int) (Math.random() * (4)));
        String head = "Q(" + randomAttribute + ") :- ";

        return parseDatalog.safeParseString(head + admission);

    }

    // Fix "subject_id", project either gender or dob
    private static DatalogStatement randomNonJoinedPatientConstraint(){
        int sid = (int) (Math.random() * (50000 - 2) + 2);
        String patient = String.format("PATIENTS(a,%d,c,d,e,f,g,h)", sid);
        String head;
        if ((int) (Math.round(Math.random())) == 1){
            head = "Q(d) :- "; // gender
        } else {
            head = "Q(c) :- "; // dob
        }

        return parseDatalog.safeParseString(head + patient);
    }

    // Fix "subject_id", can change to fix itemid but odd ranges: (ItemId 0-9000, then 30000 - 30450, then 40k - 47k)
    private static DatalogStatement randomNonJoinedCharteventConstraint(){
        int sid = (int) (Math.random() * (50000 - 2) + 2);
        ArrayList<Integer> itemIdArr = new ArrayList<>();
        itemIdArr.add((int) (Math.random() * (9000)));
        itemIdArr.add((int) (Math.random() * (30450 - 30000) + 30000));
        itemIdArr.add((int) (Math.random() * (47000 - 40000) + 40000));

        Random random = new Random();
        int index = random.nextInt(itemIdArr.size());
        int itemid = itemIdArr.get(index);

        String chartevent = String.format("CHARTEVENTS(a,%d,c,d,e,f,g,h,i,j,k,l,m,n,o)", sid);
        String head = "Q() :- ";
        return parseDatalog.safeParseString(head + chartevent);
    }


    // Using underscore with parseDatalog causes problems, so we remove them. We re-introduce them with this method
    private static String toPostgreSQLString(DatalogStatement query, Schema schema){
        return query.toPostgreSQLString(mimic_schema).replaceAll("DICDDIAGNOSES", "D_ICD_DIAGNOSES").replaceAll("DIAGNOSESICD", "DIAGNOSES_ICD");
    }

}
