package net.ciria.graphmaster.independent;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.InputMismatchException;

public class ProjectileMotion {

	// Constants
	public static final double G = 9.81;
	public static final int TWO = 2;
	public static final int RIGHT_ANGLE = 90;
	public static final int STRAIGHT_ANGLE = 180;
	// Scanner and Logger
	public static final Scanner SCANNER = new Scanner(System.in);
	private static final Logger LOGGER = Logger.getLogger("Logger");

	public static void main(String[] args) {
		// Welcome message
		System.out.println();
		System.out.print("Welcome to the projectile motion calculator. Press enter to begin: ");
		SCANNER.nextLine();
		System.out.println("Type in values for initial speed (m/s) and object release angle (degrees). ");
		System.out.println();

		try {
			projectileMotionCalc();
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

	public static void projectileMotionCalc() {

		// Get initial velocity
		System.out.print("What's the initial velocity in m/s? ");
		double initialVelocity = SCANNER.nextDouble();

		if (initialVelocity < 0) {
			System.out.println("Initial velocity can't have a negative value.");
			// Clear scanner buffer and restart main method without any new args
			SCANNER.nextLine();
			main(null);
		}

		else {
			// Get release angle
			System.out.print("What's the release angle in degrees? ");
			double releaseAngle = SCANNER.nextDouble();

			// Check if release angle is within parameters
			if (releaseAngle < 0 || releaseAngle > RIGHT_ANGLE) {
				System.out.println("Release angle must be between 0 and 90 degrees.");
				// Clear scanner buffer and restart main method without any new args
				SCANNER.nextLine();
				main(null);
			} 
			else {
				// Print input
				System.out.println();
				System.out.println("Initial Velocity: " + initialVelocity + " m/s");
				System.out.println("Release angle: " + releaseAngle + "°");

				// Get trigonometry values
				double releaseAngleRadian = releaseAngle * (Math.PI / STRAIGHT_ANGLE);
				double releaseAngleSin = Math.sin(releaseAngleRadian);
				double releaseAngleCos = Math.cos(releaseAngleRadian);

				// Calculate a, Δx and Δy
				double maximumHeight = (Math.pow(initialVelocity, TWO) * Math.pow(releaseAngleSin, TWO)) / (TWO * G);
				double range = (TWO * Math.pow(initialVelocity, TWO) * releaseAngleSin * releaseAngleCos) / G;
				double a = (maximumHeight * -1) / Math.pow(0 - range / TWO, TWO);

				// Round and print results
				maximumHeight = Math.round(maximumHeight);
				range = Math.round(range);

				System.out.println();
				System.out.println("Maximum Height: " + maximumHeight + " m");
				System.out.println("range: " + range + " m");

				System.out.println();
				System.out.println(
						"f(x)= (" + a + ") * [x-(" + range / TWO + ")]² + (" + maximumHeight + ")");
			}
		}
	}
}
