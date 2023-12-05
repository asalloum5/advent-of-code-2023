package adventofcode;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            int dayOneResult = DayOne.execute();
            assert 55701 == dayOneResult;

            int dayTwoPartOneResult = DayTwo.executePartOne();
            assert 2204 == dayTwoPartOneResult;

            int dayTwoPartTwoResult = DayTwo.executePartTwo();
            assert 71036 == dayTwoPartTwoResult;
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

}
