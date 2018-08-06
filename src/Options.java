import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mad4672 on 4/16/18.
 */
public class Options {

    //default value for fields initialized up here instead of in constructor.

    private List<Long> inputNumbers;
    private String outputDirectory;
    private boolean isVerbose;
    private String rewriteSystemFile;
    private boolean outputAggregateFile;

    /**
     * Possible options are:
     * -inputfile FILENAME: provides an input file that has numbers of input.
     *      Format for inputfile input: Uses Java's scanner, so any number wtoken ill be read as valid input, skipping anything else.
     * -inputnumber NUMBER: plug in one number and generate the output of it.
     * -outputdirectory DIRECTORY: generates output into the given directory. If nothing, default will be Output in the local directory. If directory doesn't exist, gives an error.
     * -verbose: prints out lines which correspond to the rewrite rules and the numbers they represent as we generate the output files.
     * -rewritesystem FILENAME: takes in a text file in FILENAME representing a different system. If not provided, will default to BasicRewriteRules.txt, which is the default system.
     *      Format for rewrite input:
     *          -"INPUT OUTPUT" on a line denotes rule is to find a string INPUT and convert it to string OUTPUT.
     *          -Any line beginning with a # is a comment, and will not be included.
     */
    public Options (String[] args) {
        inputNumbers = new ArrayList<>();
        outputDirectory = "Output";
        isVerbose = false;
        rewriteSystemFile = "Rewrite_Systems/BasicRewriteRules.txt";
        outputAggregateFile = false;


        for (int i = 0; i < args.length; i++) {
            String option = args[i];
            if (option.startsWith("-")) {
                String substring = option.substring(1).toLowerCase();
                switch (substring) {
                    case "inputfile":
                        try {
                            File file = new File(args[i+1]);
                            Scanner s = new Scanner(file);
                            while (s.hasNextLong()) {
                                long numToAdd = s.nextLong();
                                inputNumbers.add(numToAdd);
                            }
                        } catch (Exception e) {
                            System.err.println("Unable to process file" + args[i+1] + ": Exception " + e + " caught.");
                            System.exit(5);
                        }
                        i++;
                        break;
                    case "inputnumber":
                        try {
                            long numToAdd = Long.parseLong(args[i+1]);
                            inputNumbers.add(numToAdd);
                        } catch (Exception e) {
                            System.err.println("Unable to parse string " + args[i+1] + " as a number.");
                            System.exit(6);
                        }
                        i++;
                        break;
                    case "outputdirectory":
                        File file = new File (args[i+1]);
                        if (!file.isDirectory()){
                            System.err.println("File " + file + " is not a valid directory. Please create it and try again.");
                            System.exit(7);
                        }
                        outputDirectory = args[i+1];
                        i++;
                        break;
                    case "verbose":
                        isVerbose = true;
                        break;
                    case "rewritesystem":
                        rewriteSystemFile = args[i+1];
                        i++;
                        break;
                    case "aggregatefile":
                        outputAggregateFile = true;
                        break;
                    default:
                        System.err.println("Unrecognized option given: " + substring);
                        System.exit(3);
                }
            } else { //for now, no valid option doesn't start with a hyphen
                System.err.println("Invalid option detected: " + args[i]);
                System.exit(8);
            }

        }

        if (inputNumbers.isEmpty()) {
            System.err.println("No input numbers detected. Need at least one number to run rewrite system on.");
            System.exit(4);
        }
    }

    public boolean isVerbose() {
        return isVerbose;
    }

    public List<Long> getInputNumbers() {
        return inputNumbers;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getRewriteSystemFile() {
        return rewriteSystemFile;
    }

    public boolean isOutputAggregateFile() {
        return outputAggregateFile;
    }
}
