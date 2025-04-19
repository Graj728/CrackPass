import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class five {
    private String fileName;
    private static ArrayList<String> pass5;
    private int numThreads;
    private static volatile boolean found = false;

    /**
     * constructor for class five
     * 
     * @param fileName   takes the file name to be cracked
     * @param pass5      takes arraylist of stored password
     * @param numThreads takes the number of thread to be used
     */
    @SuppressWarnings("static-access")
    public five(String fileName, ArrayList<String> pass5, int numThreads) {
        this.fileName = fileName;
        this.pass5 = pass5;
        this.numThreads = numThreads;

    }

    static class passwordTest implements Runnable {// I have taken the concept of static class from google search and
                                                   // chatgpt
        private String fileName;
        private List<String> chunkPass;

        private String contents;

        /**
         * constructor for passwordTest class
         * 
         * @param fileName  takes the file name
         * @param chunkPass takes the password divided into chunks
         * @param contents  takes the name of folder
         */
        public passwordTest(String fileName, List<String> chunkPass, String contents) {
            this.fileName = fileName;
            this.chunkPass = chunkPass;

            this.contents = contents;
            File dir = new File(contents);
            if (!dir.exists()) {
                dir.mkdirs();
            }

        }

        @Override
        /**
         * method to run the zipfile extracttion
         * it implements the runnable interface and override the run method
         */
        public void run() {

            for (String string : chunkPass) {

                if (found) {

                    return;
                }
                try {
                    ZipFile zipFile = new ZipFile(fileName);
                    zipFile.setPassword(string);
                    zipFile.extractAll(contents);

                    System.out.println("Successfully cracked!" + string);
                    found = true;
                    break;

                } catch (ZipException ze) {
                    // System.out.println("Incorrect password :("+string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * method to create thread and execute the zip extraction
     * 
     * @throws Exception handles the file io exception
     */
    public void threadMaker() throws Exception {

        int chunkSize = (int) Math.ceil(pass5.size() / (double) numThreads);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, pass5.size());

            List<String> chunkPass = pass5.subList(start, end);
            String threadZip = "temp_copy_" + i + ".zip";// creates a temporary file for each thread
            String extractF = "content" + i;// creates content for for each thread
            Path extrPath = Path.of(extractF);// stores the path of extraction folder it has been created with help from
                                              // chatgpt
            Path threadPath = Path.of(threadZip);// stores the path of temporary zip file it has been created with help
                                                 // from chatgpt
            try {
                if (Files.exists(extrPath)) {/*
                                              * tries to check content folder an ddelete them created with help form
                                              * chatgpt
                                              */
                    Files.delete(extrPath);
                }
            } catch (Exception cnd) {

            }

            if (Files.exists(threadPath)) {/*
                                            * tries to check temporary zip file an ddelete them created with help form
                                            * chatgpt
                                            */
                Files.delete(threadPath);
            }
            Files.copy(Path.of(fileName), threadPath);
            Thread thread = new Thread(new passwordTest(threadZip, chunkPass, extractF));// calls the passwordTest
                                                                                         // method
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!found) {
            System.out.println("Password not found.");
        }
        for (int i = 0; i < numThreads; i++) {/* deletes the temporary zipfile */
            String threadZip = "temp_copy_" + i + ".zip";
            Path threadPath = Path.of(threadZip);
            if (Files.exists(threadPath)) {
                Files.delete(threadPath);
            }
        }
    }

    /**
     * method to generate 5 digit password and store them in array
     * 
     * @param pass5 takes the arraylist to store password
     * @return arraylist of stored password
     */
    public static ArrayList<String> passwordMaker(ArrayList<String> pass5) {
        String password = "";
        for (char in1 = 'a'; in1 <= 'z'; in1++) {
            for (char in2 = 'a'; in2 <= 'z'; in2++) {
                for (char in3 = 'a'; in3 <= 'z'; in3++) {
                    for (char in4 = 'a'; in4 <= 'z'; in4++) {
                        for (char in5 = 'a'; in5 <= 'z'; in5++) {
                            password = "" + in1 + in2 + in3 + in4 + in5;

                            pass5.add(password);
                        }
                    }
                }
            }
        }
        return pass5;
    }
}
