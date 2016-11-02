import java.util.ArrayList;
import java.io.IOException;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sreenath on 30/10/2016.
 */
public class Token {

    public static ArrayList<String> tokens;
    public static ArrayList<String> titles;
    public static HashMap<String, Integer> word;


    public static void setUpTokens(ArrayList<String> title){
        titles = new ArrayList<String>();
        tokens = new ArrayList<String>();
        word = new HashMap<String, Integer>();

        titles = title;
        createMappingToVocab();
        tokenise();
    }

    public static void tokenise(){

        String[] tmp;
        String tmp2 = "";
        String token = "";
        int MVal = 0;
        String value = "";

        for(int i = 0; i < titles.size(); i++){
            //get the title and split at all the words
            tmp = titles.get(i).split("\\s");

            for (int j =0; j < tmp.length; j++){
                tmp2 = (" " + word.get(tmp[j]) + ":" + occurances(tmp,tmp[j]));
                //if this word is already counted do not add it again
                if(!token.contains(tmp2)){
                    token = token + tmp2;
                    MVal++;

                }
                value = token;

            }
            //create the token
            token = MVal+""+value;
            //add the complete token to the tokens array
            tokens.add(token);
            //reset temp variables
            token = "";
            MVal = 0;
        }


    }

    public static int occurances(String[] wordsInTitle, String word){
        int count = 0;
        ArrayList<String> title  = new ArrayList<String>(Arrays.asList(wordsInTitle));
        boolean found = false;
        for (int i = 0; i < title.size(); i++){
            if (title.get(i).equals(word)){
                count = count +1;
                //the second time the word is found delete the occurance
                if(found){
                    //remove and reindex
                    title.remove(i);
                }
                found = true;
            }
        }
        return count;
    }

    public static void printTokens() throws IOException{
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("title.txt"), "utf-8"))) {
            for(String tmp : tokens){
                writer.write(tmp+"\n");
            }

        }
    }

    public static void createMappingToVocab(){
        for (int  i = 0; i < Dictionary.words.size(); i++){
            word.put(Dictionary.words.get(i), i);
        }
    }
}
