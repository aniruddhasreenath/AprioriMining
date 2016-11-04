import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by sreenath on 27/10/2016.
 */
public class Dictionary {

    public static ArrayList<String> titles = new ArrayList<String>();
    //this stores the content in the vocab file
    public static ArrayList<String> words  = new ArrayList<String>();

    public static void readFile() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/paper.txt"));
        try {
            String line = reader.readLine();
            String[] tmp;

            while(line != null){

               tmp = line.split("\\t");
                if(tmp.length > 1){
                    titles.add(tmp[1]);

                }
                line = reader.readLine();
            }

        } finally {
            reader.close();
        }
        createVocab();
    }

    public static void createVocab(){
        String[] tmp;
       for(int i = 0; i < titles.size(); i++){
           //get the title and split at all the words
           tmp = titles.get(i).split("\\s");
           //add the split words into the words array
           for (int j =0; j < tmp.length; j++){

               if(!wordExists(tmp[j])){
                   words.add(tmp[j]);
               }
           }
       }
    }

    public static boolean wordExists(String word){
        for (int i = 0; i < words.size(); i++){

            if(words.get(i).equals(word)){
                return true;
            }
        }
        return false;
    }

    public static void printDictionay() throws IOException{
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("vocab.txt"), "utf-8"))) {
            for(String tmp : words){
                writer.write(tmp+"\n");
            }

        }
    }


}
