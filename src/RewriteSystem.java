import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mad4672 on 4/14/18.
 */
public class RewriteSystem {
    private Map<String, RewriteRule> map;//contains references to the rules
    private int maxDRuleSize;//largest d rule size, allows for termination during d phase
    private Options opts;



    public RewriteSystem(Options opts) {
        //for now, just read from the static text file.
        this.opts = opts;
        map = new HashMap<>();
        String fileName = opts.getRewriteSystemFile();
        maxDRuleSize = 0;

        try {
            File file = new File(fileName);
            Scanner s = new Scanner(file);
            while(s.hasNextLine()) {
                String line = s.nextLine();
                if (line.startsWith("#")) {
                    continue;
                }
                String[] splitLine = line.split(" ");
                RewriteRule rule = new RewriteRule(splitLine[0], splitLine[1]);
                map.put(splitLine[0], rule);
                if (rule.getdRuleSize() > maxDRuleSize) {
                    maxDRuleSize = rule.getdRuleSize();
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " + fileName);
            System.exit(2);
        }

    }


    public RewriteSystemTermOutputHelper applyRewriteSystem(StringRewriteTerm term) {
        RewriteSystemTermOutputHelper helper = new RewriteSystemTermOutputHelper(term, opts);
        boolean isTerminated = false;
        int numRulesApplied = 0;
        //keep running until we've hit a termination condition for the loop to stop.
        while (!isTerminated) {
            int termLength = term.length(); //length of the term we're trying to rewrite.
            int moveTernaryStartIndex = -1;  //denotes where to start shifting the ternary character, if it exists.

            //1. Phase 1. Start with the end of the string. Apply the d rules over and over again.
            boolean foundDRule = false;
            int intialIndex = termLength - 1;
            int currentIndex = intialIndex;
            while (!foundDRule && !isTerminated) {
                String inputString = term.grabSubstringByIndices(currentIndex, intialIndex);

                // two conditions for immediate termiation: 1. No d rules fit what we have. 2. The currentIndex is out-of-bounds.
                if (inputString.length() > maxDRuleSize) {
                    isTerminated = true;
                }

                //have the key? apply the rule to the term and find if a ternary character is there.
                else if (map.containsKey(inputString)) {
                    RewriteRule rule = map.get(inputString);
                    foundDRule = true;
                    numRulesApplied++;

                    term.modifyStringWithDRule(rule);
                    if (rule.getTernaryPositionChar() >= 0) {
                        moveTernaryStartIndex = currentIndex + rule.getTernaryPositionChar(); //this is the ENDING index we start the swapping with.
                    }

                    if (opts.isVerbose()) {
                        helper.addTermToVerboseList(term);
                    }
                }
                currentIndex--; //move current index back by 1, try larger string if we need to. If the index is smaller than 0, we terminate.
                if (currentIndex < 0) {
                    isTerminated = true;
                }

            }
            //2. Phase 2. If a ternary character is found, we move it until we get to the front.
            if (moveTernaryStartIndex >= 0) {

                //start the for loop at the character before the ternary character.
                for (int i = moveTernaryStartIndex - 1; i >= 0; i--) {
                    String input = term.grabSubstring(i, 2);
                    //we NEED to have these rules present. If we don't, there's something wrong. Throw exception.
                    if (!map.containsKey(input)) {
                        throw new IllegalStateException("Unexpected term found as input: " + input + ". Unable to perform ternary character move.");
                    }
                    numRulesApplied++;
                    RewriteRule rule = map.get(input);
                    term.modifyStringWithOtherRule(i, rule);
                    if (opts.isVerbose()) {
                        helper.addTermToVerboseList(term);
                    }

                }

            }

        }

        helper.finalizeOutput(term, numRulesApplied, term.getDoubleArrayCounter());
        return helper;
    }

}





