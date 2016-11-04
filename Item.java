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
        //return the difference between the counts of the two items
        int otherCount = item.count;
        return otherCount - this.count;

    }

}
