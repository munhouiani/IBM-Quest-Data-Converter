import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by mhwong on 4/29/15.
 */
public class DataConverter {

    public static void main(String[] args) {
        if(args.length == 0) {
            printError();
        }
        else {
            if(args[0].equals("-help")) {
                printHelp();
            }
            else if(args[0].equals("-a")) {
                if(args.length != 3) {
                    printError();
                }
                else {
                    new ConvertIbmToWekaCsv(args[1], args[2]);
                }
            }
            else if (args[0].equals("-b")) {
                if(args.length != 3) {
                    printError();
                }
                else {
                    new ConvertCsvToWekaCsv(args[1], args[2]);
                }
            }
            else if (args[0].equals("-c")) {
                if(args.length != 3) {
                    printError();
                }
                else {
                    new ConvertIbmToCsv(args[1], args[2]);
                }
            }
            else if (args[0].equals("-d")) {
                if(args.length != 3) {
                    printError();
                }
                else {
                    new SeperateIbmWithComma(args[1], args[2]);
                }
            }
            else {
                printError();
            }
        }
    }

    public static void printError() {
        System.out.printf("Usage: %s [option] [input file] [output file]\n", DataConverter.class.getCanonicalName());
        System.out.printf("%s -help for more details.\n", DataConverter.class.getCanonicalName());
    }

    public static void printHelp() {
        System.out.println("Option:");
        System.out.println("\t-a\t Convert ascii data from IBM Quest Data Generator to Weka compatible csv");
        System.out.println("\t-b\t Convert conventional csv to Weka compatible csv");
        System.out.println("\t-c\t Convert ascii data from IBM Quest Data Generator to conventional csv");
        System.out.println("\t-d\t Seperate ascii data from IBM Quest Data Generator with comma");
    }
}
