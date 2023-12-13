package adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DayEight {

    public static int executePartOne() throws IOException {
        int response = 0;
        Input input = parseInput();
        String current = "AAA";

        int i = 0;
        while (i < input.sequence.length()) {
            current = input.combinations.get(current).get(input.sequence.charAt(i) == 'R' ? 1 : 0);
            if (current.equals("ZZZ")) return ++response;
            ++response;
            if (i == input.sequence.length() - 1) i = 0;
            else ++i;
        }
        return response;
    }

    private static Input parseInput() throws IOException {
        String sequence = "";
        Map<String, List<String>> combinations = new HashMap<>();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("dayEightInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if (lineNumber == 0) {
                    sequence = line;
                    ++lineNumber;
                    continue;
                }
                if (lineNumber == 1) {
                    ++lineNumber;
                    continue;
                }
                String[] comb = line.split("=");
                String[] values = comb[1].strip().replace("(", "").replace(")", "").split(",");
                combinations.put(comb[0].strip(), Arrays.asList(values[0].strip(), values[1].strip()));
                ++lineNumber;
            }
        }
        return new Input(sequence, combinations);
    }

    private record Input(String sequence, Map<String, List<String>> combinations) {
    }

}
