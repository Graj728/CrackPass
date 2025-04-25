//time to crack the 5 digit password using 3 threads=8621221 and using 4 threads =10226697
//the directory contents has the message from protected3 and the directory content ending with a number has the message from protected5

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        System.out.print("Enter number of Threads : ");
        @SuppressWarnings("resource") // warning supressor
        int numThreads = new Scanner(System.in).nextInt();// takes the number of thread
        long starttime = System.currentTimeMillis();// stores the start time of code execution

        ArrayList<String> pass = new ArrayList<>();// arraylist for 3 digit passcode
        ArrayList<String> pass5 = new ArrayList<>();// arraylist for 5digit password
        pass = three.passwordMaker(pass);// generates and store 3 digit password
        pass5 = five.passwordMaker(pass5);// generates and store 5 digit password
        new three("protected3.zip", pass);// executes the 3 digit zip fil

        System.out.println("Time to crack 3 digit password : " + (System.currentTimeMillis() - starttime));/*
                                                                                                            * time to
                                                                                                            * break 3
                                                                                                            * digit
                                                                                                            * zipfile
                                                                                                            */
        starttime=System.currentTimeMillis();
        try {
            five passwordCrack = new five("protected5.zip", pass5, numThreads);/* executes the 5 digit file */
            passwordCrack.threadMaker();/* calls the threadmaker */
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Time to crack 5 digits password : " + (System.currentTimeMillis() - starttime));/*
                                                                                                             * timeto
                                                                                                             * carck 5
                                                                                                             * digit
                                                                                                             * file
                                                                                                             */

    }
}
