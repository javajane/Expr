package com.MiscellaneousProjects;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pinaki on 12/15/2016.
 */
public class Expr {
    private String definition;

    public Expr(String definition) {
        if (validateExpr(definition)) {
            this.definition = definition;
        } else {
            throw new IllegalArgumentException(definition);
        }
    }

    private boolean validateExpr(String def) {
        char[] operators = def.toCharArray();
        int operatorcount = 0;
        for (char operator : operators) {
            switch (operator) {
                case '+':
                    operatorcount++;
                    break;
                case '-':
                    operatorcount++;
                    break;
                case '*':
                    operatorcount++;
                    break;
                case '/':
                    operatorcount++;
                    break;
                case '^':
                    operatorcount++;
                    break;
            }
        }

        //String[] arguments = def.split("'+'||'-'||'/'||'*'||'^'");
        if (operatorcount != 0) {
            String[] arguments = def.split("[-^*+/]");

            if (arguments.length >= operatorcount + 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }

    }


    public double value() {

        String input = this.definition;

        String inputstr = calcmatching(input);

        boolean paran = true;
        while (paran) {
            int[] parans = new int[2];

            parans = paranmatching(inputstr);

            if (parans[0] == -1) {
                paran = false;
            } else {
                String leftstr = inputstr.substring(0, parans[0]);
                String rightstr = inputstr.substring(parans[1] + 1, inputstr.length());
                String innerstr = inputstr.substring(parans[0]+1, parans[1]);

                innerstr=precedenceOrder(innerstr);
                //********************SAVED BLOCK START
                //************************SAVED BLOCK END
                //String innerstr = precedenceOrder(inputstr.substring(parans[0] + 1, parans[1]));

                inputstr=leftstr+innerstr+rightstr;


            }
        }

        if(inputstr.matches(".*[*/+-\\^].*")) {
            inputstr=precedenceOrder(inputstr);
        }

        System.out.println("RETURNING FROM VALUE FUNCTION: " + inputstr);
        double returnval =  Double.valueOf(inputstr);
        if(Double.valueOf(inputstr).isNaN()){
            System.out.println("ERROR OCCURRED");
            return -1d;
        }
        else {
            return Double.valueOf(inputstr);
        }


    }

    public static double value(String calcstr, double inval) {

        double returnval = -1d;

        switch (calcstr) {
            case "sin":
                returnval = Math.sin(Math.toRadians(inval));
                break;
            case "cos":
                System.out.println("COSINE VALUE OF " + inval + " is " + Math.cos(Math.toRadians(inval)));
                returnval = Math.cos(Math.toRadians(inval));
                break;
            case "tan":
                returnval = Math.tan(Math.toRadians(inval));
                break;
            case "log":
                returnval = Math.log(Math.toRadians(inval));
                break;

        }

        return returnval;
    }

    public static double value(double firstval, char operator, double secondval) {

        double returnval;
        switch (operator) {
            case '+':
                returnval = firstval + secondval;
                break;
            case '-':
                returnval = firstval - secondval;
                break;
            case '/':
                returnval = firstval / secondval;
                break;
            case '*':
                returnval = firstval * secondval;
                break;
            case '^':
                returnval = Math.pow(firstval, secondval);
                break;
            default:
                returnval=0d;
        }
        return returnval;

    }


    public double value(double leftnum, String operator, double rightnum) {

        double returnval;
        switch (operator) {
            case "+":
                returnval = leftnum + rightnum;
                break;
            case "-":
                returnval = leftnum - rightnum;
                break;
            case "*":
                returnval = leftnum * rightnum;
                break;
            case "/":
                returnval = leftnum / rightnum;
                break;
            default:
                returnval=0d;
        }

        return returnval;

    }

    public static int[] paranmatching(String input) {

        Pattern p = Pattern.compile("");
        ArrayList<Integer> openparans = new ArrayList<>();
        HashMap<Integer, Integer> matchparan = new HashMap<>();
        HashMap<Integer, Integer> reversematchparan = new HashMap<>();

        //get innermost paran

        char[] chararray = input.toCharArray();
        int lastopenindex = -1;

        for (int i = 0; i < chararray.length; i++) {
            if (chararray[i] == '(') {
                lastopenindex++;
                openparans.add(lastopenindex, i);
            } else if (chararray[i] == ')') {
                matchparan.put(openparans.get(lastopenindex), i);
                reversematchparan.put(i, openparans.get(lastopenindex));
                openparans.remove(lastopenindex);
                lastopenindex--;
            }
        }

        if (reversematchparan.isEmpty()) {
            return (new int[]{-1, -1});
        } else {

            int[] values = new int[reversematchparan.size()];
            int i = 0;
            for (Integer key : reversematchparan.keySet()) {
                values[i] = key;
                i++;
            }

            // Integer[] values = (Integer []) reversematchparan.entrySet().toArray();
            Arrays.sort(values);

            return (new int[]{reversematchparan.get(values[0]), values[0]});
        }


    }

    public static String calcmatching(String input) {


        Pattern sine = Pattern.compile("sin|cos|tan|log");

        String sinestr = "";
        boolean done = false;
        //String firsthalfstr = "";
        //String secondhalfstr = "";
        String replacedstring = "";

        Matcher m = sine.matcher(input);

        if (m.find()) {
            //while (m.find()) {
            int i = 0;
            //firsthalfstr = input.substring(0, m.start());

            if (input.charAt(m.end()) != '(') {
                System.out.println("Error in Input string.  sin must be followed by (");
            } else {
                i = m.end() + 1;
                while (input.charAt(i) != ')' && i <= input.length()) {
                    sinestr = sinestr + input.charAt(i);
                    i++;
                }
            }

           // secondhalfstr = input.substring(i + 1, input.length());


            double sinvalue = value(input.substring(m.start(), m.end()), Double.valueOf(sinestr));

            replacedstring = input.substring(0, m.start()) + sinvalue + input.substring(i + 1, input.length());

            input = calcmatching(replacedstring);


        }

        if (replacedstring.isEmpty()) {
            return input;
        } else {
            return replacedstring;
        }
    }

    private String precedenceOrder(String innerstr) {

        boolean operatormatches=true;
        while(operatormatches) {
            if (innerstr.matches(".*\\^.*")) {
                innerstr=precedenceOrder(innerstr, "(\\d+\\.?\\d*)(\\^)(\\d+\\.?\\d*)");

            }
            else if (innerstr.matches(".*/.*")){
                innerstr=precedenceOrder(innerstr, "(\\d+\\.?\\d*)(/)(\\d+\\.?\\d*)");
            }
            else if (innerstr.matches(".*\\*.*")){
                innerstr=precedenceOrder(innerstr, "(\\d+\\.?\\d*)(\\*)(\\d+\\.?\\d*)");
            }
            else if (innerstr.matches(".*\\+.*")){
                innerstr=precedenceOrder(innerstr, "(\\d+\\.?\\d*)(\\+)(\\d+\\.?\\d*)");
            }
            else if (innerstr.matches(".*-.*")){
                innerstr=precedenceOrder(innerstr, "(\\d+\\.?\\d*)(\\-)(\\d+\\.?\\d*)");
            }
            else {
                operatormatches=false;
            }
        }

        return innerstr;


    }

    private String precedenceOrder(String input, String pattern) {


        String firsthalfstr;
        String secondhalfstr;
        String replacedstring = "";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.find()) {
            int i = 0;
            firsthalfstr = input.substring(0, m.start(1));
            secondhalfstr = input.substring(m.end(), input.length());

            double invalue = value(Double.valueOf(m.group(1)), m.group(2).charAt(0), Double.valueOf(m.group(3)));

            replacedstring = firsthalfstr + invalue + secondhalfstr;

        }

        return replacedstring;

    }


}
