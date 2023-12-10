package adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DayFour {

    public static int executePartOne() throws IOException {
        int response = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("dayFourInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int nbPoints = 0;
                    GameCards gameCards = parseGameCards(line);
                    List<String> winningCards = gameCards.winningCards();
                    for (String playedCard : gameCards.playedCards()) {
                        if (winningCards.contains(playedCard)) {
                            if (nbPoints == 0) nbPoints = 1;
                            else nbPoints *= 2;
                        }
                    }
                    response += nbPoints;
                }
            }
        }
        return response;
    }

    public static int executePartTwo() throws IOException {
        int response = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("dayFourInput.txt");
        if (inputStream != null) {
            int nbLines = Math.toIntExact(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .count());
            List<Integer> nbGames = new ArrayList<>(Collections.nCopies(nbLines, 1));

            inputStream = classloader.getResourceAsStream("dayFourInput.txt");
            if (inputStream != null) {

                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                try (BufferedReader reader = new BufferedReader(streamReader)) {
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        GameCards gameCards = parseGameCards(line);
                        int nbCopies = 0;
                        List<String> winningCards = gameCards.winningCards();
                        for (String playedCard : gameCards.playedCards()) {
                            if (winningCards.contains(playedCard)) {
                                nbCopies++;
                            }
                        }
                        for (int i = lineNumber + 1, j = 0; i < nbGames.size() && j < nbCopies; ++i, ++j) {
                            nbGames.set(i, nbGames.get(i) + nbGames.get(lineNumber));
                        }
                        lineNumber++;
                    }
                }
            }
            for (Integer nbGame : nbGames) {
                response += nbGame;
            }
        }
        return response;
    }

    record GameCards(List<String> winningCards, String[] playedCards) {
    }

    private static GameCards parseGameCards(String line) {
        String[] cards = line.split(":");
        String[] cardGames = cards[1].split("\\|");
        List<String> winningCards = List.of(cardGames[0].strip().replaceAll("\\s+", " ").split(" "));
        String[] playedCards = cardGames[1].strip().replaceAll("\\s+", " ").split(" ");

        return new GameCards(winningCards, playedCards);
    }

}
