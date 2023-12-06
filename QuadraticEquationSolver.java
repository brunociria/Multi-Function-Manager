package net.ciria.graphmaster.independent;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.InputMismatchException;

public class QuadraticEquationSolver {

    // Constants
    public static final int TWO = 2;
    public static final int FOUR = 4;
    // Scanner and Logger
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger("Logger");

    public static void main(String[] args) {
        // Welcome message
        System.out.println();
        System.out.print("Welcome to the quadratic equation solver. Press enter to begin: ");
        SCANNER.nextLine();
        System.out.println("Type a quadratic equation that follows f(x) = ax² + bx + c.");
        System.out.println();

        try {
            quadraticEquationSolver();
        }
        // Catch input errors
        catch (InputMismatchException e) {
            System.out.println("Input Mismatch! Please enter Numbers");
            LOGGER.log(Level.FINEST, "InputMismatchException! " + e);

            // Clear scanner buffer
            SCANNER.nextLine();
            // Restart main method
            main(args);
        }
        // Close scanner
        System.out.println();
        SCANNER.close();
    }

    // Suppress sonarLint
    @SuppressWarnings("java:S1244")
    public static void quadraticEquationSolver() {

        // Get a quadratic equation
        System.out.print("What's the value of 'a'? ");
        double a = SCANNER.nextDouble();

        // Check if function is quadratic
        if (a == 0) {
            System.out.println("Function will not be quadratic since 'a' is zero.");
            // Clear scanner buffer and restart main method
            SCANNER.nextLine();
            main(null);
        }

        else {
            System.out.print("What's the value of 'b'? ");
            double b = SCANNER.nextDouble();
            System.out.print("What's the value of 'c'? ");
            double c = SCANNER.nextDouble();
            System.out.println();

            // Print input
            System.out.println("f(x)= (" + a + ")x² + (" + b + ")x + (" + c + ")");
            System.out.println();

            // Get value for delta
            double delta = b * b - FOUR * a * c;

            // Get value for roots
            if (delta > 0) {
                double x1 = (-b + Math.sqrt(delta)) / (TWO * a);
                double x2 = (-b - Math.sqrt(delta)) / (TWO * a);
                System.out.println("The roots are " + x1 + " and " + x2);
            } 
            else if (delta == 0) {
                double x = -b / (TWO * a);
                System.out.println("The root is " + x);
            } 
            else {
                System.out.println("No real roots since delta is smaller than zero.");
            }
        }
    }
}
