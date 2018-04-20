import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mad4672 on 4/16/18.
 */
public class RewriteSystemTermOutputHelper {
    private int numSteps;
    private List<StringNumPair> listOfTerms;
    private long initialNumber;
    private long finalNumber;
    private String initialTerm;
    private String finalTerm;

    private int numArrayDoubles;



    private Options opts;




    public RewriteSystemTermOutputHelper(StringRewriteTerm initialTerm, Options opts) {
        this.initialTerm = initialTerm.toString();
        initialNumber = initialTerm.convertStringToNumber();
        listOfTerms = new LinkedList<>();
        this.opts = opts;

    }

    private class StringNumPair {
        private long num;
        private String string;

        StringNumPair(String str, long num) {
            this.num = num;
            string = str;
        }

        long getNum() {
            return num;
        }

        String getString() {
            return string;
        }
    }



    public void addTermToVerboseList(StringRewriteTerm term){
        listOfTerms.add(new StringNumPair(term.toString(), term.convertStringToNumber()));
    }

    public void finalizeOutput(StringRewriteTerm finalTerm, int numSteps, int numArrayDoubles) {
        this.finalNumber = finalTerm.convertStringToNumber();
        this.finalTerm = finalTerm.toString();
        this.numSteps = numSteps;
        this.numArrayDoubles = numArrayDoubles;
    }

    public void generateOutputFile() {
        String[] splitFile = opts.getRewriteSystemFile().split("/|\\.");
        String fileToGenerate = "Rewrite_System_Results_Number_" + initialNumber + "_System_" + splitFile[splitFile.length-2] +".csv";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(opts.getOutputDirectory() + "/"  + fileToGenerate));
            String headerCell = "\"Input number " + initialNumber +  " with intial rewrite term " +  initialTerm + RewriteSystemOverallOutputHelper.LS
                    + "Generated final number "+ finalNumber + " with final rewrite term " + finalTerm + RewriteSystemOverallOutputHelper.LS
                    + "Number of steps: " + numSteps + "  Number of array doubles: " + numArrayDoubles +"\"";
            writer.println(headerCell);
            if (opts.isVerbose()) {
                writer.println("Term" + RewriteSystemOverallOutputHelper.CS + "Number");
                for (StringNumPair s : listOfTerms) {
                    writer.println(s.getString() + RewriteSystemOverallOutputHelper.CS + s.getNum());
                }
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Unable to generate output file "  + fileToGenerate);
            System.err.println("Exception caught: " + e);
            System.exit(10);
        }
    }


    public int getNumSteps() {
        return numSteps;
    }

    public long getFinalNumber() {
        return finalNumber;
    }

    public int getNumArrayDoubles() {
        return numArrayDoubles;
    }

    public long getInitialNumber() {
        return initialNumber;
    }
}
