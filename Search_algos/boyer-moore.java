import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


// class to take .txt file and return string.. I would definitely try arraylist or linked list next
class FileToString {
    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public String get_file(String filePath) {

        try {
            String content = this.readFileAsString(filePath);
            return content;

        } catch (IOException e) {
            return "Error reading the file: " + e.getMessage();
        }
        
    }
}



class BoyerMoore{
    private int[] preprocessBadCharacter(String subString){

        // assume 256 chars for ascii character set (found this information on wikipedia)
        int[] badChar = new int[256];
        
        for (int i = 0; i < subString.length() -1; i++) {
            badChar[subString.charAt(i)] = i;
        }
        return badChar;
    }


    public int[] search(String text, String subString){

        int subLength = subString.length();
        int textLength = text.length();
        int[] arr = new int[textLength];
        int arrCounter = 0;
        int shift = 0;
        int j;

        // preprocess the pattern and create the bad character array
        int[] badCharArray = this.preprocessBadCharacter(subString);

        while(shift<= textLength-subLength){
            // start at ending position of pattern
            j = subLength - 1; 

            // traverse entire pattern 
            while(j >=0 && subString.charAt(j) == text.charAt(shift + j)){
                j = j - 1;
            }

            // match found
            if(j <0){
            
                // Store the starting index of the found substring.
                arr[arrCounter] = shift;
                arrCounter ++;

                // Use the bad character heuristic to determine the next shift.
                if(shift + subLength < textLength ){

                    shift = shift + subLength - badCharArray[text.charAt(shift+subLength)];
                } else{
                    // just shift by 1
                    shift = shift + 1;
                }
            } else{
                shift = shift + Math.max(1, j - badCharArray[text.charAt(shift + j)]);

            }

        }

    }

}


class main{
    public static void main(String[] args) {

        FileToString file = new FileToString();
        Scanner scanner = new Scanner(System.in);
        Brute_Force bf = new Brute_Force();
        BoyerMoore bm = new BoyerMoore();


        // select input file
        String fileName = "prog1input1.txt";
        //String fileName = "prog1input2.txt";


        String text = file.get_file(fileName);


        // get user input
        System.out.println("Please enter the substring");
        String subString = scanner.nextLine();


        // start timer for Boyer Moore search & print duration after completion 
        long startTime = System.nanoTime();
        int[] bm_found = bm.search(text, subString);
        long endTime = System.nanoTime();
        long bmDuration = (endTime - startTime);
        System.out.println("Execution time for Boyer Moore: " + bmDuration + " nanoseconds.");
        System.out.println("Matches found at index : " + Arrays.toString(bm_found));

}


public class boyer-moore {

}
