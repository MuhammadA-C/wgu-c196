package com.muhammadchambersc196.helper;

import java.time.LocalDate;

public class DateValidation {


    public static boolean isDateFormattedCorrect(String dateStr) {
        if (!isDateCorrectLength(dateStr)) {
            return false;
        } else if (!doesDateContainDashes(dateStr)) {
            return false;
        } else if (!isDateMonthCorrectRange(dateStr)) {
            return false;
        } else if (!isDateDayCorrectRange(dateStr)) {
            return false;
        }
        return true;
    }

    private static boolean isDateCorrectLength(String dateStr) {
        final String dateFormat = "yyyy-mm-dd";

        if(dateStr.length() > dateFormat.length() || dateStr.length() < dateFormat.length()) {
            return false;
        }
        return true;
    }

    private static boolean isDateMonthCorrectRange(String dateStr) {
        final int MONTH_JANUARY_INT_VALUE = 01;
        final int MONTH_DECEMBER_INT_VALUE = 12;
        int dateMonth = Integer.valueOf(dateStr.substring(5,7));

        if(dateMonth >= MONTH_JANUARY_INT_VALUE && dateMonth <= MONTH_DECEMBER_INT_VALUE) {
            return true;
        }
        return false;
    }

    private static boolean isDateDayCorrectRange(String dateStr) {
        final int MINIMUM_DAY_INT_VALUE = 01;
        final int MAXIMUM_DAY_INT_VALUE = 31;
        int dateDay = Integer.valueOf(dateStr.substring(8,10));

        if(dateDay >= MINIMUM_DAY_INT_VALUE && dateDay <= MAXIMUM_DAY_INT_VALUE) {
            return true;
        }
        return false;
    }

    private static boolean doesDateContainDashes(String dateStr) {
        final String dash = "-";
        String dateDashOne = String.valueOf(dateStr.toCharArray()[4]);
        String dateDashTwo = String.valueOf(dateStr.toCharArray()[7]);
        //Note: Date format yyyy-mm-dd has two dashes in it located at index 0 and index 7

        if(dateDashOne.equals(dash) && dateDashTwo.equals(dash)) {
            return true;
        }
        return false;
    }
}
