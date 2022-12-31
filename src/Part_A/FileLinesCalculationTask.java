package Part_A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class FileLinesCalculationTask implements Callable <Integer> {
    private String fileName;
    private volatile Integer lines;

    public FileLinesCalculationTask (String fileName) {
        this.fileName = fileName;
        this.lines = 0;
    }

    @Override
    public Integer call() throws Exception {
        try {
            FileReader in = new FileReader("src\\Part_A\\WrittenFiles\\" + this.fileName);
            BufferedReader br = new BufferedReader(in);

            while (br.readLine() != null) {
                this.lines++;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.lines;
    }
}
