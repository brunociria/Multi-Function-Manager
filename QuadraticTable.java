package net.ciria.graphmaster.independent;

import java.util.Scanner;

import parser.MathExpression;

public class QuadraticTable {

    private static final int RANGE = 5;
    private static final double RESOLUTION = 1;
    public static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Type a function that follows f(x)=");
        String input = SCANNER.nextLine();

        MathExpression expression = new MathExpression("x=0;" + input);

        for (double x = -RANGE; x <= RANGE; x += RESOLUTION) {
            expression.setValue("x", String.valueOf(x));
            String y = expression.solve();
            System.out.println(x + " | " + y);
        }
    }
}
