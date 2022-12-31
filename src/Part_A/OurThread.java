package Part_A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OurThread extends Thread {
    private String fileName;
    private volatile int lines;

    public OurThread (String fileName) {
        this.fileName = fileName;
        this.lines = 0;
    }

    public int getLines() {
        return this.lines;
    }

    public void run() {
        try {
            FileReader in = new FileReader(this.fileName);
            BufferedReader br = new BufferedReader(in);

            while (br.readLine() != null) {
                this.lines++;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
