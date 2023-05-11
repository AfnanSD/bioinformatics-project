import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class ahoCorasickAlgorithim {

    static Scanner input = new Scanner(System.in);

    static int maxNumberOfStates = 500;//max number of states
    static int characters = 26;//Since we are using asci code we decided to keep character to 26 which is the number of letter is alphabet
    
    //definitions of the arrays
    static int [] output = new int[maxNumberOfStates];//sotre index of the end of the keywords,  for every state ‘s’ of the automaton, it finds all words which are ending at the state ‘s’
    static int [] fail = new int [maxNumberOfStates]; //find the backward transition to get a proper suffix of some keywords
    //when fail to match to not go back to root, it will point to the end of the pattern with the long suffix*(it will enhance the search to O(m=text length) )
    static int [][] goTo = new int [maxNumberOfStates][characters]; //to make the tree using all keywords (also called Trie)

    static int numberOfComparision = 0;//initilize with 0


    static Object pattern [] ;
    static boolean [] patternsFound;
    static String sequence ;

    //to get input from the user
    public static void getInput(){

        System.out.print("Enter the DNA sequence: ");
        sequence = input.nextLine();

        LinkedList<String> tmp = new LinkedList<String>();
        System.out.print("Enter the patterns you are searching for: ");
        while (true) {
            String userInput = input.nextLine();
            if (userInput.charAt(0)=='0') {
                break;
            }
            tmp.add(userInput);
            System.out.print("Enter the next pattern you are searching for (to stop enter 0): ");
        }

        System.out.println();
        pattern =  tmp.toArray();

        //this array to keep track of patterns found and not found
        patternsFound = new boolean[pattern.length];
        Arrays.fill(patternsFound,false);

    }

    public static void buildTree(Object[] pattern){

        Arrays.fill(output, 0); //set all elements of output array to 0
        Arrays.fill(fail,-1);//set all elements of fail array to -1  

        //set all elements of goto matrix to -1
        for (int i = 0; i < maxNumberOfStates; i++) {
            Arrays.fill(goTo[i], -1);
        }

        int state = 1;//there is only one state at first

        //move over each word in pattern list
        for (int i = 0; i < pattern.length; ++i) {
            String word = (String) pattern[i];
            int present = 0;

            //iterate through each word
            for (int j = 0; j < word.length(); ++j) {
                int ch = word.charAt(j) - 'A';//to make it a number
                
                //create a new state if a node for ch doesn't exist 
                if (goTo[present][ch] == -1) {
                    goTo[present][ch] = state;
                    state++;
                } 
                present = goTo[present][ch];
            }

            //add current word to output array
            output[present] |=  (1<<i) ;

        } 

        //for all characters if it does not have edge from root, add edge to the root itself
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
                        failure = fail[failure];
                    }
                    failure = goTo[failure][ch];
                    fail[goTo[newState][ch]]= failure;
                    output[goTo[newState][ch]] |= output[failure];
                    q.add(goTo[newState][ch]);
                }
            }
        }
    }

    static int getNextState(int presentState,char nextChar){
        int answer = presentState;
        int ch = nextChar - 'A';

        //if not defined in goTo array, use fail array
        while (goTo[answer][ch]==-1) {
            answer = fail[answer];
        }

        return goTo[answer][ch];
    }

    static void patternSearch(Object[] pattern,String sequence){
        buildTree(pattern);
        int presentState = 0;

        for (int index = 0; index < sequence.length(); ++index) {
            numberOfComparision++;

            presentState = getNextState(presentState, sequence.charAt(index));

            //match not found
            if (output[presentState]==0) {
                continue;
            }

            //match found 
            for (int i = 0; i < pattern.length; ++i) {

                //print pattern when it is first found only
                if ((output[presentState] & (1<<i))>0 && !patternsFound[i]) {

                    System.out.print("The pattern exist: '");
                    System.out.println(pattern[i]+"'");
                    patternsFound[i] = true;
                    System.out.println("the number of comparsions it took to find it: "+numberOfComparision);
                    System.out.println();
                }
            }
        }

        //print the patterns which were not found
        for (int j = 0; j < patternsFound.length; j++) {
            if (!patternsFound[j]) {
                System.out.print("The pattern does not exist: '");
                System.out.println(pattern[j]+"'");
                System.out.println("the number of comparsions the algorithim made: "+sequence.length());
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        getInput();
        patternSearch(pattern, sequence);
    }
}
