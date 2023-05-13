import java.util.Scanner;
   
public class smartShift {

   public static void main (String[] args){
      Scanner scanner = new Scanner(System.in);
   
      System.out.print("Enter the text: ");
      String textString = scanner.nextLine();
      char[] s = textString.toCharArray();
   
      System.out.print("Enter the pattern: ");
      String patternString = scanner.nextLine();
      char[] p = patternString.toCharArray();
   
      SmartShift(s,p);
   }//end main
   //----------------------------------------------------------------------------------------------------------------------------------
   public static void SmartShift(char[] s ,char[] pattern){
   //generating shif amount array using method shiftAmountGenerator()
      int[] shiftAmount = shiftAmountGenerator(pattern);
   //n is the size of the text array
      int n = s.length;
   //m is the size of pattern array
      int m = pattern.length;
   // integer i represent the index of text to compare with
      int i = 0;
   //integer j represent the index of pattern to be compare with
      int j = 0;
   //count represent the counter of any comparison
      int count =0;
   //flag to find if a pattern found at least once in the text
      boolean flag = false;
   //while the index of the text does not reach to the length of the text (if reached it means thet we coudn't find the pattern in the text)
      while(i<n){
      //if the two characters are the same move both to the next index and increment counter because we compare them here
         if (s[i] == pattern [j]){
            System.out.printf("comparing s[%d]=%s with pattern[%d]=%s \n",i,s[i],j,pattern[j]);
            count++;
            i++;
            j++;
         }
      //if the index reached to the end of the length of the patttern that means all characters are match , the pattern is found here and try to find the other occurance of the pattern if found
         if(j==m){
            System.out.printf("found pattern at s[%d] \n",i-j);
            flag=true;
            j=shiftAmount[j-1];
         }
         //if i hasn't reach to the end of the text yet and the characters in index i of patttern and j of text are not matching shift the pattern by changing the index to the value of the index before j in shift amount
         // in case the value of j isn't 0 (we can not assign j[0-1])
         //if j is 0 move the index of i in the text once step
         else if (i<n&&pattern[j]!=s[i]){
            System.out.printf("comparing s[%s]=%s with pattern[%s]=%s \n",i,s[i],j,pattern[j]);
            count++;
            if(j!=0){
               System.out.printf("shift j to the value of index %d  in shift amount which is : %d , index of i is %d  \n",j-1,shiftAmount[j-1],i);
               j=shiftAmount[j-1];
            }
            else {
               i++;
            }
         }
      }
      //now we need to check if we went for all characters in the text and we didn't find the pattern and that means the pattern didn't found
      if (!flag)
         System.out.println("not found pattern");
    // printing the number of comparision 
      System.out.println("Number of comparisons="+count);
   }//end smart shift
   //---------------------------------------------------------------------------------------------------------------------------------
   public static int[] shiftAmountGenerator( char[] pattern ){
   // m is the size of the pattern
      int m = pattern.length;
      //creating shift amount array
      int[] shiftAmount = new int[m];
      // integers i and j to find the occurance of any indexes of the patern from starting from 0
      int i = 0;
      int j = 1;
      //while not finished coparing all indexes untill the end of the pattern size
      while (j < m) {
      //if we found a matching ,give shiftAmount array the next falue of i and move j to next index
         if (pattern[i] == pattern[j]) {
            shiftAmount[j++] = ++i;
         }
         //if they are not matching and i at index isn't the zero shif i to the value of shiftAmount at index i-1
         else if (i > 0) {
            i = shiftAmount[i-1];
         // if they are not matching and i is zero give shift amount at j a zero value and move j to next character.
         } else {
            shiftAmount[j++] = 0;
         }
      }
      //return shiftAmount array after finished coparing all indexes untill the end of the pattern size
      return shiftAmount;
   }//end smart shift
//---------------------------------------------------------------------------------------------------------------------------------
}//end class