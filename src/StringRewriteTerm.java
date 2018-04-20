/**
 * Created by mad4672 on 4/14/18.
 */
public class StringRewriteTerm {
    private char[] buf;
    private int headIndex;
    private int tailIndex;
    private int doubleArrayCounter;

    //looks correct.
    StringRewriteTerm(long num) {
        String binary = Long.toBinaryString(num);
        buf = new char[binary.length()*4]; //times 4 the binary size initally. The terms will slide around and we'll run out of space
        tailIndex = buf.length - 1;
        buf[tailIndex] = 'd';
        //add all characters except the first 1, which will be a c. Start after the d.
        for (int i = binary.length()-1; i > 0; i--) {
            int bufIndex = buf.length - 1 - (binary.length() - i);//subtract one more off with this 1, as we don't want to overwrite the d.
            switch (binary.charAt(i)) {
                case '0':
                    buf[bufIndex] = 'a';
                    break;
                case '1':
                    buf[bufIndex] = 'b';
                    break;
                default:
                    throw new IllegalArgumentException("Invalid char " + binary.charAt(i) + " in string: " + binary);
            }
        }
        headIndex = buf.length - 1 - binary.length();
        buf[headIndex] = 'c';
        doubleArrayCounter = 0;
    }

    //This method handles the modification of a d rule. We handle this separately because d rules can only shrink, never grow.
    //index not needed, because it will always be from the tail.
    //This looks correct, but I think I should also have an internal index???
    public void modifyStringWithDRule(RewriteRule rule) {
        tailIndex += rule.getNetSizeChange();
        String output = rule.getOutput();

        //write this backwards, since it's from the tail.
        for (int i = output.length() - 1; i >= 0; i--) {
            buf[tailIndex - (output.length() - 1 - i)] = output.charAt(i); //first index will not be changed.
        }

    }

    //we handle all other rules here because they can stay the same size, or grow.
    //Index NEEDS to come from Rewrite System. However, the actual index needs to be hidden from the rewrite system. We should provide the actual index
    //and hide the intricacies of this class.
    public void modifyStringWithOtherRule(int index, RewriteRule rule) {
        int actualIndex = index + headIndex;
        String output = rule.getOutput();
        //actualIndex == headIndex means we have a c character, may have to grow our string. Also change our head index.
        if (actualIndex == headIndex) {
            int newHeadIndex = headIndex - rule.getNetSizeChange();
            if (newHeadIndex < 0) {
                growArray();
            }
            headIndex -= rule.getNetSizeChange();
            for (int i = 0; i < output.length(); i++) {
                buf[headIndex + i] = output.charAt(i);
            }
        } else {
            for (int i = 0; i < output.length(); i++) {
                buf[actualIndex+i] = output.charAt(i);
            }

        }

    }

    //this seems dicey. Need to test when I get the chance.
    //doubles the size of the array, and discards unnecessary dangling 'd' characters. Should only have to be called a couple of times for larger terms.
    private void growArray() {
        char [] newBuf = new char[buf.length*2];

        //old relevant string length is just the tailIndex plus 1. All other 'd' characters are discarded.
        int oldRelevantStringLength = tailIndex + 1;
        int newBufStart = newBuf.length - oldRelevantStringLength;
        System.arraycopy(buf, 0, newBuf, newBufStart, oldRelevantStringLength);//need to subtract one more here, tailIndex accounts for 1.
        buf = newBuf;
        headIndex = newBufStart;
        tailIndex = newBuf.length-1;
        doubleArrayCounter++;
    }

    public int length() {
        return tailIndex - headIndex + 1;
    }

    public String grabSubstring(int index, int subStringLength) {
        StringBuilder builder = new StringBuilder();
        try {
            int actualIndex = index + headIndex;
            if (actualIndex + subStringLength - 1 > tailIndex) {
                System.err.println("Error triggered! Index is " + index + " and length is " + subStringLength);
                System.err.println("Printing internal dump:");
                System.err.println(internalDump());
                throw new IllegalArgumentException("Requested string of " + subStringLength + " length too long.");
            }

            for (int i = actualIndex; i < actualIndex + subStringLength; i++) {
                builder.append(buf[i]);
            }

        } catch (Exception e) {
            System.err.println("Unexpected exception occurred! " + e);
            System.exit(12);
        }
        return builder.toString();
    }

    public String grabSubstringByIndices(int startIndex, int endIndex) {
        return grabSubstring(startIndex, endIndex - startIndex + 1);
    }

    public int getDoubleArrayCounter() {
        return doubleArrayCounter;
    }

    public long convertStringToNumber() {
        long result = 1;
        for (int i = headIndex; i <= tailIndex; i++){
            switch (buf[i]) {
                case 'a':
                    result *= 2;
                    break;
                case 'b':
                    result = result * 2 + 1;
                    break;
                case 'c':
                case 'd':
                    //do nothing, these are placeholders.
                    break;
                case 'e':
                    result *= 3;
                    break;
                case 'f':
                    result = result * 3 + 1;
                    break;
                case 'g':
                    result = result * 3 + 2;
                    break;
                default:
                    throw new IllegalStateException("Unexpected character " + buf[i] + " found in rewrite buffer " + this.toString());
            }
        }
        return result;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = headIndex; i <= tailIndex; i++) {
            stringBuilder.append(buf[i]);
        }
        return stringBuilder.toString();
    }

    private String internalDump() {
        return "Buffer has: " + this.toString() + "\nheadIndex: " + headIndex + " tailIndex: " + tailIndex + " double array counter:" + doubleArrayCounter;
    }

}
