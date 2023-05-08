import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
//TODO pattern, number-of-comarisions, andshift-amount

/*
 * afnan
 */
public class ahoCorasickAlgorithim {

    static int maxs = 100;// 1000 which is arbitrary is the max number of states => sum of the length of all keywords
    static int characters = 26;//should be four? because DNA? or 20 because of protien
    
    //definitions of the arrays
    static int [] output = new int[maxs];//sotre index of the end of the keywords,  for every state ‘s’ of the automaton, it finds all words which are ending at the state ‘s’
    static int [] fail = new int [maxs]; //find the backward transition to get a proper suffix of some keywords
    static int [][] goTo = new int [maxs][characters]; //to make the tree using all keywords (also called Trie)

    static int numberOfComparision = 0;//initilize with 0



    //1  static String  pattern;
    //,"any","their","there","answer","bye"
    static String pattern [] =  {"he", "she", "hers", "his","afnan","kk"} ;//TODO fix not found if more than one word in pattern(need more code)
    static boolean [] foundarr = new boolean[pattern.length];
    static String sequence = "ahishers";


    //take it as input?
    public static void getInput(){
        //to get input from the user
        //static Scanner input = new Scanner(System.in);
        //System.out.println("write down pattern");
        //TODO fix it to be more,,, or is it hust one word?
        //1 pattern =  input.nextLine();//just one at the moment
        
        // System.out.println("write down sequence");
        // sequence = input.nextLine();
    }


    //size? or defult just one word
    //TODO user size
    public static int buildTree(String[] p,int size){

        Arrays.fill(output, 0); //set all elements of output array to 0
        Arrays.fill(fail,-1);//set all elements of fail array to -1  
        //set all elements of goto matrix to -1
        for (int i = 0; i < maxs; i++) {
            Arrays.fill(goTo[i], -1);
        }

        int state = 1;//there is only one state at first?

        //move over each word in pattern list
        for (int i = 0; i < p.length; ++i) {
            String word = p[i];
            int present = 0;

            //iterate through each word
            for (int j = 0; j < word.length(); ++j) {
                int ch = word.charAt(j) - 'a';//to make it a number
                
                //create a new state if a node for ch doesn't exist ?
                if (goTo[present][ch] == -1) {
                    goTo[present][ch] = state;
                    state++;
                } 
                present = goTo[present][ch];
            }

            //add current word to output array?
            output[present] |=  (1<<i) ;

        } 

        //extra ?? add to algorithim !!

        // For all characters which don't have
        // an edge from root (or state 0) in Trie,
        // add a goto edge to state 0 itself
        for(int ch = 0; ch < characters; ch++)
        if (goTo[0][ch] == -1)
            goTo[0][ch] = 0;
        

        Queue<Integer> q = new LinkedList<>();
        

        for (int ch = 0; ch < characters; ++ch) {
            if (goTo[0][ch]!=0) {
                fail[goTo[0][ch]] = 0;
                q.add(goTo[0][ch]);
            }
        }

        while (!q.isEmpty()) {
            
            int newState = q.poll();

            for (int ch = 0; ch < characters; ++ch) {
                if (goTo[newState][ch]!=-1) {
                    int failure = fail[newState];
                    while (goTo[failure][ch]==-1) {
                        failure = fail[failure];//goTo[failure][ch]??
                    }
                    failure = goTo[failure][ch];//?
                    fail[goTo[newState][ch]]= failure;
                    output[goTo[newState][ch]] |= output[failure];
                    q.add(goTo[newState][ch]);
                }
            }
        }
        return state;
    }

    static int getNextState(int presentState,char nextChar){
        int answer = presentState;
        int ch = nextChar - 'a';

        //if not defined in goTo array, use fail array
        while (goTo[answer][ch]==-1) {
            answer = fail[answer];
        }
       // numberOfComparision++;
        return goTo[answer][ch];
    }

    static void patternSearch(String[] pattern,int size,String sequence){
        buildTree(pattern,size);//size?
        int presentState = 0;
        boolean found = false;

        for (int index = 0; index < sequence.length(); ++index) {
            
            presentState = getNextState(presentState, sequence.charAt(index));

            //match not found
            if (output[presentState]==0) {
                //numberOfComparision++;
                continue;
            }

            //match found
            for (int i = 0; i < size; ++i) {
                if ((output[presentState] & (1<<i))>0) {
                    found = true;
                     System.out.print("The pattern exist ,");
                     System.out.println(pattern[i]);
                     foundarr[i] = true;
                     //System.out.print("it appears from " +(index - pattern[i].length() + 1) +" to " + index + "\n");
                     numberOfComparision = index + 1; //since the work is all done in building the trie>> comparison would be linear?
                     System.out.println("the number of comparsions it took to find it: "+numberOfComparision);//TODO count it
                     System.out.println();
                }
            }
        }

        //print not found
        for (int j = 0; j < foundarr.length; j++) {
            if (!foundarr[j]) {
                System.out.print("The pattern does not exist ");
                System.out.println(pattern[j]);
                System.out.println("the number of comparsions the algorithim made: "+sequence.length());
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        Arrays.fill(foundarr,false);//TODO change its place

        getInput();
        patternSearch(pattern, pattern.length, sequence);
    }
}
