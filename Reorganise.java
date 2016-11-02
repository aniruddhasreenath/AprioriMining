import java.io.*;
import java.util.ArrayList;

/**
 * Created by sreenath on 1/11/2016.
 */
public class Reorganise {

    public static ArrayList<String> words;
    public static ArrayList<String> topic_one;
    public static ArrayList<String> topic_two;
    public static ArrayList<String> topic_three;
    public static ArrayList<String> topic_four;
    public static ArrayList<String> topic_five;

   public static BufferedWriter file1;
   public static BufferedWriter file2;
   public static BufferedWriter file3;
   public static BufferedWriter file4;
   public static BufferedWriter file5;

    public static void setUpReorganiser() throws IOException{

        words = new ArrayList<String>();
        topic_one = new ArrayList<String>();
        topic_two = new ArrayList<String>();
        topic_three = new ArrayList<String>();
        topic_four = new ArrayList<String>();
        topic_five = new ArrayList<String>();

        file1 = new BufferedWriter(new FileWriter("topic-0.txt"));
        file2 = new BufferedWriter(new FileWriter("topic-1.txt"));
        file3 = new BufferedWriter(new FileWriter("topic-2.txt"));
        file4 = new BufferedWriter(new FileWriter("topic-3.txt"));
        file5 = new BufferedWriter(new FileWriter("topic-4.txt"));

        readAssignments();
    }

    public static void readAssignments() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/word-assignments.dat"));
        try {
            String line = reader.readLine();
            String[] tmp;

            while(line != null){

                tmp = line.split("\\s");
                //remove the count of terms at the start of the string
                for (int i = 1; i < tmp.length; i++){
                    //call organise here
                    organise(tmp[i]);
                }
                line = reader.readLine();
                printTopics();
                resetLists();
            }
            closeFiles();

        } finally {
            reader.close();
        }

    }

    public static void organise(String wrd) throws IOException{

        String[] tmp;
           // System.out.println("Word being classified: " + wrd);
            tmp = wrd.split(":");
            if(tmp[1].equals("00")){
                topic_one.add(tmp[0]);
                //System.out.print(" Inserted this word into first topic " + wrd + " actally inserted word: " + tmp[0]);
            }
            else if(tmp[1].equals("01")){
                topic_two.add(tmp[0]);
                //System.out.print(" Inserted this word into second topic " + wrd + " actally inserted word: " + tmp[0]);
            }
            else if(tmp[1].equals("02")){
                topic_three.add(tmp[0]);
                //System.out.print(" Inserted this word into third  topic " + wrd + " actally inserted word: " + tmp[0]);
            }
            else if(tmp[1].equals("03")){
                topic_four.add(tmp[0]);
                //System.out.print(" Inserted this word into 4th topic " + wrd + " actally inserted word: " + tmp[0]);
            }
            else if(tmp[1].equals("04")){
                topic_five.add(tmp[0]);
                //System.out.print(" Inserted this word into 5th topic " + wrd + " actally inserted word: " + tmp[0]);

            }

    }

    public static void resetLists(){
        topic_one.clear();
        topic_two.clear();
        topic_three.clear();
        topic_four.clear();
        topic_five.clear();
    }

    public static void printTopics() throws IOException{

        if (!topic_one.isEmpty()){
            for (int i = 0; i<topic_one.size(); i++){
                if(i==0) {
                    file1.write(topic_one.get(i));
                }
                else{
                    file1.write(" "+topic_one.get(i));
                }

            }
            file1.write("\n");
        }

        if (!topic_two.isEmpty()){
            for (int i = 0; i<topic_two.size(); i++){
                if(i==0){
                    file2.write(topic_two.get(i));
                }
                else{
                    file2.write(" "+topic_two.get(i));
                }

            }
            file2.write("\n");
        }

        if (!topic_three.isEmpty()){
            for (int i = 0; i<topic_three.size(); i++){
                if(i==0){
                    file3.write(topic_three.get(i));
                }
                else{
                    file3.write(" "+topic_three.get(i));
                }
            }
            file3.write("\n");
        }

        if (!topic_four.isEmpty()){
            for (int i = 0; i<topic_four.size(); i++){
                if(i==0){
                    file4.write(topic_four.get(i));
                }
                else{
                    file4.write(" "+topic_four.get(i));
                }
            }
            file4.write("\n");
        }

        if (!topic_five.isEmpty()){
            for (int i = 0; i<topic_five.size(); i++){
                if(i==0){
                    file5.write(topic_five.get(i));
                }
                else{
                    file5.write(" "+topic_five.get(i));
                }
            }
            file5.write("\n");
        }
    }

    public static void closeFiles() throws IOException{
        file1.close();
        file2.close();
        file3.close();
        file4.close();
        file5.close();
    }
}
