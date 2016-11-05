import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.*;
import java.math.*;

/**
 * Created by sreenath on 3/11/2016.
 */
public class Purity {

    public ArrayList<Item> frequentPatterns;
    public ArrayList<Integer> secondTermPurity;

    public ArrayList<Item> patternsRankedByPurity;

    public int valueOfT;

    public double valueModDT;

    public static BufferedWriter printer;

    // |D(t,t`)| (stores all the values for this)
    public HashMap<String,Integer> topicUnions;

    //this stores all supports in all topics [the key is t value + the pattern]
    public HashMap<String, Integer> supports;


    public Purity(String f, int tval, int modT) throws IOException{
        frequentPatterns = new ArrayList<Item>();
        topicUnions = new HashMap<String, Integer>();
        patternsRankedByPurity = new ArrayList<Item>();
        supports = new HashMap<String, Integer>();
        secondTermPurity = new ArrayList<Integer>();
        printer = new BufferedWriter(new FileWriter(generateFileName(f)));
        valueOfT = tval;
        valueModDT = modT;
        getTopic(f);

        frequentPatterns = convertContentOfFilesToListsOfPatterns(f);
        System.out.println("CALCULATING |D(t,t`)| FOR ALL t and t`...");
        generateTopicUnions();
        System.out.println(topicUnions.toString());

        System.out.println("CALCULATING |f(t`,p)| FOR ALL t` and p...");
        generateFrequencies();
        System.out.println(supports.toString());

        System.out.println("Listing 2nd Vales");
        calculatePurity();
        System.out.println(secondTermPurity.toString());

    }

    public void calculatePurity() throws IOException{
        ArrayList<Double> valuesOfSecondTerm = new ArrayList<Double>();

        //f(t,p)
        double valueFTP = 0;

        //f(t`,p)
        int valueNotTP = 0;

        //|D(t,t`)|
        int valueDT = 0;

        double vall = 0.0;

        double fin = 0.0;

        double maxSecondT = 0.0;

        double firstT = 0.0;

        //get the first pattern
        for(int i = 0; i < frequentPatterns.size(); i ++){

                //f(t,p)
                valueFTP = supports.get(Integer.toString(valueOfT)+getKey(frequentPatterns.get(i).pattern));

                // the second term computed for every t and t`
                for(int k = 0; k < getNotT(valueOfT).length; k++){

                    valueNotTP = supports.get(Integer.toString(getNotT(valueOfT)[k])+getKey(frequentPatterns.get(i).pattern));
                    valueDT = topicUnions.get(Integer.toString(valueOfT)+Integer.toString(getNotT(valueOfT)[k]));
                    vall = (valueFTP + valueNotTP);
                    //System.out.println("f(t,p) + f(t`p): "+vall);
                    //System.out.println("Dt "+ valueDT);
                    fin = vall/valueDT;
                    //System.out.println("fin: " + fin);
                    valuesOfSecondTerm.add(fin);

                }

            //the max of all the second term values for this pattern
            //secondTermPurity.add(getMax(valuesOfSecondTerm));
            //valuesOfSecondTerm.clear();

            maxSecondT = getMax(valuesOfSecondTerm);
            double mx = Math.log(maxSecondT)/Math.log(2);
            valuesOfSecondTerm.clear();
            double fx = valueFTP/valueModDT;
            firstT = Math.log(fx)/Math.log(2);

            double purity = fx - mx;
            //System.out.println("Purity: ~~~~   " + (purity));
            int nct =  frequentPatterns.get(i).count * (int) purity;
            //System.out.println("NEW COUNT: " + nct);
            Item pur = new Item(frequentPatterns.get(i).pattern, nct);
            patternsRankedByPurity.add(pur);

        }

        printToFile();
        printer.close();

    }

    public double getMax(ArrayList<Double> a){
        double max = 0;

        for(int i = 0; i < a.size(); i ++){
            if(a.get(i) > max){

                max = a.get(i);
                //System.out.println("value "+a.get(i));
                //System.out.println("MAX: "+max);
            }

        }

        return max;

    }

    public int[] getNotT(int t){

        if (t == 0){
            int[] tmp = {1,2,3,4};
            return tmp;
        }
        if (t == 1){
            int[] tmp = {0,2,3,4};
            return tmp;
        }
        if (t == 2){
            int[] tmp = {1,0,3,4};
            return tmp;
        }
        if (t == 3){
            int[] tmp = {1,2,0,4};
            return tmp;
        }
        if (t == 4){
            int[] tmp = {1,2,3,0};
            return tmp;
        }
        System.out.println("MATCH NOT FOUND");
        return null;

    }

    public ArrayList<Item> convertContentOfFilesToListsOfPatterns(String fileName) throws IOException {

        ArrayList<Item> filePattern = new ArrayList<Item>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            String line = reader.readLine();
            String[] tmp;

            while(line != null){

                tmp = line.split("\\s");
                //min_sup = Integer.parseInt(tmp[0]);
                ArrayList<String> pattern = new ArrayList<String>();

                //transfer with the support for the pattern files
                if(fileName.contains("pattern")){
                    for (int i = 1; i < tmp.length; i++){
                        //create a new item
                        pattern.add(tmp[i]);
                    }
                    String[] pat = new String[pattern.size()];
                    pat = pattern.toArray(pat);
                    Item newPat = new Item(pat,Integer.parseInt(tmp[0]));
                    filePattern.add(newPat);
                    line = reader.readLine();
                }
                else{
                    for (int i = 0; i < tmp.length; i++){
                        //create a new item
                        pattern.add(tmp[i]);
                    }
                    String[] pat = new String[pattern.size()];
                    pat = pattern.toArray(pat);
                    Item newPat = new Item(pat,0);
                    filePattern.add(newPat);
                    line = reader.readLine();

                }

            }

        } finally {
            reader.close();
        }

