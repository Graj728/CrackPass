import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        System.out.print("Enter number of Threads : ");
        @SuppressWarnings("resource")
        int numThreads=new Scanner(System.in).nextInt();
        long starttime=System.currentTimeMillis();

        // ArrayList<String> pass = new ArrayList<>() ;   
        ArrayList<String> pass5 = new ArrayList<>() ;
        // pass=three.passwordMaker(pass);
        pass5=five.passwordMaker(pass5);
        // new three("protected3.zip",pass);
        try {
            five passwordCrack = new five("protected5.zip", pass5, numThreads);
            passwordCrack.threadMaker(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(System.currentTimeMillis()-starttime);
        
        
    }
}
