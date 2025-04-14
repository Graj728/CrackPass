import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class five {
    private String fileName;
    private static ArrayList<String> pass5;
    private int numThreads;
    private static volatile boolean found = false;

    public five(String fileName, ArrayList<String> pass5, int numThreads) {
        this.fileName = fileName;
        this.pass5 = pass5;
        this.numThreads = numThreads;

    }
    static class passwordTest implements Runnable{
        private String fileName;
        private List<String> passChunk;

        public passwordTest(String fileName, List<String> passChunk) {
            this.fileName = fileName;
            this.passChunk = passChunk;
        }
        @Override
        public void run(){
        for (String string : pass5) {
            try {
            ZipFile zipFile = new ZipFile(fileName);
            zipFile.setPassword(string);
            zipFile.extractAll("contents1");
            System.out.println("Successfully cracked!"+string);
            found=true;
            break;
            
        } catch (ZipException ze) {
            System.out.println("Incorrect password :(");
        } catch (Exception e){
            e.printStackTrace();
        }
    }}}
    

    public void threadMaker() throws Exception {
         String threadZip = "temp_copy_" + Thread.currentThread().getId() + ".zip";
        Files.copy(Path.of(fileName), Path.of(threadZip));
        int chunkSize = (int) Math.ceil(pass5.size() / (double) numThreads);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, pass5.size());

            List<String> passChunk = pass5.subList(start, end);
            Thread thread = new Thread(new passwordTest(fileName, passChunk));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for all threads to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!found) {
            System.out.println("Password not found.");
        }
        Files.delete(Path.of(threadZip));
    }

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
