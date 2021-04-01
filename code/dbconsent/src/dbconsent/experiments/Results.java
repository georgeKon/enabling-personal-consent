package dbconsent.experiments;

import java.util.Arrays;

public class Results {
    public float planning;
    public float execution;
    public Results(float[] planning, float[] execution) {

        //Average Execution Time
        float sum = 0;
        for (Float timingE : execution) {
            sum += timingE;
        }
        this.execution = sum / ((float) execution.length);

        //Average planning time
        sum = 0;
        for (Float timingP : planning) {
            sum += timingP;
        }
        this.planning = sum / ((float) planning.length);
    }
}
