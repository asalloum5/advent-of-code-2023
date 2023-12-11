package adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DaySeven {

    private final List<String> fiveOfAKind = new ArrayList<>();
    private final List<String> fourOfAKind = new ArrayList<>();
    private final List<String> fullHouse = new ArrayList<>();
    private final List<String> threeOfAKind = new ArrayList<>();
    private final List<String> twoPair = new ArrayList<>();
    private final List<String> onePair = new ArrayList<>();
    private final List<String> highCard = new ArrayList<>();

    public int executePartOne() throws IOException {
        int response = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("daySevenInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                Map<String, Integer> bidByHand = new HashMap<>();

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(" ");
                    bidByHand.put(values[0], Integer.parseInt(values[1]));
                    addToKind(values[0]);
                }
                sortLists(DaySeven::compareCards);
                response = computeBidBasedOnRank(response, bidByHand);
            }
        }
        return response;
    }

    public int executePartTwo() throws IOException {
        int response = 0;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("daySevenInput.txt");
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                Map<String, Integer> bidByHand = new HashMap<>();

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(" ");
                    bidByHand.put(values[0], Integer.parseInt(values[1]));
                    addToKindConsideringJoker(values[0]);
                }
                sortLists(DaySeven::compareCardsWithJoker);
                response = computeBidBasedOnRank(response, bidByHand);
            }
        }
        return response;
    }

    private int computeBidBasedOnRank(int response, Map<String, Integer> bidByHand) {
        AtomicInteger rank = new AtomicInteger(1);
        response += computeBid(highCard, bidByHand, rank);
        response += computeBid(onePair, bidByHand, rank);
        response += computeBid(twoPair, bidByHand, rank);
        response += computeBid(threeOfAKind, bidByHand, rank);
        response += computeBid(fullHouse, bidByHand, rank);
        response += computeBid(fourOfAKind, bidByHand, rank);
        response += computeBid(fiveOfAKind, bidByHand, rank);
        return response;
    }

    private void sortLists(Comparator<? super String> compareCardsMethod) {
        fiveOfAKind.sort(compareCardsMethod);
        fourOfAKind.sort(compareCardsMethod);
        fullHouse.sort(compareCardsMethod);
        threeOfAKind.sort(compareCardsMethod);
        twoPair.sort(compareCardsMethod);
        onePair.sort(compareCardsMethod);
        highCard.sort(compareCardsMethod);
    }

    private void addToKind(String input) {
        Map<Character, Integer> labelCounts = countCharacters(input);
        addToKind(labelCounts, input);
    }

    private void addToKindConsideringJoker(String input) {
        Map<Character, Integer> labelCounts = countCharacters(input);
        if (labelCounts.containsKey('J')) {
            int maxValue = Integer.MIN_VALUE;
            Character key = null;
            for (Map.Entry<Character, Integer> entry : labelCounts.entrySet()) {
                if(entry.getKey() == 'J') continue;
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    key = entry.getKey();
                }
            }
            if (key != null) {
                labelCounts.put(key, labelCounts.get(key) + labelCounts.get('J'));
                labelCounts.remove('J');
            }
        }
        addToKind(labelCounts, input);
    }

    private void addToKind(Map<Character, Integer> labelCounts, String input) {
        if (labelCounts.containsValue(5)) {
            fiveOfAKind.add(input);
        } else if (labelCounts.containsValue(4) && labelCounts.size() == 2) {
            fourOfAKind.add(input);
        } else if (labelCounts.containsValue(3) && labelCounts.containsValue(2)) {
            fullHouse.add(input);
        } else if (labelCounts.containsValue(3) && labelCounts.size() == 3) {
            threeOfAKind.add(input);
        } else if (labelCounts.containsValue(2) && labelCounts.size() == 3) {
            twoPair.add(input);
        } else if (labelCounts.containsValue(2) && labelCounts.size() == 4) {
            onePair.add(input);
        } else {
            highCard.add(input);
        }
    }

    private Map<Character, Integer> countCharacters(String input) {
        Map<Character, Integer> labelCounts = new HashMap<>();
        for (char c : input.toCharArray()) {
            labelCounts.put(c, labelCounts.getOrDefault(c, 0) + 1);
        }
        return labelCounts;
    }

    private static int compareCards(String lhs, String rhs) {
        return compareCards(lhs, rhs, "AKQJT98765432");
    }

    private static int compareCardsWithJoker(String lhs, String rhs) {
        return compareCards(lhs, rhs, "AKQT98765432J");
    }

    private static int compareCards(String lhs, String rhs, String order) {
        if (lhs.length() != rhs.length()) return 0;
        if (lhs.equals(rhs)) return 0;
        for (int i = 0; i < lhs.length(); ++i) {
            int lhsIndexOf = order.indexOf(lhs.charAt(i));
            int rhsIndexOf = order.indexOf(rhs.charAt(i));
            if(lhsIndexOf < rhsIndexOf) return 1;
            if(lhsIndexOf > rhsIndexOf) return -1;
        }
        return 0;
    }

    private int computeBid(List<String> cards, Map<String, Integer> bidByHand, AtomicInteger rank) {
        int response = 0;
        for (String card : cards) {
            response += (bidByHand.get(card) * rank.get());
            rank.incrementAndGet();
        }
        return response;
    }

}
