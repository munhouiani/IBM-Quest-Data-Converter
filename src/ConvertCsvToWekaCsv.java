import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by mhwong on 4/30/15.
 */
public class ConvertCsvToWekaCsv {
    public ConvertCsvToWekaCsv(String input, String output) {
        convertCsvToWekaCsv(input, output);
    }

    public void convertCsvToWekaCsv(String input, String output) {
        try {
            InputStream input_file = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(input_file, Charset.forName("ascii"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(output, "ascii");
            String input_line;
            int lastTransId = 0;
            List<Item> itemList = new ArrayList<>();
            while((input_line = bufferedReader.readLine()) != null) {
                int itemId;
                Scanner scanner = new Scanner(new ByteArrayInputStream(input_line.getBytes()));
                scanner.useDelimiter("[ ,\r\n]+");
                while(scanner.hasNext()) {
                    itemId = scanner.nextInt();
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
                        itemList.get(position).transId.add(lastTransId);
                    }
                    // not found
                    else {
                        Item item = new Item();
                        item.itemId = itemId;
                        item.transId.add(lastTransId);
                        itemList.add(item);
                    }
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
