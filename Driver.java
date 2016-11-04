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
        //Reorganise.setUpReorganiser();

        //step 4
        //runApriori();

        //step 5
        findMaxAndClosedPatterns();

    }

    public static void preprocess() throws IOException{
        //create vocab file
        Dictionary.readFile();
        Dictionary.printDictionay();
        //create title file
        Token.setUpTokens(Dictionary.titles);
        Token.printTokens();
    }

    public static void runApriori() throws IOException{
        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-0");
        Apriori ap1 = new Apriori("src/topic-0.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-1");
        Apriori ap2 = new Apriori("src/topic-1.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-2");
        Apriori ap3 = new Apriori("src/topic-2.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-3");
        Apriori ap4 = new Apriori("src/topic-3.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-4");
        Apriori ap5 = new Apriori("src/topic-4.txt",0.02);
    }

    public static void findMaxAndClosedPatterns() throws IOException{
        System.out.println("RUNNING MINING ALGORITHM ON FILE pattern-0");
        Maximum mc1 = new Maximum("pattern-0.txt");

        System.out.println("RUNNING MINING ALGORITHM ON FILE pattern-1");
        Maximum mc2 = new Maximum("pattern-1.txt");

        System.out.println("RUNNING MINING ALGORITHM ON FILE pattern-2");
        Maximum mc3 = new Maximum("pattern-2.txt");

        System.out.println("RUNNING MINING ALGORITHM ON FILE pattern-3");
        Maximum mc4 = new Maximum("pattern-3.txt");

        System.out.println("RUNNING MINING ALGORITHM ON FILE pattern-4");
        Maximum mc5 = new Maximum("pattern-4.txt");

    }

}
