import java.util.Arrays;
import java.util.Scanner;

/*
 * afnan
 */
public class ahoCorasickAlgorithim {

    static int maxs = 1000;// 1000 which is arbitrary is the max number of states => sum of the length of all keywords
    
    //definitions of the arrays
    static int [] output = new int[maxs];//sotre index of the end of the keywords
    static int [] fail = new int [maxs]; //
    static int [][] goTo = new int [maxs][26]; //

    //to get input from the user
    static Scanner input = new Scanner(System.in);
    static String  pattern;
    static String sequence;


    public static void getInput(){
        System.out.println("write down pattern");
        //TODO fix it to be more,,, or is it hust one word?
        pattern = input.nextLine();//just one at the moment
        System.out.println("write down sequence");
        sequence = input.nextLine();
        System.out.println(pattern);
        System.out.println(sequence);
    }


    //size? or defult just one word
    public static void buildTree(String p, int size){

        Arrays.fill(output, 0); //set all elements of output array to 0
        Arrays.fill(fail,-1);//set all elements of fail array to -1  
        //set all elements of goto matrix to -1
        for (int i = 0; i < goTo.length; i++) {
            Arrays.fill(goTo[i], -1);
        }

        int state = 1;
        int present = 0;
        

    }

    public static void main(String[] args) {
        getInput();
        buildTree(pattern, maxs);//or just 1?
    }
}
