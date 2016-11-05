import com.sun.org.apache.regexp.internal.RE;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 27/10/2016.
 */
public class Driver {

    public static void main(String[] args) throws IOException{
        //step 2
        preprocess();

        //step 3
        Reorganise.setUpReorganiser();

        //step 4
        runApriori();

        //step 5
        findMaxAndClosedPatterns();

        //step 6
        generatePurity();

        //(Extra Step) RUN PRE-PROCESSING BEFORE RUNNING THIS
        generatePhraseFiles();

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
        Apriori ap1 = new Apriori("topic-0.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-1");
        Apriori ap2 = new Apriori("topic-1.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-2");
        Apriori ap3 = new Apriori("topic-2.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-3");
        Apriori ap4 = new Apriori("topic-3.txt",0.01);

        System.out.println("RUNNING MINING ALGORITHM ON FILE topic-4");
        Apriori ap5 = new Apriori("topic-4.txt",0.01);
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

    public static void generatePhraseFiles()throws IOException{
        System.out.println("GENERATING PHRASES ON ALL PATTERN FILES");
        for(int i = 0; i <= 4; i++){
            Phrases ph1 = new Phrases("pattern-"+Integer.toString(i)+".txt");
        }

        System.out.println("GENERATING PHRASES ON ALL MAX FILES");
        for(int i = 0; i <= 4; i++){
            Phrases ph2 = new Phrases("max-"+Integer.toString(i)+".txt");
        }

        System.out.println("GENERATING PHRASES ON ALL CLOSED FILES");
        for(int i = 0; i <= 4; i++){
            Phrases ph3 = new Phrases("closed-"+Integer.toString(i)+".txt");
        }
        for(int i = 0; i <= 4; i++){
            Phrases ph4 = new Phrases("purity-"+Integer.toString(i)+".txt");
        }

    }

    public static void generatePurity() throws IOException{

        Purity pur1 = new Purity("pattern-0.txt", 0, 9013);
        Purity pur2 = new Purity("pattern-1.txt", 0, 9785);
        Purity pur3 = new Purity("pattern-2.txt", 0, 8378);
        Purity pur4 = new Purity("pattern-3.txt", 0, 9130);
        Purity pur5 = new Purity("pattern-4.txt", 0, 9440);

    }

}
