package lw01.Unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src/lw01/Unguided/washes.txt");
            Scanner scanner = new Scanner(file);

            if (!scanner.hasNextInt()) {
                scanner.close();
                return;
            }

            int totalRecords = scanner.nextInt();
            WashService[] services = new WashService[totalRecords];

            for (int i = 0; i < totalRecords; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                int units = scanner.nextInt();

                WashService service = null;
                if (type.equalsIgnoreCase("MOTORCYCLE")) {
                    service = new MotorcycleWash(id, days);
                } else if (type.equalsIgnoreCase("CAR")) {
                    service = new CarWash(id, days);
                }

                services[i] = service;
                
                // Jika ingin langsung mencetak dengan mempertimbangkan units sesuai soal:
                if (services[i] != null) {
                    // Polymorphism murni: memanggil summary atau mencetak langsung dengan calculateCharge(units)
                    System.out.println(services[i].getId() + " | " + services[i].label() + " | " + services[i].calculateCharge(units));
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File washes.txt tidak ditemukan: " + e.getMessage());
        }
    }
}
