import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sreenath on 3/11/2016.
 */
public class Maximum {


    public ArrayList<Item> frequentPatterns;
    public ArrayList<Item> maxPatterns;
    public ArrayList<Item> closedPatterns;

    public static BufferedWriter printer1;
    public static BufferedWriter printer2;

    public String patternFileName;

    //public int min_sup;



    public Maximum(String name)throws  IOException{
        frequentPatterns = new ArrayList<Item>();
        maxPatterns = new ArrayList<Item>();
        printer1 = new BufferedWriter(new FileWriter(generateMaxFileName(name)));
        printer2 = new BufferedWriter(new FileWriter(generateMinFileName(name)));
        closedPatterns = new ArrayList<Item>();
        //topicFileName = topic;
        //min_sup = 0;
        patternFileName = name;
        readFile(name);
        findMaxPatterns();
        findCLosedPatterns();
        printValuesMax();
        printValesClo();
        //printValues();
        printer1.close();
        printer2.close();
    }

    public String generateMaxFileName(String f){
        if(f.contains("0")){
            return "max-0.txt";
        }
        else if(f.contains("1")){
            return "max-1.txt";
        }
        else if(f.contains("2")){
            return "max-2.txt";
        }
        else if(f.contains("3")){
            return "max-3.txt";
        }
        else if(f.contains("4")){
            return "max-4.txt";
        }

        return "max-def.txt";
    }

    public String generateMinFileName(String f){
        if(f.contains("0")){
            return "closed-0.txt";
        }
        else if(f.contains("1")){
            return "closed-1.txt";
        }
        else if(f.contains("2")){
            return "closed-2.txt";
        }
        else if(f.contains("3")){
            return "closed-3.txt";
        }
        else if(f.contains("4")){
            return "closed-4.txt";
        }

        return "closed-def.txt";
    }

    public void readFile(String fileName) throws IOException{

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
                Item newPat = new Item(pat,Integer.parseInt(tmp[0]));
                frequentPatterns.add(newPat);
                line = reader.readLine();
            }

        } finally {
            reader.close();
        }
    }

    public void printValuesMax() throws  IOException{
        removeDuplicatesInCandidateList();
        for (int i = 0; i < maxPatterns.size(); i ++){

            System.out.print(maxPatterns.get(i).count);
            printer1.write(String.valueOf(maxPatterns.get(i).count));
            for (int j = 0; j < maxPatterns.get(i).pattern.length; j++){
                System.out.print(" "+maxPatterns.get(i).pattern[j]);
                printer1.write(" "+maxPatterns.get(i).pattern[j]);
            }
            System.out.println();
            printer1.write("\n");
        }
        System.out.println("MAX LENGTH: " + maxPatterns.size() + " FREQ LENGTH: " + frequentPatterns.size());
    }

    public void printValesClo() throws IOException{
        removeDuplicatesInCandidateList();
        for (int i = 0; i < closedPatterns.size(); i ++){

            System.out.print(closedPatterns.get(i).count);
            printer2.write(String.valueOf(closedPatterns.get(i).count));
            for (int j = 0; j < closedPatterns.get(i).pattern.length; j++){
                System.out.print(" "+closedPatterns.get(i).pattern[j]);
                printer2.write(" "+closedPatterns.get(i).pattern[j]);
            }
            System.out.println();
            printer2.write("\n");
        }
        System.out.println("MAX LENGTH: " + closedPatterns.size() + " FREQ LENGTH: " + frequentPatterns.size());

    }

    public void findMaxPatterns()throws IOException{

        boolean foundSuperSet = false;
        //run through pattern array
        for(int i = 0; i < frequentPatterns.size(); i++){
            //select a pattern
            String[] pat = frequentPatterns.get(i).pattern;

           for(int j = i +1; j < frequentPatterns.size(); j++){
               String[] patOther = frequentPatterns.get(j).pattern;

                   if(isSubset(pat,patOther)){
                       foundSuperSet = true;
                   }
                   else{
                       foundSuperSet = false;
                       maxPatterns.add(frequentPatterns.get(i));
                   }

           }
            removeDuplicatesInCandidateList();
        }

    }

    public void findCLosedPatterns()throws IOException{

        boolean foundSuperSet = false;
        //run through pattern array
        for(int i = 0; i < frequentPatterns.size(); i++){
            //select a pattern
            String[] pat = frequentPatterns.get(i).pattern;

            for(int j = i +1; j < frequentPatterns.size(); j++){
                String[] patOther = frequentPatterns.get(j).pattern;

                if (frequentPatterns.get(i).count == frequentPatterns.get(j).count) {
                    if(isSubset(pat,patOther)){
                        foundSuperSet = true;
                    }
                    else{
                        foundSuperSet = false;
                        closedPatterns.add(frequentPatterns.get(i));
                    }
                }



            }
            removeDuplicatesInCandidateList();

        }

    }

    public boolean isSubset(String[] a, String[] b){
        boolean isSubset = false;
        int lenA = a.length;
        int lenB = b.length;

        if (lenA >= lenB){
            return false;
        }
        else{
            for (int i = 0; i < lenA; i++){
                String word = a[i];
                for (int j = 0; j < lenB; j++){
                    if (word.equals(b[j])){
                        isSubset = true;
                        break;
                    }
                    else{
                        isSubset = false;
                    }
                }

            }
        }

        return isSubset;
    }

    public void removeDuplicatesInCandidateList(){

        for (int i = 0; i < maxPatterns.size(); i++){

            for (int j = i+1; j < maxPatterns.size(); j++){
                if(Arrays.equals(maxPatterns.get(i).pattern, maxPatterns.get(j).pattern)){
                    maxPatterns.remove(j);
                }
            }
        }

    }




}
