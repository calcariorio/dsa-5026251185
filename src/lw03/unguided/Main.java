package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> registeredStudents = new HashSet<>();
        
        try {
            Scanner regScanner = new Scanner(new File("src/lw03/unguided/registrations.txt"));
            while (regScanner.hasNextLine()) {
                String id = regScanner.nextLine().trim();
                if (!id.isEmpty()) {
                    registeredStudents.add(id);
                }
            }
            regScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File registrations.txt tidak ditemukan!");
            return;
        }
        Set<String> checkedInStudents = new HashSet<>();
        
        System.out.println("===== Event Check-In Results =====");
        
        int successfulCheckIns = 0;
        int rejectedAttempts = 0;


        try {
            Scanner checkinScanner = new Scanner(new File("src/lw03/unguided/checkins.txt"));
            while (checkinScanner.hasNextLine()) {
                String id = checkinScanner.nextLine().trim();
                if (id.isEmpty()) continue;

                if (!registeredStudents.contains(id)) {
                    System.out.println(id + ": Rejected (not registered)");
                    rejectedAttempts++;
                } else {
                    if (checkedInStudents.contains(id)) {
                        System.out.println(id + ": Rejected (already checked in)");
                        rejectedAttempts++;
                    } else {
                        System.out.println(id + ": Checked in");
                        checkedInStudents.add(id);
                        successfulCheckIns++;
                    }
                }
            }
            checkinScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File checkins.txt tidak ditemukan!");
            return;
        }
        int totalRegistered = registeredStudents.size();
        int absentStudents = totalRegistered - successfulCheckIns;

        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + totalRegistered);
        System.out.println("Successful check-ins: " + successfulCheckIns);
        System.out.println("Absent students: " + absentStudents);
        System.out.println("Rejected attempts: " + rejectedAttempts);
    }
}
