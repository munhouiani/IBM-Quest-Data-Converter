import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by mhwong on 4/30/15.
 */
public class SeperateIbmWithComma {

    public SeperateIbmWithComma(String input, String output) {
        seperateIbmWithComma(input, output);
    }

    public void seperateIbmWithComma(String input, String output) {
        try {
            InputStream input_file = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(input_file, Charset.forName("ascii"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(output, "ascii");
            String input_line;
            String output_line;
            while((input_line = bufferedReader.readLine()) != null) {
                int custId, transId, itemId;
                Scanner scanner = new Scanner(new ByteArrayInputStream(input_line.getBytes()));
                custId = scanner.nextInt();
                transId = scanner.nextInt();
                itemId = scanner.nextInt();
                printWriter.printf("%d,%d,%d\n", custId, transId, itemId);

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
