package lw04.Unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("parcels.txt");
        if (!file.exists()) {
            file = new File("src/lw04/unguided/parcels.txt");
        }
        if (!file.exists()) {
            file = new File("src/lw04/Unguided/parcels.txt");
        }

        try (Scanner sc = new Scanner(file)) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int m = sc.nextInt();
            int[] targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = sc.nextInt();
            }

            int[] after2Passes = new int[n];

            
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (arr[j] < arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
                if (i == 1) {
                    for (int k = 0; k < n; k++) {
                        after2Passes[k] = arr[k];
                    }
                }
            }
            System.out.println("==== After 2 Passes ====");
            printArray(after2Passes);

            System.out.println("==== Fully Sorted ====");
            printArray(arr);

            System.out.println("==== Search Results ====");
            for (int t = 0; t < m; t++) {
                int target = targets[t];
                int low = 0;
                int high = n - 1;
                int steps = 0;
                int foundIndex = -1;

                while (low <= high) {
                    steps++;
                    int mid = (low + high) / 2;

                    if (arr[mid] == target) {
                        foundIndex = mid;
                        break;
                    } else if (arr[mid] < target) {
                        high = mid - 1; 
                    } else {
                        low = mid + 1;  
                    }
                }

                if (foundIndex != -1) {
                    System.out.println(target + ": FOUND at index " + foundIndex + " (steps: " + steps + ")");
                } else {
                    System.out.println(target + ": NOT FOUND (steps: " + steps + ")");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File parcels.txt tidak ditemukan!");
        }
    }

    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i == arr.length - 1 ? "" : " "));
        }
        System.out.println();
    }
}
