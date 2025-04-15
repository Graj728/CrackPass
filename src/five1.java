
    import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.util.ArrayList;
import java.util.List;

public class five1 {
    private String fileName;
    private ArrayList<String> passwords;
    private int numThreads;
    private static volatile boolean found = false; // To stop all threads when password is found

    public five1(String fileName, ArrayList<String> passwords, int numThreads) {
        this.fileName = fileName;
        this.passwords = passwords;
        this.numThreads = numThreads;
        startCracking(); // Start threads immediately
    }

    private void startCracking() {
        int chunkSize = (int) Math.ceil(passwords.size() / (double) numThreads);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, passwords.size());

            List<String> passChunk = passwords.subList(start, end);
            Thread thread = new Thread(new Cracker(fileName, passChunk));
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
    }

    // Runnable inner class that does the cracking
    static class Cracker implements Runnable {
        private String fileName;
        private List<String> passChunk;

        public Cracker(String fileName, List<String> passChunk) {
            this.fileName = fileName;
            this.passChunk = passChunk;
        }

        @Override
        public void run() {
            for (String password : passChunk) {
                if (found) return; // Stop if another thread found it

                try {
                    ZipFile zipFile = new ZipFile(fileName);
                    zipFile.setPassword(password);
                    zipFile.extractAll("contents1");

                    found = true;
                    System.out.println("Password found by " + Thread.currentThread().getName() + ": " + password);
                    return;
                } catch (ZipException e) {
                    // incorrect password, move on
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Optional utility to generate 5-letter lowercase passwords
    public static ArrayList<String> passwordMaker() {
        ArrayList<String> passList = new ArrayList<>();
        String password;
        for (char a = 'a'; a <= 'z'; a++) {
            for (char b = 'a'; b <= 'z'; b++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    for (char d = 'a'; d <= 'z'; d++) {
                        for (char e = 'a'; e <= 'z'; e++) {
                            password = "" + a + b + c + d + e;
                            passList.add(password);
                        }
                    }
                }
            }
        }
        return passList;
    }
}

