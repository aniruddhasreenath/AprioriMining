import com.sun.org.apache.regexp.internal.RE;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.lang.*;

/**
 * Created by sreenath on 27/10/2016.
 */
public class Driver {

    public static void main(String[] args) throws IOException{
        //step 2
        //preprocess();

        //step 3
        Reorganise.setUpReorganiser();
    }

    public static void preprocess() throws IOException{
        //create vocab file
        Dictionary.readFile();
        Dictionary.printDictionay();
        //create title file
        Token.setUpTokens(Dictionary.titles);
        Token.printTokens();
    }

}
