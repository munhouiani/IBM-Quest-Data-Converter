import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by mhwong on 4/29/15.
 */
public class ConvertIbmToWekaCsv {

    public ConvertIbmToWekaCsv(String input, String output) {
        convertIbmToWekaCsv(input, output);
    }

    public void convertIbmToWekaCsv(String input, String output) {
        try {
            InputStream input_file = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(input_file, Charset.forName("ascii"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(output, "ascii");
            String input_line;
            int lastTransId = 0;
            List<Item> itemList = new ArrayList<>();
            while((input_line = bufferedReader.readLine()) != null) {
                int custId, transId, itemId;
                Scanner scanner = new Scanner(new ByteArrayInputStream(input_line.getBytes()));
                custId = scanner.nextInt();
                transId = scanner.nextInt();
                itemId = scanner.nextInt();
                lastTransId = transId;

                // a comparator to compare item in item list
                Comparator<Item> comparator = new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return o1.itemId - o2.itemId;
                    }
                };

                // always sort first
                Collections.sort(itemList, comparator);

                // find if an item exists
                int position = Collections.binarySearch(itemList, new Item(itemId), comparator);

                // found
                if(position >= 0) {
                    itemList.get(position).transId.add(transId);
                }
                // not found
                else {
                    Item item = new Item();
                    item.itemId = itemId;
                    item.transId.add(transId);
                    itemList.add(item);
                }
                scanner.close();
            }


            // sort transaction list
            for(Item i : itemList) {
                Collections.sort(i.transId);
            }


            // print item list
            int index;
            for(index = 0; index != itemList.size() - 1; index++) {
                printWriter.printf("'%d',",itemList.get(index).itemId);
            }
            printWriter.printf("'%d'\n",itemList.get(index).itemId);

            // print transaction list
            for(int currentTransId = 1; currentTransId <= lastTransId; currentTransId++) {
                for(index = 0; index != itemList.size() - 1; index++) {
                    // find item
                    int position = Collections.binarySearch(itemList.get(index).transId, currentTransId);
                    // found
                    if(position >= 0) {
                        printWriter.printf("t,");
                        itemList.get(index).transId.remove(position);
                    }
                    else {
                        printWriter.printf(",");
                    }
                }
                // handle the last item
                int position = Collections.binarySearch(itemList.get(index).transId, currentTransId);
                // found
                if(position >= 0) {
                    printWriter.println("t");
                }
                else {
                    printWriter.println();
                }
            }
            input_file.close();
            inputStreamReader.close();
            bufferedReader.close();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