        return filePattern;
    }


    public String getTopic(String f){

        if(f.contains("0")){
            return "topic-0.txt";
        }
        if(f.contains("1")){
            return "topic-1.txt";
        }
        if(f.contains("2")){
            return "topic-2.txt";
        }
        if(f.contains("3")){
            return "topic-3.txt";
        }
        if(f.contains("4")){
            return "topic-4.txt";
        }

        System.out.println("FILE NOT FOUND!");
        return "topic-0.txt";

    }

    public int calculateFrequent(String[] pattern, String file) throws IOException{

        boolean match = false;
        int frequency = 0;

        String data = "";
        // read the file and compare every transcartion with the candidate list
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        while (reader.ready()){
            // read the transaction in and tokenize it for easy traversal.
            data = reader.readLine();

                //if the pattern exists withing the read line then keep change match to true
                for(int j = 0; j < pattern.length; j++){

                    if(data.contains(pattern[j])){

                        match = true;
                    }
                    else{
                        //System.out.println("Data line: " + data + "  MISS matching: " + elementsToMatch[j]);
                        match = false;
                        break;
                    }
                }

                // if the pattern was found after the comparison of the entire patter then increment the count
                if(match){
                    frequency++;
                }
        }

        return frequency;
    }

    public void generateFrequencies() throws IOException{
        int freq = 0;
        for(int i = 0; i <= 4; i ++){
            for(int j = 0; j < frequentPatterns.size(); j++){
                freq = calculateFrequent(frequentPatterns.get(j).pattern,getTopic(Integer.toString(i)));
                // add those supports to the hashmap
                supports.put(Integer.toString(i)+getKey(frequentPatterns.get(j).pattern),freq);
            }
        }

    }

    public String getKey(String[] a){
        String tmp = "";
        for(int i = 0 ; i  < a.length; i++){
            tmp = tmp + a[i];
        }
        return tmp;
    }

    public void generateTopicUnions() throws IOException{

        ArrayList<Item> firstTopic = new ArrayList<Item>();
        ArrayList<Item> secondTopic = new ArrayList<Item>();
        int unionValue = 0;

        //combine two files and then call remove duplicate
        for(int i = 0; i <=4; i++){
            for (int j =i+1; j<=4; j++){

                if(i != j){
                    firstTopic = convertContentOfFilesToListsOfPatterns(getTopic(Integer.toString(i)));
                    secondTopic = convertContentOfFilesToListsOfPatterns(getTopic(Integer.toString(j)));
                    unionValue = performUnion(firstTopic,secondTopic);

                    //add to hashmap this will store the union of t and t`
                    topicUnions.put(Integer.toString(i)+Integer.toString(j),unionValue);
                    topicUnions.put(Integer.toString(j)+Integer.toString(i),unionValue);
                }

            }
        }

    }

    public int performUnion(ArrayList<Item> a, ArrayList<Item> b){
        ArrayList<Item> uni = new ArrayList<Item>();
        for(int i = 0; i < b.size(); i++){
            a.add(b.get(i));
        }
        uni = removeDuplicatesInCandidateList(a);


        return uni.size();

    }

    public ArrayList<Item> removeDuplicatesInCandidateList(ArrayList<Item> union){

        for (int i = 0; i < union.size(); i++){

            for (int j = i+1; j < union.size(); j++){
                if(Arrays.equals(union.get(i).pattern, union.get(j).pattern)){
                    union.remove(j);
                }
            }
        }

        return union;

    }

    public void printToFile() throws IOException{

        //sort in the correct order before printing
        setupFormat();
        for (int i = 0; i < patternsRankedByPurity.size(); i ++){

            printer.write(patternsRankedByPurity.get(i).count + " ");
            System.out.print(patternsRankedByPurity.get(i).count +" ");
            for (int j = 0; j < patternsRankedByPurity.get(i).pattern.length; j++){
                if (j ==0){
                    System.out.print(patternsRankedByPurity.get(i).pattern[j]);
                    printer.write(patternsRankedByPurity.get(i).pattern[j]);
                }
                else{
                    System.out.print(" "+patternsRankedByPurity.get(i).pattern[j]);
                    printer.write(" "+patternsRankedByPurity.get(i).pattern[j]);
                }

            }
            printer.newLine();
            System.out.println();

        }
    }

    public void setupFormat(){

        Item[] sortpats =  new Item[patternsRankedByPurity.size()];
        sortpats = patternsRankedByPurity.toArray(sortpats);
        Arrays.sort(sortpats);
        ArrayList<Item> sortedListOfPatterns = new ArrayList<Item>(Arrays.asList(sortpats));
        patternsRankedByPurity = sortedListOfPatterns;
    }

    public String generateFileName(String f){
        if(f.contains("0")){
            return "purity-0.txt";
        }
        else if(f.contains("1")){
            return "purity-1.txt";
        }
        else if(f.contains("2")){
            return "purity-2.txt";
        }
        else if(f.contains("3")){
            return "purity-3.txt";
        }
        else if(f.contains("4")){
            return "purity-4.txt";
        }

        return "pattern-def.txt";
    }

}
