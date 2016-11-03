import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.util.*;
import java.io.*;


/**
 * Created by sreenath on 2/11/2016.
 */
public class Apriori {

    public ArrayList<Item> items;

    public ArrayList<String> words;

    public String nameOfTransactionFile;

    public int numDifItems;

    public int totalTransactions;

    public int min_sup;

    public Apriori(String file, double min) throws IOException{

        items = new ArrayList<Item>();
        nameOfTransactionFile = file;
        numDifItems = 0;
        totalTransactions = 0;
        words = new ArrayList<String>();
        calculateNumItems();
        Double tmp;
        tmp = min * totalTransactions;
        min_sup = tmp.intValue();
        printValues();
        mine();

    }

    public void calculateNumItems() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(nameOfTransactionFile));
        String data = "";
        String token = "";
        while(reader.ready()){
            totalTransactions++;
            data = reader.readLine();

            StringTokenizer word = new StringTokenizer(data," ");
            while (word.hasMoreTokens()) {
                numDifItems++;
                token = word.nextToken();
                words.add(token);
            }
        }
    }

    public void printValues(){

        for (int i = 0 ; i < words.size(); i++){
          //  System.out.println("ADDED WORDS: "+words.get(i));
        }

        for (int i = 0 ; i < items.size(); i++){
         //   System.out.println("ADDED WORDS: "+words.get(i));
        }

        System.out.println();
        System.out.println("Currently Processing File: " + nameOfTransactionFile);
        System.out.println("Number of items in File: " + numDifItems);
        System.out.println("Total Transactions: " + totalTransactions);
        System.out.println("min sup: " + min_sup);


    }

    public void printPatterns(){
        for (int i = 0; i < items.size(); i ++){

            System.out.print("PATTERN TO MATCH: ");
            for (int j = 0; j < items.get(i).pattern.length; j++){
                System.out.print(" "+items.get(i).pattern[j]);
            }
            System.out.print(" COUNT: " + items.get(i).count);
            System.out.println();
        }

        System.out.println("MATCHED PATTERNS: " + items.size() + " Total Transactions: " + totalTransactions);
    }

    public void mine() throws IOException{

        //create the first itemset with all the items
        createFirstList();

        calculateFrequent();
        removeItemsBelowMinSupport();
       // printPatterns();

        //second iteration this is where k = 1 meaning that the size of the pattern is 1
        createOtherCandidateLists(1);
        calculateFrequent();
        //removeItemsBelowMinSupport();
         printPatterns();

        //createOtherCandidateLists(2);
        //calculateFrequent();
        //removeItemsBelowMinSupport();
       // printPatterns();


    }



    public void createFirstList(){

        for(int i = 0;i< numDifItems; i++){
            String[] list = {words.get(i)};
            Item pattern = new Item(list, 0);
            items.add(pattern);
        }

        //remove duplicates added to list
        removeDuplicatesInCandidateList();
    }

    public void createOtherCandidateLists(int k){

        ArrayList<Item> newPat = new ArrayList<Item>();
        ArrayList<String> wordCombo = new ArrayList<String>();

        if (k == 1){
            //loop through the list of patterns
            for(int i = 0; i < items.size(); i ++){
                String word = items.get(i).pattern[0];
                wordCombo.add(word);

                //for every pattern loop thorugh all the other patterns to create combos
                for (int j = i +1; j < items.size(); j++){
                        String word2 = items.get(j).pattern[0];
                        wordCombo.add(word2);

                        //conver pattern into an array
                        String[] arr = new String[wordCombo.size()];
                        arr = wordCombo.toArray(arr);

                        //add this array to the new item and set count to 0
                        Item pattern = new Item(arr, 0);
                        newPat.add(pattern);

                        //clear wordscombo
                        wordCombo.remove(1);
                }
                wordCombo.clear();
            }

            items = newPat;
            removeDuplicatesInCandidateList();
        }

        else{

            for(int i = 0; i < items.size(); i ++){
                ArrayList<String> patternToMatch = new ArrayList<String>();
                ArrayList<String> newCandidate = new ArrayList<String>();
                //pick the k-1th pattern in the current patterns
                for(int l = 0; l < k-1; l ++){
                    patternToMatch.add(items.get(i).pattern[l]);
                }

                //check that the same patterns exist in all the other patterns in list
                for(int j = i+1; j < items.size(); j++){

                    boolean comp = false;
                    for(int m = 0; m < k-1; m ++){
                        if(patternToMatch.get(m).equals(items.get(j).pattern[m])){
                            //System.out.println("Matching: " + patternToMatch.get(m) + "with: " + items.get(j).pattern[m]);
                            comp = true;
                        }
                        else{
                            comp = false;
                        }
                    }
                    //found a matching pattern
                    if(comp){

                        //add all the values to the new candidate
                        for (int n = 0; n < k; n ++){
                                newCandidate.add(items.get(i).pattern[n]);
                           // System.out.print(" " + items.get(i).pattern[n]);

                        }
                        newCandidate.add(items.get(j).pattern[k-1]);
                        //System.out.print(" "+newCandidate.get(newCandidate.size() -1));

                        //System.out.println();
                        //convert pattern into an array
                        String[] arr = new String[newCandidate.size()];
                        arr = newCandidate.toArray(arr);

                        //add this array to the new item and set count to 0
                        Item pattern = new Item(arr, 0);
                        newPat.add(pattern);

                       // System.out.println(newCandidate.size());

                        //clear wordscombo
                        newCandidate.clear();
                    }
                }
            }
            items = newPat;
            removeDuplicatesInCandidateList();
        }


    }
    public void removeItemsBelowMinSupport(){

        ArrayList<Item> tmp = new ArrayList<Item>();

            for(int i = 0; i < items.size(); i++){
                if(items.get(i).count < min_sup){
                    //System.out.println("Removed: " + items.get(i).pattern[0] + " with count: " + items.get(i).count);
                }
                else{
                    tmp.add(items.get(i));
                }
            }

            items = tmp;
    }

    public void calculateFrequent() throws IOException{
        //System.out.println("Calculating the frequency of pattern at value: " );

        boolean match = false;
        //frequent candidates list
        ArrayList<String[]> freqCand = new ArrayList<String[]>();

        String data = "";
        // read the file and compare every transcartion with the candidate list
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nameOfTransactionFile)));

        while (reader.ready()){
            // read the transaction in and tokenize it for easy traversal.
            data = reader.readLine();

                //loop over the entier list of candidates and find out if there is a match
                for(int i = 0; i < items.size(); i++){

                    // this will store the patterns in the candidate list
                    String[] elementsToMatch;
                    elementsToMatch = items.get(i).pattern;

                    //if the pattern exists withing the read line then keep change match to true
                    for(int j = 0; j < elementsToMatch.length; j++){
                        //System.out.println(elementsToMatch[j]);
                        if(data.contains(elementsToMatch[j])){

                            match = true;
                        }
                        else{
                            //System.out.println("Data line: " + data + "  MISS matching: " + elementsToMatch[j]);
                            match = false;
                            break;
                        }
                    }

                    // if the pattern was found after the comparison of the entire patter then increment the count
                   // System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
                    if(match){
                        if (elementsToMatch.length > 1){
                            for (int v = 0; v < elementsToMatch.length; v++){
                                //System.out.print(" Data line: " + data + " matching ITEM : " + elementsToMatch[v] );
                            }
                           // System.out.println();
                        }
                       // System.out.println("==================================================");

                        items.get(i).count++;
                    }



            }
        }

    }

    public void removeDuplicatesInCandidateList(){

        for (int i = 0; i < items.size(); i++){

            for (int j = i+1; j < items.size(); j++){
                if(Arrays.equals(items.get(i).pattern, items.get(j).pattern)){
                    items.remove(j);
                }
            }
        }

    }

}
