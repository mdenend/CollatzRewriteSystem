import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mad4672 on 4/17/18.
 */
public class RewriteSystemOverallOutputHelper {

    private List<RewriteSystemRow> list;
    private Options opts;

    //these separate the csv files and give correct newlines based on the system the files were generated.
    public static final String CS = "\t";
    public static final String LS = System.getProperty("line.separator");

    public RewriteSystemOverallOutputHelper(Options opts) {
        list = new LinkedList<>();
        this.opts = opts;
    }

    private class RewriteSystemRow {
        private long inputNumber;
        private long finalNumber;
        private int numSteps;
        private int numArrayDoubles;

        RewriteSystemRow (long inputNumber, long finalNumber, int numSteps, int numArrayDoubles) {
            this.finalNumber = finalNumber;
            this.inputNumber = inputNumber;
            this.numArrayDoubles = numArrayDoubles;
            this.numSteps = numSteps;
        }

        public int getNumArrayDoubles() {
            return numArrayDoubles;
        }

        public int getNumSteps() {
            return numSteps;
        }

        public long getFinalNumber() {
            return finalNumber;
        }

        public long getInputNumber() {
            return inputNumber;
        }
    }

    public void addRowToOverallOutput(RewriteSystemTermOutputHelper termOutputHelper) {
        RewriteSystemRow row = new RewriteSystemRow(termOutputHelper.getInitialNumber(), termOutputHelper.getFinalNumber(),
                termOutputHelper.getNumSteps(), termOutputHelper.getNumArrayDoubles());
        list.add(row);
    }

    public void generateOutputFile(){
        String[] splitFile = opts.getRewriteSystemFile().split("/|\\.");
        String fileToGenerate = "Rewrite_System_Overall_Results_System_" + splitFile[splitFile.length-2] +".csv";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(opts.getOutputDirectory() + "/"  + fileToGenerate));
            writer.println("Input Number" + CS + "Final Number" + CS + "Number of rewrite steps" + CS + "Number of array doubles");

            for (RewriteSystemRow r : list)
                writer.println(r.getInputNumber() + CS + r.getFinalNumber() + CS + r.numSteps + CS + r.getNumArrayDoubles());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Unable to generate output file "  + fileToGenerate);
            System.err.println("Exception caught: " + e);
            System.exit(11);
        }
    }


}
