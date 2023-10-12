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

// brute force class with search method
class Brute_Force{
    public int[] search(String text, String subString){

        int textLength = text.length();
        int subLength = subString.length();
        
        int[] occurrences = new int[textLength];
        int arrayIndex = 0;

        // iterate through every index and compare with first index of pattern. if match, iterate through text and pattern until match or failure
        for(int i = 0; i <= textLength-subLength; i++){
            int y = 0;
            for(int j = 0; j < subLength; j++){
                if(subString.charAt(j) == text.charAt(i + j)){
                    y++;
                }
            };
            if(y == subLength){
                occurrences[arrayIndex] = i;           
                arrayIndex ++;
            };
        };
        return occurrences;
    }
}


class Kmp{
    private int[] failure(String substring){

        // get array length
        int stringLength = substring.length();

        // init int array to hold values
        int[] arr = new int[stringLength];
        // fill array with zeros
        Arrays.fill(arr, 0);
         // set top string being compared 
        int topPointer = 1;
        // set bottom substring index currently being compared 
        int bottomPointer = 0;
        int j = 0;

        while(topPointer <= stringLength-1){
            // if substring index matches with index
            if(substring.charAt(bottomPointer) == substring.charAt(topPointer)){
                // failure function gets set with current y
                j++;
                arr[topPointer] = j;

                //increment top/ bottom pointer
                topPointer++;
                bottomPointer++;
                

            }else if(bottomPointer != 0){
                // dec by 1, set the bottom pointer to that number
                // set the counter to num found at bottom pointer 
                bottomPointer = arr[j-1];
                j = bottomPointer;
                
            }else{
                arr[topPointer] = 0;
                j=0;
                topPointer ++;
            }
        }
        return arr;
    }



    public int[] search(String text, String subString){

        // complete failure function
        int[] failureFunction= this.failure(subString);


       int subStringlength = subString.length();
       int stringLength = text.length();

        int[] arr = new int[stringLength];
        int arrCounter = 0;


        int topPointer= 0;
        int bottomPointer = 0;

        // compare top and bottom pointer, if match, increment both
        while(topPointer <= stringLength-1){
            if(subString.charAt(bottomPointer) == text.charAt(topPointer)){
                // failure function gets set with current y
                //increment top/ bottom pointer
                topPointer++;
                bottomPointer++;

                // if bottom pointer is the length of pattern, return starting index and reset pointers
                if(bottomPointer == subStringlength ){
                    int found = topPointer - bottomPointer;
                    arr[arrCounter] = found;
                    arrCounter++;
                    bottomPointer = failureFunction[bottomPointer-1];
                }
            } else if(subString.charAt(bottomPointer) != text.charAt(topPointer) && bottomPointer != 0){
                // check bottom pointers number, refer to that index, set bottom pointer to that index 
                bottomPointer = failureFunction[bottomPointer-1];
            }else{
                topPointer++;
            }
        }
        return arr;
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

        return arr;
    }

}


class main{
    public static void main(String[] args) {

        FileToString file = new FileToString();
        Scanner scanner = new Scanner(System.in);
        Brute_Force bf = new Brute_Force();
        Kmp kmp = new Kmp();
        BoyerMoore bm = new BoyerMoore();


        // select input file
        String fileName = "prog1input1.txt";
        //String fileName = "prog1input2.txt";


        String text = file.get_file(fileName);


        // get user input
        System.out.println("Please enter the substring");
        String subString = scanner.nextLine();

        // start timer for brute force search & print duration after completion 
        long startTime = System.nanoTime();
        int[] bf_search = bf.search(text, subString);
        long endTime = System.nanoTime();
        long bfDuration = (endTime - startTime);
        System.out.println("Execution time for Brute Force: " + bfDuration + " nanoseconds.");

        // start timer for Boyer Moore search & print duration after completion 
        startTime = System.nanoTime();
        int[] bm_search = bm.search(text, subString);
        endTime = System.nanoTime();
        long bmDuration = (endTime - startTime);
        System.out.println("Execution time for Boyer Moore: " + bmDuration + " nanoseconds.");

        // start timer for KMP search & print duration after completion 
        startTime = System.nanoTime();
        int[] kmp_search = kmp.search(text, subString);
        endTime = System.nanoTime();
        long kmpDuration = (endTime - startTime);
        System.out.println("Execution time for KMP: " + kmpDuration + " nanoseconds.");

        // compare algorithms 
        if (bfDuration <= bmDuration && bfDuration <= kmpDuration) {
            System.out.println("Brute Force is the fastest.");
        } else if (bmDuration <= bfDuration && bmDuration <= kmpDuration) {
            System.out.println("Boyer Moore is the fastest.");
        } else {
            System.out.println("KMP is the fastest.");
        }

    }
}
