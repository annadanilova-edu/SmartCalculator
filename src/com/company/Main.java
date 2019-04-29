package com.company;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Main {
    static Map variables = new HashMap<String, Integer>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();

        while (!input.contains("/exit")) {

            if (input.isEmpty()){
                input = scan.nextLine();
                continue;
            }
            if (input.equals("/help")){
                System.out.println("The program calculates the sum and substraction of numbers");
                input = scan.nextLine();
                continue;
            }
            if (input.startsWith("/")) {
                System.out.println("Unknown command");
                input = scan.nextLine();
                continue;
            }
            if (input.contains("=")){
                writeToMap(input);
            }
            else {
                if (input.contains(" ") && (input.contains("-") || input.contains("+"))) {
                    System.out.println(calculateNumbers(input));
                }
                else {
                    if (input.startsWith("+")) {
                        input = input.substring(1);
                    }
                    if (input.matches("-?\\d+")) {
                        System.out.println(input);
                    }
                    else
                        if(variables.containsKey(input)){
                            System.out.println(variables.get(input));
                        }
                        else
                        if (input.matches("[a-zA-Z]+")){
                            System.out.println("Unknown variable");
                        }
                        else System.out.println("Invalid expression");
                }
            }

            input = scan.nextLine();
        }
        System.out.println("Bye!");
    }

    static int calculateNumbers(String input){
        String[] numbers = input.split("\\s");
        int sum = 0;

        for (int i = 0; i < numbers.length; ++i) {
            if (!numbers[i].matches("-?\\d+")){
                if (numbers[i].contains("-") && countMinuses(numbers[i]) > 2) {
                    numbers[i] = "-";
                }
                if (numbers[i].equals("-")) {
                    if (numbers[i + 1].startsWith("-")) {
                        numbers[i + 1] = numbers[i + 1].replace("-","");
                    }
                    else {
                        if (numbers[i + 1].matches("\\d+")) {
                            numbers[i + 1] = "-" + numbers[i + 1];
                        }
                        else if (variables.containsKey(numbers[i + 1])){
                            String value = variables.get(numbers[i + 1]).toString();
                            sum -= Integer.parseInt(value);
                            i++;
                            continue;
                        }
                    }
                }
                if (variables.containsKey(numbers[i])){
                    sum += Integer.parseInt(variables.get(numbers[i]).toString());
                }
            }
            else sum += Integer.parseInt(numbers[i]);
        }
        return(sum);
    }

    static int countMinuses(String str){
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){

            lastIndex = str.indexOf("-",lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex ++;
            }
        }
        return count;
    }

    static void writeToMap(String input){
        input = input.replaceAll(" ", "");
        int eqSign = input.indexOf("=");
        String key = input.substring(0 , eqSign);
        String value = input.substring(eqSign + 1, input.length());

        if (value.isEmpty()){
            System.out.println("Invalid expression");
            return;
        }

        if (!key.matches("[a-zA-Z]+")){
            System.out.println("Invalid identifier");
            return;
        }

        if (!value.matches("-?\\d+")){
            if (variables.containsKey(value)){
                String newKey = value;
                String newValue = variables.get(newKey).toString();
                if (variables.containsKey(newKey)){
                    variables.replace(key, newValue);
                }
                else variables.put(key, newValue);

                //System.out.println(Arrays.asList(variables));
            }
            else {
                if (value.matches("-?.*\\d.*")) {
                    System.out.println("Invalid assignment");
                } else
                    if (value.matches("[a-zA-Z]+"))
                        System.out.println("Unknown variable");
                    else System.out.println("Invalid expression");
            }

            return;
        }

        if (variables.containsKey(key)){
            variables.replace(key, value);
        }
        else variables.put(key, value);
    }
}

