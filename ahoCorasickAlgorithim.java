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
    static int characters = 26;//should be four? because DNA?
    
    //definitions of the arrays
    static int [] output = new int[maxs];//sotre index of the end of the keywords
    static int [] fail = new int [maxs]; //
    static int [][] goTo = new int [maxs][characters]; //

    //to get input from the user
    static Scanner input = new Scanner(System.in);
    //1  static String  pattern;
    static String pattern [] =  {"is","any","their","there","answer","bye"} ;
    static String sequence = "isthereanyanswerokgoodbye";


    //take it as input?
    public static void getInput(){
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
        //++ first
        for (int i = 0; i < p.length; ++i) {
            String word = p[i];
            int present = 0;

            //iterate through each word
            //was j++?? why? <<< error now changed
            for (int j = 0; j < word.length(); ++j) {
                int ch = word.charAt(j) - 'a';//? to make it a number?
                if (goTo[present][ch] == -1) {
                    goTo[present][ch] = state;
                    state++;
                } 
                present = goTo[present][ch];
            }

            //output[present] := output[present] OR (shift left 1 for i times)
            output[present] |=  (1<<i) ;

        }  
        //why?? shift left??

        //extra ??
        // For all characters which don't have
        // an edge from root (or state 0) in Trie,
        // add a goto edge to state 0 itself
        for(int ch = 0; ch < characters; ch++)
        if (goTo[0][ch] == -1)
            goTo[0][ch] = 0;
        

        Queue<Integer> q = new LinkedList<>();
        
        //++ first
        for (int ch = 0; ch < characters; ++ch) {
            if (goTo[0][ch]!=0) {
                //System.out.println(goTo[0][ch]+"--0000");
                fail[goTo[0][ch]] = 0;
                q.add(goTo[0][ch]);
            }
        }

        while (!q.isEmpty()) {
            int newState = q.poll();
            //++ first
            for (int ch = 0; ch < characters; ++ch) {
                if (goTo[newState][ch]!=-1) {
                    int failure = fail[newState];
                    while (goTo[failure][ch]==-1) {
                        failure = fail[failure];//goTo[failure][ch]
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
        int ch = nextChar - 'a';//?
        while (goTo[answer][ch]==-1) {
            answer = fail[answer];
        }

        return goTo[answer][ch];
    }

    static void patternSearch(String[] pattern,int size,String sequence){
        buildTree(pattern,size);//size?
        int presentState = 0;

        for (int index = 0; index < sequence.length(); ++index) {
            presentState = getNextState(presentState, sequence.charAt(index));
            if (output[presentState]==0) {
                continue;
            }
            for (int i = 0; i < size; ++i) {
                if ((output[presentState] & (1<<i))>0) {
                    System.out.println("word: exists ");
                    System.out.println("comparsions: ");//TODO count it
                }
                else{
                    //System.err.println("nooo");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        getInput();
        // buildTree(pattern);//or just 1?
        patternSearch(pattern, pattern.length, sequence);
    }
}
