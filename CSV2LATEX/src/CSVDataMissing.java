import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is an exception class that works when the CSV file misses some data.
 * */

public class CSVDataMissing extends InvalidException{

    /**
     * This constructor creates a log file that has the attribute(s) where data is missing and the row of that data.
     * The missing data will be named ***.
     * @param arr array of attributes.
     * @param file the log file.
     * @param lines the number of rows of data.
     * @param theFileCSV a CSV file.
     * */

    public CSVDataMissing(String[] arr, int lines, File file, File theFileCSV) throws IOException {

        try {

            FileWriter fw2 = new FileWriter(file, true);
            fw2.write("In file " + theFileCSV.getName() + ".CSV line " + lines + " not converted to LATEX : missing data");
            fw2.write("\n");
            fw2.write(Arrays.toString(arr).replace("[", "")  //remove the right bracket
                            .replace("]", "")); //remove the left bracket
            fw2.write("\n");
            fw2.write("Missing: " + Driver.getAttributeWhenException());
            fw2.write("\n");

            fw2.flush();
            fw2.close();


        } catch (FileNotFoundException e){
            System.out.println("Could not open input file " + file.getName() + " for reading." + "\n" +
                    "Please check if file exists! Program will terminate after closing any opened files");
            System.exit(0);
        } catch (IOException e){
            System.out.println("Error while writing to the log file.");
        }


    }


}
