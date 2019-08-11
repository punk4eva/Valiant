package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

/**
 * Used for debugging and general utility methods
 * @author Charlie
 * @author Adam
 */
public final class Utils {
    
    private Utils(){};
    
    
    public static final Random R = new Random();
    public transient static PrintStream exceptionStream, performanceStream;
    static{
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
    }
    
    
    public static @interface Unfinished{
        String value() default "";
    }
    
    public static @interface ThreadUsed{
        String value() default "";
    }
    
    public static void shuffle(int[] ary){
        int j, temp;
        for(int n=ary.length-1;n>=1;n--){
            j = R.nextInt(n+1);
            temp = ary[n];
            ary[n] = ary[j];
            ary[j] = temp;
        }
    }
    
    public static boolean listContainsArray(List<int[]> list, int[] ary){
        return list.stream().anyMatch(a -> arraysSame(a, ary));
    }
    
    public static boolean arraysSame(int[] a1, int[] a2){
        if(a1.length!=a2.length) return false;
        for(int n=0;n<a1.length;n++) if(a1[n]!=a2[n]) return false;
        return true;
    }
    
    
    /**
     * Main class used for debugging
     * @param args Command line arguments
     */
    public static void main(String... args){
        
    }
    
}
