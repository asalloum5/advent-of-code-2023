package adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DayTwo {
    public static int executePartOne() throws IOException {
        int sum = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("dayTwoInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] game = line.split(":");
                    int gameNumber = Integer.parseInt(game[0].substring(4).strip());
                    boolean isGamePossible = true;

                    String[] revealedSet = game[1].split(";");
                    for (String s : revealedSet) {
                        String[] cubes = s.split(",");
                        for (String cube : cubes) {
                            String[] parts = cube.strip().split("\\s+");
                            int countBlue = 0, countRed = 0, countGreen = 0;
                            switch (parts[1]) {
                                case "blue" -> countBlue += Integer.parseInt(parts[0]);
                                case "red" -> countRed += Integer.parseInt(parts[0]);
                                case "green" -> countGreen += Integer.parseInt(parts[0]);
                            }
                            if (countBlue > 14 || countGreen > 13 || countRed > 12) {
                                isGamePossible = false;
                            }
                        }
                    }
                    if (isGamePossible) sum += gameNumber;
                }
            }
        }
        return sum;
    }

    public static int executePartTwo() throws IOException {
        int sum = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("dayTwoInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] game = line.split(":");
                    String[] revealedSet = game[1].split(";");
                    int countBlue = 0, countRed = 0, countGreen = 0;
                    for (String s : revealedSet) {
                        String[] cubes = s.split(",");
                        for (String cube : cubes) {
                            String[] parts = cube.strip().split("\\s+");
                            switch (parts[1]) {
                                case "blue" -> countBlue = Math.max(Integer.parseInt(parts[0]), countBlue);
                                case "red" -> countRed = Math.max(Integer.parseInt(parts[0]), countRed);
                                case "green" -> countGreen = Math.max(Integer.parseInt(parts[0]), countGreen);
                            }
                        }
                    }
                    int pow = countGreen * countBlue * countRed;
                    sum += pow;
                }
            }
        }
        return sum;
    }
}
