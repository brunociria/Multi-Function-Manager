package net.ciria.graphmaster.independent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class FunctionGrapher extends JPanel {

    // Constants
    private static final int TWO = 2;
    private static final int WINDOW_SIZE = 400;
    private static final int POINT_SIZE = 0;
    private static final int RANGE = 100;
    private static final double SCALE_X = 10.0;
    private static final double SCALE_Y = 10.0;
    private static final double RESOLUTION = 0.1;
    private static final float LINE_THICKNESS = 2.0F;

    // Scanner and Logger
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger("Logger");

    // Initialize a list for the calculated points
    private List<Point> pointArray = new ArrayList<>();

    // Suppress sonarLint
    @SuppressWarnings("java:S5612")
    public static void main(String[] args) {

        // Welcome message
        System.out.println();
        System.out.print("Welcome to the multi function grapher. Press enter to begin: ");
        SCANNER.nextLine();
        System.out.println("Type a function that follows either f(x) = ax² + bx + c, or f(x)= bx + c");
        System.out.println();

        // Initialize function coefficients and inputFunction String
        String inputFunction = "no function specified";
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;

        boolean inputValid = false;
        while (!inputValid) {
            try {
                // Get the values for the function
                System.out.print("What's the value of 'a'? ");
                a = SCANNER.nextDouble();
                System.out.print("What's the value of 'b'? ");
                b = SCANNER.nextDouble();
                System.out.print("What's the value of 'c'? ");
                c = SCANNER.nextDouble();
                System.out.println();

                // Print input
                inputFunction = "f(x)= (" + a + ")x² + (" + b + ")x + (" + c + ")";
                System.out.println(inputFunction);
                System.out.println();

                // If input is valid, exit the loop
                inputValid = true;
            }
            // Catch input errors
            catch (InputMismatchException e) {
                System.out.println("Input Mismatch! Please enter Numbers");
                LOGGER.log(Level.FINEST, "InputMismatchException! " + e);

                // Clear scanner buffer and jump to the next line
                SCANNER.nextLine();
                System.out.println();
            }
        }
        // Close scanner
        System.out.println();
        SCANNER.close();

        // Restate local variables as final so that they can be used in a bigger scope
        final String finalInputFunction = inputFunction;
        final double finalA = a;
        final double finalB = b;
        final double finalC = c;

        // Add SwingUtilities.invokeLater method to run the JFrame method on the event
        // dispatching thread (Swing Bullshit)
        SwingUtilities.invokeLater(() -> {

            // Create application window and set its properties
            JFrame frame = new JFrame("Graph of " + finalInputFunction);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
            frame.setVisible(true);

            // Add graphical interface to the frame
            FunctionGrapher pointGraph = new FunctionGrapher();
            frame.add(pointGraph);

            // Adds the calculated points to the pointArray list
            for (double x = -RANGE; x <= RANGE; x += RESOLUTION) {
                double y = calculateFunction(finalA, finalB, finalC, x);
                pointGraph.pointArray.add(new Point((int) (x * SCALE_X), (int) (y * SCALE_Y)));
            }

        });
    }

    // Function to calculate the value of the function
    public static double calculateFunction(double a, double b, double c, double x) {
        return a * x * x + b * x + c;
    }

    // Creates graphical interface using awt.Graphics paintComponent method
    @Override
    protected void paintComponent(Graphics g) {
        // Preserve default painting behavior by calling super method
        super.paintComponent(g);
        // Convert the Graphics object to Graphics2D for advanced rendering.
        Graphics2D g2d = (Graphics2D) g;
        // Enables anti-aliasing for better readability
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculates X and Y axis coordinates
        int centerX = getWidth() / TWO;
        int centerY = getHeight() / TWO;

        g2d.setColor(Color.BLACK);
        // Draws X-axis
        g2d.drawLine(0, centerY, getWidth(), centerY);
        // Draws Y-axis
        g2d.drawLine(centerX, 0, centerX, getHeight());

        // Goes thru the list of points and draws them in each iteration
        g2d.setColor(Color.GRAY);
        for (Point coordinates : pointArray) {
            int x = (int) coordinates.getX() + centerX;
            // Negate the Y-coordinate to account for the flipped Y-axis on JPanels! (-)
            int y = (int) -coordinates.getY() + centerY;
            // Creates relation between POINT_SIZE so that x,y is the center
            g2d.fillOval(x - POINT_SIZE, y - POINT_SIZE, POINT_SIZE * TWO, POINT_SIZE * TWO);
        }

        // Connect points with lines
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(LINE_THICKNESS));
        for (int i = 1; i < pointArray.size(); i++) {

            // Gets coordinates for the points in pointArray
            Point previousPoint = pointArray.get(i - 1);
            Point currentPoint = pointArray.get(i);

            // Negate the Y-coordinate to account for the flipped Y-axis on JPanels! (-)
            int x1 = (int) previousPoint.getX() + centerX;
            int y1 = (int) -previousPoint.getY() + centerY;
            int x2 = (int) currentPoint.getX() + centerX;
            int y2 = (int) -currentPoint.getY() + centerY;

            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
