
/**
 * A parent class for the rest of the exceptions.
 * */
public class InvalidException extends Exception{

    /**
     * This is the parent constructor of the other 2 exceptions. It will give an error message mutual with all exceptions.
     * */
    public InvalidException(){
        System.out.println("Error: Input row cannot be parsed due to missing information.");
        System.out.println("Check if a log file created for more information.");
    }

}
