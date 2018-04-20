import java.util.List;

/**
 * Created by mad4672 on 4/14/18.
 */
public class Main {

    public static void main(String[] args) {
        /*
        1. Take some inputs. Not entirely sure what they'll be yet, but I'll start with a number. Possibly a different file for different rewrite systems.
        I can use the  OptionsHelper like idea if I think things will get complicated. It may, given that I might output in various formats.
        2. Convert the number into a binary termination string. Should be as straightforward as an internal conversion, and replacing numbers with characters.
        3. Apply the rules. Keep track of each step and store the steps somewhere. I may do a dump to a file if we get past some threshold.
            3.1 I'll have to write down my thoughts here.
        4. Print out the results. Either a terminal or an output file.
         */
        //I was thinking an interactive console might be a good idea. Also, a batch option might be good too, given the fact that we have several records we want to check all at once.

        Options opts = new Options(args);
        RewriteSystem system = new RewriteSystem(opts);
        StringRewriteTermFactory factory = new StringRewriteTermFactory();
        List<Long> numbers = opts.getInputNumbers();
        RewriteSystemOverallOutputHelper overallOutputHelper = new RewriteSystemOverallOutputHelper(opts);
        for (long i : numbers) {
            StringRewriteTerm term = factory.generateRewriteString(i);
            RewriteSystemTermOutputHelper helper = system.applyRewriteSystem(term);
            helper.generateOutputFile();
            overallOutputHelper.addRowToOverallOutput(helper);
        }
        if (opts.isOutputAggregateFile()) {
            overallOutputHelper.generateOutputFile();
        }
    }

}
