package ru.ITMO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        final String ERROR_MES  = "Input string is not a valid expression.";
        String inputString = "w33[W]e2[Xx1[Yy2[Zz]]]3[End]20[w]";

        if(isValid(inputString)) {
            System.out.println("Input string:  " + inputString +
                    "\nOutput string: " + unpackString(inputString));
        }
        else System.out.println(ERROR_MES);
    }


    private static boolean isValid(String inputString) {
        if (!inputString.matches("[^\\[][a-zA-Z0-9\\[\\]]+[$[^\\d]]")) return false; //первый ![, последний не цифра
        boolean prevCharIsDigit;
        char currChar;
        int balanceBracket = 0;
        for (int i=1; i<inputString.length();i++){                          //первый символ уже проверен на ![
            prevCharIsDigit=Character.isDigit(inputString.charAt(i-1));
            currChar = inputString.charAt(i);
            if (balanceBracket<0) return false;
            if ((prevCharIsDigit)&&!((currChar=='[')|Character.isDigit(currChar))) return false; //после цифры [ или цифра
            if (currChar=='['){
                if (!prevCharIsDigit) return false;                        //перед [ должна быть цифра
                balanceBracket++;
            }
            if (currChar==']') balanceBracket--;
        }
        return balanceBracket==0;
    }


    private static String unpackString(String inputString){
        String regex = "\\d+\\[[a-zA-Z]+]";
        String outputString = inputString;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            outputString = matcher.replaceFirst(unpackSubString(matcher.group(0)));
            matcher = pattern.matcher(outputString);
        }
        return outputString;
    }

    
    private static String unpackSubString(String expression) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(expression);
        matcher.find();
        int numberOfRepeat = Integer.parseInt(matcher.group());
        pattern = Pattern.compile("\\[.+]");
        matcher = pattern.matcher(expression);
        matcher.find();
        String result = expression.substring(matcher.start()+1, matcher.end()-1);
        return result.repeat(numberOfRepeat);
    }
}
