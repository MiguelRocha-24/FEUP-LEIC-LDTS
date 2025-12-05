package feup2526.ldts.t02g03.model;

import java.io.*;
import java.util.Scanner;

public abstract class Counter {
    protected int count;
    protected String filename;

    public Counter(String filename) {
        this.filename = filename;
        this.count = load();
    }

    public void increment() {
        this.count++;
        save();
    }

    public void increment(int i) {
        this.count += i;
        save();
    }

    public void decrement() {
        this.count--;
        save();
    }

    public void decrement(int i) {
        this.count -= i;
        save();
    }

    public int getCount() {
        return this.count;
    }

    protected void save() {
        try {
            File file = new File(filename);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println(count);
            }
        } catch (IOException e) {
            System.err.println("Error saving counter: " + e.getMessage());
        }
    }

    protected int load() {
        File file = new File(filename);
        if (!file.exists()) {
            return 0;
        }
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            return 0;
        } catch (FileNotFoundException e) {
            System.err.println("Error loading counter: " + e.getMessage());
            return 0;
        }
    }
}
