
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
        String dna=input.nextLine(); 
        System.out.print("\n");
        System.out.print("Enter the pattern you are searching for: ");
        String patter = input.nextLine();
        input.close();
        

        //Applying Brute Force Search
        int n = dna.length();
        int m = patter.length();

        //number of comparisons made
int comparisons = 0;
//boolean to print a specialized string in case there’s no match
    Boolean found = false;
//we go through the window to check for every possible match
//n is the length of the text, m is the length of the pattern
    for(int i = 0; i <= n - m; i++){
//we move letter by letter and make a substring the length of m
        String substring = dna.substring(i, i+m);
 
   //comparing the substring and the pattern 	
    if(substring.equals(patter)){
            found = true;
    //print where the match is found
            System.out.println("Pattern found at " + i);
        }
    //add to the number of comparisons by 1
        comparisons++;
    }
 

        if(!found)
            System.out.println("No occurrence found. And the number of comparisons was " + comparisons);

        else
        System.out.println("Number of overall comparisons  = "+ comparisons );
    }
}

