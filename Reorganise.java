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


    public static void setUpReorganiser() throws IOException{

        words = new ArrayList<String>();
        topic_one = new ArrayList<String>();
        topic_two = new ArrayList<String>();
        topic_three = new ArrayList<String>();
        topic_four = new ArrayList<String>();
        topic_five = new ArrayList<String>();

        readAssignments();
        //organise();

    }

    public static void readAssignments() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/word-assignments.dat"));
        try {
            String line = reader.readLine();
            String[] tmp;
            String title = "";
            int loop = 0;

            while(line != null){

                tmp = line.split("\\s");
                //remove the count of terms at the start of the string
                for (int i = 1; i < tmp.length; i++){
                    //words.add(tmp[i]);
                    //call organise here

                    organise(tmp[i]);
                }
                line = reader.readLine();
                printTopics();
                resetLists();
                loop++;
                if(loop==3)
                break;
            }

        } finally {
            reader.close();
        }

    }

    public static void organise(String wrd) throws IOException{

        String[] tmp;
            System.out.println("Word being classified: " + wrd);
            tmp = wrd.split(":");
            if(tmp[1].equals("00")){
                topic_one.add(tmp[0]);
                System.out.print(" Inserted this word into first topic " + wrd + " actally inserted word: " + tmp[0]);

            }
            else if(tmp[1].equals("01")){
                topic_two.add(tmp[0]);
                System.out.print(" Inserted this word into second topic " + wrd + " actally inserted word: " + tmp[0]);

            }
            else if(tmp[1].equals("02")){
                topic_three.add(tmp[0]);
                System.out.print(" Inserted this word into third  topic " + wrd + " actally inserted word: " + tmp[0]);

            }
            else if(tmp[1].equals("03")){
                topic_four.add(tmp[0]);
                System.out.print(" Inserted this word into 4th topic " + wrd + " actally inserted word: " + tmp[0]);

            }
            else if(tmp[1].equals("04")){
                topic_five.add(tmp[0]);
                System.out.print(" Inserted this word into 5th topic " + wrd + " actally inserted word: " + tmp[0]);

            }
            System.out.println();
            System.out.println("===========");
        System.out.println("1: " + topic_one.size());
        System.out.println("2: " + topic_two.size());
        System.out.println("3: " + topic_three.size());
        System.out.println("4: " + topic_four.size());
        System.out.println("5: " + topic_five.size());

    }

    public static void resetLists(){
        topic_one.clear();
        topic_two.clear();
        topic_three.clear();
        topic_four.clear();
        topic_five.clear();
    }

    public static void printTopics() throws IOException{
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("topic-0.txt"), "utf-8"))) {
            for(String tmp : topic_one){
                writer.write(tmp + " ");
            }
            writer.write("\n");

        }

        try (Writer writer1 = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("topic-1.txt"), "utf-8"))) {
            for(String tmp : topic_two){
                writer1.write(tmp + " ");
            }
            writer1.write("\n");

        }

        try (Writer writer2 = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("topic-2.txt"), "utf-8"))) {
            for(String tmp : topic_three){
                writer2.write(tmp + " ");
            }

            writer2.write("\n");
        }

        try (Writer writer3 = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("topic-3.txt"), "utf-8"))) {
            if (!topic_four.isEmpty()){
                for(String tmp : topic_four){
                    writer3.write(tmp+ " ");
                }
                writer3.write("\n");
            }

        }

        try (Writer writer4 = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("topic-4.txt"), "utf-8"))) {
            if (!topic_five.isEmpty()){
                for(String tmp : topic_five){
                    writer4.write(tmp);
                }
                writer4.write("\n");
            }
        }
    }
}
