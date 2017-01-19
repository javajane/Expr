package com.MiscellaneousProjects;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //boolean quit = false;

       // System.out.println("Enter a mathematical expression");
        //System.out.println("Valid operators are +,-,*,/,^ and sin, cos, abs, tab, sec");
        //System.out.println("Eg: sin(60), 20+(50-30)");
        //System.out.println("Enter quit to end the program");

        boolean quit=false;

        while (!quit){
            System.out.println("Enter a mathematical expression");
            System.out.println("Valid operators are +,-,*,/,^ and sin, cos, abs, tab, sec");
            System.out.println("Eg: sin(60), 20+(50-30)");
            System.out.println("Enter quit or hit 'enter' to end the program");
            String input=scanner.nextLine();
            if(input.isEmpty() || input.toUpperCase().charAt(0) == 'Q' ){
                quit=true;
            }
            else {
                Expr myexpr = new Expr(input);
                double value= myexpr.value();
                System.out.println("\nyou entered: " + input);
                System.out.println("Value: " + value + "\n");

            }

        }



       // String input = scanner.nextLine();
       // System.out.println("INPUT: " + input);

        //String input = "5^2+80";
      // String input = "(sin(90)+90)*(2*(5+6*(3/2)*((9+8))))*99";

        //String input="2*(5+6*(3/2)*((9+8)))";



       // Expr myexpr = new Expr(input);



     //   myexpr.value();




    }


    // }
}
