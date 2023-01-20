package src.main;

import java.util.Scanner;

public class Main {
    private static final int PRICE_FRONT_HALF = 10;
    private static final int PRICE_BACK_HALF = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = getNumberOfRows(scanner);
        int seats = getNumberOfSeats(scanner);
        int[][] cinema = new int[rows][seats];

        int opt;
        do {
            opt = showMenu(scanner);
            switch (opt) {
                case 1 -> printSeating(cinema);
                case 2 -> buyTicket(scanner, cinema, rows, seats);
                case 3 -> showStatistics(cinema, rows, seats);
            }
        } while (opt != 0);
    }

    private static int getNumberOfRows(Scanner scanner) {
        System.out.println("Enter the number of rows:");
        return scanner.nextInt();
    }

    private static int getNumberOfSeats(Scanner scanner) {
        System.out.println("Enter the number of seats in each row:");
        return scanner.nextInt();
    }

    private static int showMenu(Scanner scanner) {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scanner.nextInt();
    }

    // Print seating arrangement
    private static void printSeating(int[][] cinema) {
        System.out.print("\nCinema:\n  ");

        int rows = cinema.length;
        int seats = cinema[0].length;

        // Print column numbers
        for (int i = 1; i <= seats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print seating arrangement
        for (int i = 0; i < rows; i++) {
            System.out.print(i+1 + " ");
            for (int j = 0; j < seats; j++) {
                if (cinema[i][j] == 1) {
                    System.out.print("B ");
                } else {
                    System.out.print("S ");
                }
            }
            System.out.println();
        }
    }

    private static void buyTicket(Scanner scanner, int[][] cinema, int rows, int seats) {
        boolean purchased = false;
        do {
            // Read row and seat number
            System.out.println();
            System.out.println("Enter a row number:");
            int rowNum = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatNum = scanner.nextInt();

            if (rowNum - 1 >= rows || seatNum - 1 >= seats || rowNum <= 0 || seatNum <= 0) {
                System.out.println("Wrong input!");
            } else {
                // Mark seat as bought
                if (cinema[rowNum - 1][seatNum - 1] == 0) {
                    cinema[rowNum - 1][seatNum - 1] = 1;

                    // Calculate ticket price
                    System.out.println("Ticket price: $" + getPrice(rows, seats, rowNum));
                    purchased = true;

                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            }
        } while (!purchased);
    }

    // Calculate ticket price
    private static int getPrice(int rows, int seats, int selectedRow) {
        if (rows * seats <= 60) {
            return PRICE_FRONT_HALF;
        } else {
            return (selectedRow <= rows / 2) ? PRICE_FRONT_HALF : PRICE_BACK_HALF;
        }
    }

    private static int currentIncome(int[][] cinema, int rows, int seats) {
        int currentIncome = 0;
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[0].length; j++) {
                if (cinema[i][j] == 1) {
                    currentIncome += getPrice(rows, seats, i+1);
                }
            }
        }
        return currentIncome;
    }

    private static void showStatistics(int[][] cinema, int rows, int seats) {
        int totalSeats = rows * seats;
        int totalIncome = totalSeats <= 60 ? totalSeats * PRICE_FRONT_HALF : rows / 2 * seats * PRICE_FRONT_HALF
                + (rows - rows / 2) * seats * PRICE_BACK_HALF;
        int occupiedSeats = 0;

        for (int[] ints : cinema) {
            for (int j = 0; j < cinema[0].length; j++) {
                if (ints[j] == 1) {
                    occupiedSeats++;
                }
            }
        }

        System.out.println();
        System.out.println("Number of purchased tickets: " + occupiedSeats);
        System.out.printf("Percentage: %.2f%%%n", (float)occupiedSeats * 100 / totalSeats);
        System.out.println("Current income: $" + currentIncome(cinema, rows, seats));
        System.out.println("Total income: $" + totalIncome);
    }
}
