package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

public class RestaurantOrderProcessing {
    public static void main(String[] args) {
        LinkedList<String[]> orderList = new LinkedList<>();
        try {
            Scanner fileScanner = new Scanner(new File("src/lw02/unguided/orders.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");
                String name = parts[0];
                String food = parts[1];
                String drink = parts[2];
                String table = parts[3];
                orderList.add(new String[]{name, food, drink, table});
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File orders.txt tidak ditemukan!");
            return;
        }

        LinkedList<String[]> foodStock = new LinkedList<>();
        foodStock.add(new String[]{"Bakso", "2"});
        foodStock.add(new String[]{"Sate", "1"});
        foodStock.add(new String[]{"Soto", "2"});

        LinkedList<String[]> drinkStock = new LinkedList<>();
        drinkStock.add(new String[]{"EsTeh", "4"});
        drinkStock.add(new String[]{"EsJeruk", "2"});

        Queue<String[]> orderQueue = new LinkedList<>();
        for (int i = 0; i < orderList.size(); i++) {
            orderQueue.add(orderList.get(i));
        }

        LinkedList<String[]> successfulOrders = new LinkedList<>();
        Stack<String[]> failedOrdersStack = new Stack<>();

        while (!orderQueue.isEmpty()) {
            String[] currentOrder = orderQueue.poll();
            String foodOrdered = currentOrder[1];
            String drinkOrdered = currentOrder[2];

            boolean foodAvailable = true;
            boolean drinkAvailable = true;

            int foodIndex = -1;
            if (!foodOrdered.equals("-")) {
                for (int i = 0; i < foodStock.size(); i++) {
                    if (foodStock.get(i)[0].equals(foodOrdered)) {
                        foodIndex = i;
                        int stock = Integer.parseInt(foodStock.get(i)[1]);
                        if (stock <= 0) {
                            foodAvailable = false;
                        }
                        break;
                    }
                }
            }

            int drinkIndex = -1;
            if (!drinkOrdered.equals("-")) {
                for (int i = 0; i < drinkStock.size(); i++) {
                    if (drinkStock.get(i)[0].equals(drinkOrdered)) {
                        drinkIndex = i;
                        int stock = Integer.parseInt(drinkStock.get(i)[1]);
                        if (stock <= 0) {
                            drinkAvailable = false;
                        }
                        break;
                    }
                }
            }

            if (foodAvailable && drinkAvailable) {
                if (foodIndex != -1) {
                    int currentStock = Integer.parseInt(foodStock.get(foodIndex)[1]);
                    foodStock.get(foodIndex)[1] = String.valueOf(currentStock - 1);
                }
                if (drinkIndex != -1) {
                    int currentStock = Integer.parseInt(drinkStock.get(drinkIndex)[1]);
                    drinkStock.get(drinkIndex)[1] = String.valueOf(currentStock - 1);
                }
                successfulOrders.add(currentOrder);
            } else {
                failedOrdersStack.push(currentOrder);
            }
        }

        System.out.println("=== Successfully Processed Orders ===");
        for (int i = 0; i < successfulOrders.size(); i++) {
            String[] ord = successfulOrders.get(i);
            System.out.println(ord[0] + " " + ord[1] + " " + ord[2] + " " + ord[3]);
        }

        System.out.println("=== Remaining Food Stock ===");
        for (int i = 0; i < foodStock.size(); i++) {
            System.out.println(foodStock.get(i)[0] + ": " + foodStock.get(i)[1]);
        }

        System.out.println("=== Remaining Drink Stock ===");
        for (int i = 0; i < drinkStock.size(); i++) {
            System.out.println(drinkStock.get(i)[0] + ": " + drinkStock.get(i)[1]);
        }

        System.out.println("=== Failed Orders ===");
        while (!failedOrdersStack.isEmpty()) {
            String[] ord = failedOrdersStack.pop();
            System.out.println(ord[0] + " " + ord[1] + " " + ord[2] + " " + ord[3]);
        }
    }
}
