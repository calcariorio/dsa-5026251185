package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

public class BankTransactionProcessor {
    public static void main(String[] args) {
        LinkedList<String[]> transactionList = new LinkedList<>();
        
        File file = new File("src/lw02/prelab/transactions.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    if (parts.length == 3) {
                        transactionList.add(parts);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: transactions.txt file not found.");
            return;
        }

        LinkedList<String[]> customerList = new LinkedList<>();
        for (String[] tx : transactionList) {
            String customerName = tx[0];
            boolean exists = false;
            for (String[] customer : customerList) {
                if (customer[0].equals(customerName)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                customerList.add(new String[] { customerName, "0" });
            }
        }

        Queue<String[]> transactionQueue = new LinkedList<>(transactionList);

        Stack<String[]> failedStack = new Stack<>();


        while (!transactionQueue.isEmpty()) {
            String[] tx = transactionQueue.poll();
            String name = tx[0];
            String type = tx[1];
            long amount = Long.parseLong(tx[2]);


            String[] targetCustomer = null;
            for (String[] customer : customerList) {
                if (customer[0].equals(name)) {
                    targetCustomer = customer;
                    break;
                }
            }

            if (targetCustomer != null) {
                long currentBalance = Long.parseLong(targetCustomer[1]);
                if (type.equals("DEPOSIT")) {
                    currentBalance += amount;
                    targetCustomer[1] = String.valueOf(currentBalance);
                } else if (type.equals("WITHDRAW")) {
                    if (amount > currentBalance) {
                        // Failed transaction: push to stack
                        failedStack.push(tx);
                    } else {
                        currentBalance -= amount;
                        targetCustomer[1] = String.valueOf(currentBalance);
                    }
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] customer : customerList) {
            System.out.println(customer[0] + ": " + customer[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failedStack.isEmpty()) {
            String[] failedTx = failedStack.pop();
            System.out.println(failedTx[0] + " " + failedTx[1] + " " + failedTx[2]);
        }
    }
}