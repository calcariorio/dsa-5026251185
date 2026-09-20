package lw01.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<PrintJob> jobs = new ArrayList<>();
        
        try {
            File file = new File("src/lw01/prelab/jobs.txt");
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    String type = parts[0];
                    String id = parts[1];
                    int pages = Integer.parseInt(parts[2]);
                    
                    if (type.equalsIgnoreCase("MONO")) {
                        jobs.add(new MonoPrint(id, pages));
                    } else if (type.equalsIgnoreCase("COLOUR")) {
                        jobs.add(new ColourPrint(id, pages));
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File jobs.txt tidak ditemukan!");
            return;
        }

        for (PrintJob job : jobs) {
            System.out.println(job.summary());
        }
    }
}
