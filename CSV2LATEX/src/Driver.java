
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is the Driver class that contains the processFilesForValidation and main methods.
 * It has 3 private attributes, which are the title of the file, a string of attribute(of that file) and a string to make it *** when necessary.
 * */

public class Driver {

    /** This is the title of the file. */
    private  static String title;
    /** This array will contain the row of attributes. */
    private static String[] att;
    /** This string will hold the attribute name when data is missing to put it in the log file. */
    private static String attributeWhenException;

    /**
     * This is a getter for the static attribute attributeWhenException.
     * @return attributeWhenException.
     * */
    public static String getAttributeWhenException() {
        return attributeWhenException;
    }

    /**
     * This method deletes a file.
     * @param fileCanBeDeleted a file.
     * */

    private static void deleteDirectory(File fileCanBeDeleted) {
        if (fileCanBeDeleted.delete()) {
            //System.out.println(fileCanBeDeleted.getName() + " is deleted");
        }
//        else {
//            System.out.println("Directory not deleted");
//        }
    }

    /**
     * This method checks whether a file is empty or not.
     * @param f a file.
     * */
    private static boolean fileIsEmpty(File f){
        return f.length() == 0;
    }

    /**
     * This method takes the name of a file and then converts a CSV file to a Latex file.
     * It deals with perfect, missing attributes and missing data files.
     * In case of a missing attribute, the file will not be converted to LATEX.
     * In case of missing data, the record will be included in the LATEX table.
     * @param file_path The path of the file.
     * */

    public static void processFilesForValidation(String file_path){

        // Ignore the last '\' of the path in order to create the log in the right path.

        int index = file_path.lastIndexOf('\\');
        File logDfile = new File(file_path.substring(0,index),"log.txt");


        // Delete the log file if any old one exists.
        if(!fileIsEmpty(logDfile)){
            deleteDirectory(logDfile);
        }

        Scanner Reader = null;
        PrintWriter printW = null;
        String attributes;
        String rowsOfData;
        int attributeCounter = 0;
        File canBeDeleted = null;
        File f = null;

        //================ deal with attributes.

        try {
            f = new File(file_path + ".CSV");
            Reader = new Scanner(f);
            canBeDeleted = new File(file_path + ".TEX");
            printW = new PrintWriter(canBeDeleted);


            title = Reader.nextLine();
            attributes = Reader.nextLine();
            StringTokenizer aToken = new StringTokenizer(attributes, ",");
            attributeCounter = aToken.countTokens();

            att = attributes.split(",", -1);


            printW.write("\\" + "begin{table}[]" + "\n");

            // Controlling the number of l| in the second row of the Latex file.
            printW.write("\t" + "\\" + "begin{tabular}" + "{" + new String(new char[attributeCounter]).replace("\0", "l|") + "}" + "\n");

            printW.write("\t\t" + "\\toprule" + "\n");
            printW.write("\t\t");


            for (int l = 0; l < attributeCounter; l++) {

                if (l == attributeCounter - 1) {
                    if (att[l].isEmpty()) {
                        att[l] = "***";
                        printW.close();
                        throw new CSVFileInvalidException(file_path,logDfile, att, f);
                    }
                    printW.print(att[l]);
                } else {
                    if (att[l].isEmpty()) {
                        att[l] = "***";
                        deleteDirectory(canBeDeleted);
                        printW.close();
                        throw new CSVFileInvalidException(file_path,logDfile, att, f);
                    }
                    printW.print(att[l] + " & ");
                }

            }

        }
        catch (CSVFileInvalidException e){
            deleteDirectory(canBeDeleted);
            System.exit(0);
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not open input file " + f.getName() + " for reading." + "\n" +
                    "Please check if file exists! Program will terminate after closing any opened files");
            System.exit(0);
        }




            printW.write(" \\\\" + " \\midrule");
            printW.write("\n");

            //===================== deal with data.

        /* This is a counter for the number of data rows in a latex file excluding the first 4 rows since they are not data */
        int lines = 4;

        do {
            try {

                rowsOfData = (Reader.nextLine()) ;
                lines++;
                String[] data = rowsOfData.split(",", -1);

                for (int y=0; y<data.length; y++) {
                    if (data[y].isEmpty()) {
                        attributeWhenException = att[y] ;
                        data[y] = "***";
                        throw new CSVDataMissing(data, lines,logDfile,f);
                    }
                }

                printW.write("\t\t");

                for (int l = 0; l < data.length; l++) {

                    if (l == data.length -1) {
                            printW.print(data[l]);

                    } else {
                        printW.write(data[l] + " & ");
                    }

                }

                if (!Reader.hasNextLine()) {
                    printW.print(" \\\\" + " \\bottomrule");
                } else {
                    printW.print(" \\\\" + " \\midrule");
                }

                printW.println();

            } catch (FileNotFoundException e) {
                System.out.println("Could not open input file " + f.getName() + " for reading." + "\n" +
                        "Please check if file exists! Program will terminate after closing any opened files");
                System.exit(0);
            } catch (IOException e){
                System.out.println("Error while writing to the file " + f.getName() +".TEX");
            } catch (CSVDataMissing e) {
                Reader.hasNextLine();
            } catch (Exception e) {
                System.out.println("Unknown error has occurred!");
            }

        } while (Reader.hasNextLine());


        // ============= Write the last 4 rows of a latex file.

        printW.write("\t" + "\\end{tabular}" + "\n");
        printW.write("\t" + "\\caption" + "{" + title.replaceAll("[^a-zA-Z0-9]*$", "") + "}" + "\n");
        printW.write("\t" + "\\label" + "{" + f.getName().substring(0, f.getName().length() - 4) + "}" + "\n");
        printW.write("\\end{table}");


        Reader.close();
        printW.flush();
        printW.close();

        if(fileIsEmpty(logDfile)){
            deleteDirectory(logDfile);
        }


    }


    /**
     * This is the main method of the program.
     * @param args String[] args.
     * */

    public static void main(String[] args) {

        System.out.println("Enter the path of a CSV file to covert it to TEX: ");
        Scanner keyboard = new Scanner(System.in);
        String file_path = keyboard.nextLine();
        processFilesForValidation(file_path);

        System.out.println("Enter the path of the file you would like to read: ");
        file_path = keyboard.nextLine();
        String line;
        int stopper = 0;

        while (stopper != 2) {
            try {

                FileReader r = new FileReader(file_path);
                BufferedReader buffer = new BufferedReader(r);

                while ((line = buffer.readLine()) != null) {
                    System.out.println(line);
                }
                r.close();
                stopper = 2;

            } catch (FileNotFoundException e) {
                stopper++;
                System.out.println("Could not open input file " + e.getMessage() + " for reading." + "\n" +
                        "Please check if file exists! Program will terminate after closing any opened files");
                if (stopper == 2)
                    System.exit(0);
                file_path = keyboard.nextLine();
            } catch (IOException e) {
                System.out.println("Error reading from the file");
            }

        }


        }

    }
