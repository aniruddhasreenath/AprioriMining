import java.util.ArrayList;

/**
 * Created by sreenath on 2/11/2016.
 */
public class Item implements Comparable<Item> {

    public String[] pattern;

    public int count;

    public Item(String[] pat, int c){
        pattern = pat;
        count = c;
    }
    public int compareTo(Item item) {

        int otherCount = item.count;

        //ascending order
        //return this.count - otherCount;

        //descending order
        return otherCount - this.count;

    }

}
