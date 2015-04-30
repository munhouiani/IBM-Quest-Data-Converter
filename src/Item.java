import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhwong on 4/29/15.
 */
public class Item {

    public List<Integer> transId;
    public int itemId;
    public Item() {
        this.transId = new ArrayList<>();
    }
    public Item(int itemId) {
        this.itemId = itemId;
    }

}
