import java.util.LinkedList;
import java.util.List;

/**
 * Created by mad4672 on 4/14/18.
 */
public class StringRewriteTermFactory {



    public StringRewriteTermFactory() {

    }


    public StringRewriteTerm generateRewriteString(long num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number must be positive.");
        }
        return new StringRewriteTerm(num);
    }


}
