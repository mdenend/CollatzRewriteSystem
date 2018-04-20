/**
 * Created by mad4672 on 4/14/18.
 */
public class RewriteRule{
    private String input; //input of rule
    private String output; //output of rule
    private int netSizeChange; //amount the list changes after rule is applied
    private int ternaryPositionChar; //position of last ternary character. -1 if it doesn't exist.
    private int dRuleSize; //0 if we don't contain a d, but contains a length of this string if it is a d rule

    RewriteRule(String input, String output) {
        this.input = input;
        this.output = output;
        netSizeChange = output.length() - input.length();

        ternaryPositionChar = -1;
        dRuleSize = 0;
        for (int i = 0; i < output.length(); i++) {
            char c = output.charAt(i);
            if (c >= 'e' && c <= 'g') {
                ternaryPositionChar = i;
            } else if (c == 'd') {
                dRuleSize = output.length();
            }
        }

    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int getNetSizeChange() {
        return netSizeChange;
    }

    public int getTernaryPositionChar() {
        return ternaryPositionChar;
    }

    public int getdRuleSize() {
        return dRuleSize;
    }
}
