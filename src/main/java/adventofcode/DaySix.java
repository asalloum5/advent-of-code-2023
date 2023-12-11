package adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DaySix {

    public static long executePartOne() throws IOException, IllegalArgumentException {
        long response = 1;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("daySixInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                String[] times = new String[0], distances = new String[0];
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(":");
                    if (values[0].equals("Time")) {
                        times = values[1].strip().replaceAll("\\s+", " ").split(" ");
                    } else if (values[0].equals("Distance")) {
                        distances = values[1].strip().replaceAll("\\s+", " ").split(" ");
                    }
                }
                if (times.length != distances.length) {
                    throw new IllegalArgumentException("Bad Request: Input not correctly formatted.");
                }
                for (int i = 0; i < times.length; ++i) {
                    response *= getNumberOfWaysToBeatRecord(Long.parseLong(times[i]), Long.parseLong(distances[i]));
                }
            }
        }
        return response;
    }

    public static long executePartTwo() throws IOException {
        long response = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("daySixInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                String time = "", distance = "";
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(":");
                    if (values[0].equals("Time")) {
                        time = values[1].strip().replaceAll("\\s+", "");
                    } else if (values[0].equals("Distance")) {
                        distance = values[1].strip().replaceAll("\\s+", "");
                    }
                }
                response = getNumberOfWaysToBeatRecord(Long.parseLong(time), Long.parseLong(distance));
            }
        }
        return response;
    }

    private static long getNumberOfWaysToBeatRecord(long raceTime, long distanceToBeat) {
        long nbWays = 0;
        for (long speed = 0; speed < raceTime; ++speed) {
            if (speed >= distanceToBeat) break;
            long timeLeft = raceTime - speed;
            long maxDistanceToReach = timeLeft * speed;
            if (maxDistanceToReach > distanceToBeat) ++nbWays;
        }
        return nbWays;
    }

}
