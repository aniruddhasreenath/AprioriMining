import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sreenath on 4/11/2016.
 */
public class Phrases {

    public  HashMap<Integer, String> vocabContent;

    public Phrases(String file) throws IOException{
        vocabContent = new HashMap<Integer, String>();
        generateHashMap();
        createPhraseData(file);
    }

    public  void generateHashMap(){

        for(int i = 0; i < Dictionary.words.size(); i++){
            vocabContent.put(i, Dictionary.words.get(i));
        }
    }

    public  void printPhrases(String file, ArrayList<Item> pattern) throws IOException {

        BufferedWriter file1 = new BufferedWriter(new FileWriter(file+".phrase"));

        for (int i = 0; i < pattern.size(); i ++){

            System.out.print(pattern.get(i).count);
            file1.write(String.valueOf(pattern.get(i).count));
            for (int j = 0; j < pattern.get(i).pattern.length; j++){
                System.out.print(" "+pattern.get(i).pattern[j]);
                file1.write(" "+pattern.get(i).pattern[j]);
            }
            System.out.println();
            file1.write("\n");
        }

        file1.close();

    }

    public  void createPhraseData(String File) throws IOException{

        BufferedReader reader = new BufferedReader(new FileReader(File));
        ArrayList<Item> frequentPatterns = new ArrayList<Item>();

        try {
            String line = reader.readLine();
            String[] tmp;

            while(line != null){

                tmp = line.split("\\s");
                ArrayList<String> pattern = new ArrayList<String>();

                for (int i = 1; i < tmp.length; i++){
                    //create a new item
                    pattern.add(vocabContent.get(Integer.parseInt(tmp[i])));
                }

                //Dictionary.words.get()
                String[] pat = new String[pattern.size()];
                pat = pattern.toArray(pat);
                Item newPat = new Item(pat,Integer.parseInt(tmp[0]));
                frequentPatterns.add(newPat);
                line = reader.readLine();
            }

        } finally {
            reader.close();
        }

        printPhrases(File, frequentPatterns);
    }
}
