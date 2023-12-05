package adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DayOne {

    private static final String[] wordsToCheck = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
    public static int execute() throws IOException {
        int sum = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("dayOneInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int firstDigit = -1, lastDigit = -1;
                    int i = 0, j = line.length() - 1;
                    while (i <= j && (firstDigit == -1 || lastDigit == -1)) {
                        if (firstDigit == -1 && Character.isDigit(line.charAt(i))) {
                            firstDigit = Character.getNumericValue(line.charAt(i));
                        }
                        if (firstDigit == -1 && findWordDigitAtIndex(line, i) != null) {
                            firstDigit = getDigitFromWord(findWordDigitAtIndex(line, i));
                        }
                        if (lastDigit == -1 && Character.isDigit(line.charAt(j))) {
                            lastDigit = Character.getNumericValue(line.charAt(j));
                        }
                        if (lastDigit == -1 && findWordDigitAtIndexFromEnd(line, j) != null) {
                            lastDigit = getDigitFromWord(findWordDigitAtIndexFromEnd(line, j));
                        }
                        if (firstDigit == -1) {
                            ++i;
                        }
                        if (lastDigit == -1) {
                            --j;
                        }
                    }
                    sum += Integer.parseInt(firstDigit + String.valueOf(lastDigit));
                }
            }
        }
        return sum;
    }


    private static String findWordDigitAtIndex(String input, int startIndex) {
        String substring = input.substring(startIndex);
        for (String word : wordsToCheck) {
            if (substring.startsWith(word)) {
                return word;
            }
        }
        return null;
    }

    private static String findWordDigitAtIndexFromEnd(String input, int endIndex) {
        if (endIndex >= input.length() || endIndex < 0) {
            return null;
        }

        String substring = input.substring(0, endIndex + 1);
        for (String word : wordsToCheck) {
            if (substring.endsWith(word)) {
                return word;
            }
        }
        return null;
    }


    private static int getDigitFromWord(String word) {
        return switch (word) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> -1;
        };
    }
}
