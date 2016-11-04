import java.io.*;
import java.util.ArrayList;

/**
 * Created by sreenath on 3/11/2016.
 */
public class Purity {

    public ArrayList<Item> frequentPatterns;
    public ArrayList<PurityList> patPurity;


    public Purity(String f) throws IOException{
        frequentPatterns = new ArrayList<Item>();
        patPurity = new ArrayList<PurityList>();
        readFile(f);
        calculateMaxPurity();
        printVals();
    }

    public void readFile(String fileName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            String line = reader.readLine();
            String[] tmp;

            while(line != null){

                tmp = line.split("\\s");
                //min_sup = Integer.parseInt(tmp[0]);
                ArrayList<String> pattern = new ArrayList<String>();

                //remove the support at the start for the maximum pattern
                for (int i = 1; i < tmp.length; i++){
                    //create a new item
                    pattern.add(tmp[i]);
                }
                String[] pat = new String[pattern.size()];
                pat = pattern.toArray(pat);
                Item newPat = new Item(pat,0);
                frequentPatterns.add(newPat);
                line = reader.readLine();
            }

        } finally {
            reader.close();
        }
    }

    public void calculateMaxPurity() throws IOException{
        ArrayList<Item> pur;
        int index = 0;
        int loopControl = frequentPatterns.size();

        while(loopControl > 0){

            PurityList patL = new PurityList();

            for(int i = 0; i < 4; i ++){
                // get the counts of the first purity
                pur = calculateFrequent("src/"+"topic-"+Integer.toString(i)+".txt",frequentPatterns);
                //store these into the first list.
                patL.patternOccurancesAccrossTopics.add(frequentPatterns.get(index));
            }
            patPurity.add(patL);
            loopControl--;
            if(index < frequentPatterns.size()){
                index++;
            }
        }

    }

    public void printVals(){

        for (int i = 0; i < patPurity.size(); i++){
            for(int j = 0; j < patPurity.get(i).patternOccurancesAccrossTopics.size(); j++){
                for (int k= 0; k < patPurity.get(i).patternOccurancesAccrossTopics.get(j).pattern.length; k++){
                    System.out.println("PATTERN: "+patPurity.get(i).patternOccurancesAccrossTopics.get(j).pattern[k]
                            +" OCCURED: " + patPurity.get(i).patternOccurancesAccrossTopics.get(j).count
                    + " OCCURED IN TOPIC: " + k);
                }
            }
        }
    }

    public ArrayList<Item> calculateFrequent(String topicFile, ArrayList<Item> FP) throws IOException{
        //System.out.println("Calculating the frequency of pattern at value: " );

        boolean match = false;
        //frequent candidates list
        ArrayList<String[]> freqCand = new ArrayList<String[]>();

        String data = "";
        // read the file and compare every transcartion with the candidate list
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(topicFile)));

        while (reader.ready()){
            // read the transaction in and tokenize it for easy traversal.
            data = reader.readLine();

            //loop over the entier list of candidates and find out if there is a match
            for(int i = 0; i < FP.size(); i++){

                // this will store the patterns in the candidate list
                String[] elementsToMatch;
                elementsToMatch = FP.get(i).pattern;

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

                    FP.get(i).count++;
                }

            }

        }
        return FP;
    }

}
