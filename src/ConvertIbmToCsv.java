import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by mhwong on 4/30/15.
 */
public class ConvertIbmToCsv {

    public ConvertIbmToCsv(String input, String output) {
        convertIbmToCsv(input, output);
    }

    public void convertIbmToCsv(String input, String output) {
        try {
            InputStream input_file = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(input_file, Charset.forName("ascii"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(output, "ascii");
            String input_line;
            String output_line = "";
            int current_custId = 0, current_transId = 0;
            int lastTransId = 0;
            List<Item> itemList = new ArrayList<>();
            while((input_line = bufferedReader.readLine()) != null) {
                int custId, transId, itemId;
                Scanner scanner = new Scanner(new ByteArrayInputStream(input_line.getBytes()));
                custId = scanner.nextInt();
                transId = scanner.nextInt();
                itemId = scanner.nextInt();

                if(output_line.isEmpty()) {
                    current_custId = custId;
                    current_transId = transId;
                    output_line += String.valueOf(itemId);
                }
                else if(custId != current_custId && transId != current_transId) {
                    printWriter.println(output_line);
                    output_line = "";
                }
                else {
                    output_line += "," + String.valueOf(itemId);
                }

                scanner.close();
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
