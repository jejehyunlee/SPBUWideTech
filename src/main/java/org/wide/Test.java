package org.wide;

import java.util.*;

public class Test {

    static class Vehicle {
        String type;
        int maxCapacity;
        int currentCapacity;

        Vehicle(String type, int maxCapacity) {
            this.type = type;
            this.maxCapacity = maxCapacity;
            this.currentCapacity = maxCapacity;
        }
    }

    static class Transaction {
        String buyer;
        String vehicleType;
        int amount;
        Date timestamp;

        Transaction(String buyer, String vehicleType, int amount) {
            this.buyer = buyer;
            this.vehicleType = vehicleType;
            this.amount = amount;
            this.timestamp = new Date(); // current time
        }
    }

    private static final Map<String, Vehicle> vehicles = new HashMap<>();
    private static final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int M = Integer.parseInt(scanner.nextLine().trim());

        // Read vehicle registrations
        for (int i = 0; i < M; i++) {
            String[] parts = scanner.nextLine().split("-");
            String name = parts[0];
            String type = parts[1];
            int maxCapacity = type.equals("mobil") ? 50 : 10;
            vehicles.put(name + "-" + type, new Vehicle(type, maxCapacity));
        }

        // Process commands until end-of-file
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.startsWith("isi;")) {
                processRefill(input);
            } else if (input.startsWith("data pembeli")) {
                printVehicleStatus();
            } else if (input.startsWith("data transaksi")) {
                printTransactions();
            } else if (input.startsWith("total;")) {
                printTotalFuel(input);
            } else if (input.startsWith("refill;")) {
                refillFuel(input);
            } else if (input.startsWith("cek;")) {
                checkStatus(input);
            }
        }
    }

    private static void processRefill(String input) {
        String[] parts = input.split(";");
        String[] vehicleParts = parts[1].split("-");
        String buyer = vehicleParts[0];
        String vehicleType = vehicleParts[1];
        int amount = Integer.parseInt(parts[2]);

        String key = buyer + "-" + vehicleType;
        Vehicle vehicle = vehicles.get(key);
        if (vehicle != null && vehicle.currentCapacity >= amount) {
            vehicle.currentCapacity -= amount;
            transactions.add(new Transaction(buyer, vehicleType, amount));
        }
    }

    private static void printVehicleStatus() {
        List<Map.Entry<String, Vehicle>> list = new ArrayList<>(vehicles.entrySet());
        list.sort(Map.Entry.comparingByKey());

        for (Map.Entry<String, Vehicle> entry : list) {
            Vehicle vehicle = entry.getValue();
            System.out.println(entry.getKey() + " " + vehicle.currentCapacity);
        }
    }

    private static void printTransactions() {
        transactions.sort(Comparator.comparing(t -> t.timestamp));
        for (Transaction transaction : transactions) {
            System.out.println(transaction.buyer + "-" + transaction.vehicleType + " " + transaction.amount + " " + transaction.timestamp);
        }
    }

    private static void printTotalFuel(String input) {
        String vehicleType = input.split(";")[1];
        int total = transactions.stream()
                .filter(t -> t.vehicleType.equals(vehicleType))
                .mapToInt(t -> t.amount)
                .sum();
        System.out.println("Total " + vehicleType + " " + total);
    }

    private static void refillFuel(String input) {
        String[] parts = input.split(";");
        String[] vehicleParts = parts[1].split("-");
        String buyer = vehicleParts[0];
        String vehicleType = vehicleParts[1];

        String key = buyer + "-" + vehicleType;
        Vehicle vehicle = vehicles.get(key);
        if (vehicle != null) {
            // Maximum refill amount is 10 liters
            int refillAmount = 10;
            // Calculate the amount needed to fill the tank to its maximum capacity
            int neededAmount = vehicle.maxCapacity - vehicle.currentCapacity;

            // Determine the actual refill amount (minimum of needed amount and 10 liters)
            int actualRefillAmount = Math.min(refillAmount, neededAmount);
            vehicle.currentCapacity += actualRefillAmount;

            // Ensure the capacity does not exceed the maximum capacity
            if (vehicle.currentCapacity > vehicle.maxCapacity) {
                vehicle.currentCapacity = vehicle.maxCapacity;
            }

            System.out.println("Refilled " + actualRefillAmount + " liters for " + key);
        } else {
            System.out.println("Vehicle not found.");
        }
    }


    private static void checkStatus(String input) {
        String buyer = input.split(";")[1];
        vehicles.forEach((key, vehicle) -> {
            if (key.startsWith(buyer + "-")) {
                System.out.println(key + " " + vehicle.currentCapacity + " " + (vehicle.maxCapacity - vehicle.currentCapacity));
            }
        });
    }


}
