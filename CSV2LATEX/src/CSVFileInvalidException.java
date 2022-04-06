import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * This is an exception class that works when the CSV file misses an attribute.
 * */

public class CSVFileInvalidException extends InvalidException{

    /**
     * This constructor creates a log file that has the attributes and the missing one will be ***.
     * @param arr the array of attributes.
     * @param file the name of the file.
     * @param theFileCSV a CSV file.
     * */

    public CSVFileInvalidException(String path,File file, String[] arr, File theFileCSV){

        try {

            PrintWriter fw2 = new PrintWriter(file);
            fw2.println("File "+ theFileCSV.getName() +" is invalid: attribute is missing.");
            int i=0;
            while (i<arr.length){

                if(i == arr.length -1){
                    fw2.print(arr[i]);
                }
                else {
                    fw2.print((arr[i] + " ,"));
                }
                i++;
            }
            fw2.println();
            fw2.println("File "+ theFileCSV.getName() +" is not converted to LATEX.");

            fw2.flush();
            fw2.close();


        } catch (FileNotFoundException e){
            System.out.println("Could not open input file " + file.getName() + " for reading." + "\n" +
                    "Please check if file exists! Program will terminate after closing any opened files");
            System.exit(0);
        }


        }
    }



