package lw03.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        solveProblem1();
        System.out.println();
        solveProblem2();
        System.out.println();
        solveProblem3();
    }

    public static void solveProblem1() {
        List<String> playlist = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/lw03/prelab/playlist.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("ADD ")) {
                    String song = line.substring(4).trim();
                    playlist.add(song);
                } else if (line.startsWith("INSERT ")) {
                    String payload = line.substring(7).trim();
                    int spaceIdx = payload.indexOf(' ');
                    if (spaceIdx != -1) {
                        int index = Integer.parseInt(payload.substring(0, spaceIdx));
                        String song = payload.substring(spaceIdx + 1).trim();
                        if (index >= 0 && index <= playlist.size()) {
                            playlist.add(index, song);
                        }
                    }
                } else if (line.startsWith("REMOVE ")) {
                    String song = line.substring(7).trim();
                    playlist.remove(song);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("playlist.txt not found.");
        }

        System.out.println("===== Problem 1");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

    public static void solveProblem2() {
        Set<String> uniqueParticipants = new LinkedHashSet<>();
        int duplicateCount = 0;

        try (Scanner scanner = new Scanner(new File("src/lw03/prelab/participants.txt"))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) continue;

                boolean added = uniqueParticipants.add(name);
                if (!added) {
                    duplicateCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("participants.txt not found.");
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + uniqueParticipants.size());
        int index = 1;
        for (String participant : uniqueParticipants) {
            System.out.println(index + ". " + participant);
            index++;
        }
        System.out.println("Duplicate registrations: " + duplicateCount);
    }

    public static void solveProblem3() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;

        try (Scanner scanner = new Scanner(new File("src/lw03/prelab/inventory.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                if (parts.length < 3) continue;

                String type = parts[0];
                String product = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                if (type.equals("ADD")) {
                    inventory.put(product, inventory.getOrDefault(product, 0) + quantity);
                } else if (type.equals("SELL")) {
                    if (inventory.containsKey(product) && inventory.get(product) >= quantity) {
                        inventory.put(product, inventory.get(product) - quantity);
                    } else {
                        failedSales++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("inventory.txt not found.");
        }

        System.out.println("===== Problem 3 =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }
}