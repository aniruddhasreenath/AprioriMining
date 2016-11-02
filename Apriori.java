import java.util.*;
import java.io.*;


/**
 * Created by sreenath on 2/11/2016.
 */
public class Apriori {

    public ArrayList<String> items;

    public String nameOfTransactionFile;

    public int numDifItems;

    public int totalTransactions;

    public double min_sup;

    public Apriori(String file, double min) throws IOException{

        items = new ArrayList<String>();
        nameOfTransactionFile = file;
        numDifItems = 0;
        totalTransactions = 0;
        min_sup = min;

        calculateNumItems();
        printValues();
    }

    public void calculateNumItems() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(nameOfTransactionFile));
        String data = "";
        while(reader.ready()){
            totalTransactions++;
            data = reader.readLine();

            StringTokenizer word = new StringTokenizer(data," ");
            while (word.hasMoreTokens()) {
                numDifItems++;
                System.out.println(word.nextElement());
            }
        }
    }

    public void printValues(){
        System.out.println("Currently Processing File: " + nameOfTransactionFile);
        System.out.println("Number of items in File: " + numDifItems);
        System.out.println("Total Transactions: " + totalTransactions);
        System.out.println("min sup: " + min_sup);
    }

}
