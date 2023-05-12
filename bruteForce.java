
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


public class bruteForce {
    public static void main (String[] args){


//         The problem is to find one, several, or all occurrences of a pattern P with m characters in a
//          DNA sequence S with n characters in total. We note that m < n. In some cases, especially in
//          bioinformatics applications m << n.
//          Solve the problem using Brute Force BF approach by sliding a window of length m (pattern)
//          over the sequence (of length n) from left to right one letter at a time.


    
        Scanner input=new Scanner(System.in);

        

        //Getting Input
        System.out.print("Enter the DNA sequence: ");
        String dna=input.next(); 
        System.out.print("\n");
        System.out.print("Enter the patter you are searching for: ");
        String patter = input.next();
        input.close();
        

        //Applying Brute Force Search
        int n = dna.length();
        int m = patter.length();

        int comparisions = 0;

        Boolean found = false;
        for(int i = 0; i < n - m; i++){
            String substring = dna.substring(i, i+m);
            //System.out.println(substring);
            if(substring.equals(patter)){
                found = true;
                System.out.println("Pattern found at " + i);
            }
            comparisions++;
        }
 

        if(!found)
            System.out.println("No occurrence found");

        System.out.println("Number of overall comparisions = "+ comparisions);
    }
}

