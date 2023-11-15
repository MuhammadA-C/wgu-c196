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

        /*
            Need to add a check to verify if:
            - the year is a number
            - the month is a number
            - the day is a number
         */
    }

    private static boolean isDateCorrectLength(String dateStr) {
        final String dateFormat = "yyyy-mm-dd";

        if (dateStr.length() > dateFormat.length() || dateStr.length() < dateFormat.length()) {
            return false;
        }
        return true;
    }

    private static boolean isDateMonthCorrectRange(String dateStr) {
        final int MONTH_JANUARY_INT_VALUE = 01;
        final int MONTH_DECEMBER_INT_VALUE = 12;
        int dateMonth = Integer.valueOf(dateStr.substring(5, 7));

        if (dateMonth >= MONTH_JANUARY_INT_VALUE && dateMonth <= MONTH_DECEMBER_INT_VALUE) {
            return true;
        }
        return false;
    }

    private static boolean isDateDayCorrectRange(String dateStr) {
        final int MINIMUM_DAY_INT_VALUE = 01;
        final int MAXIMUM_DAY_INT_VALUE = 31;
        int dateDay = Integer.valueOf(dateStr.substring(8, 10));

        if (dateDay >= MINIMUM_DAY_INT_VALUE && dateDay <= MAXIMUM_DAY_INT_VALUE) {
            return true;
        }
        return false;
    }

    private static boolean doesDateContainDashes(String dateStr) {
        final String dash = "-";
        String dateDashOne = String.valueOf(dateStr.toCharArray()[4]);
        String dateDashTwo = String.valueOf(dateStr.toCharArray()[7]);
        //Note: Date format yyyy-mm-dd has two dashes in it located at index 0 and index 7

        if (dateDashOne.equals(dash) && dateDashTwo.equals(dash)) {
            return true;
        }
        return false;
    }

    public static boolean isStartDateBeforeEndDate(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        if (startDate.isBefore(endDate)) {
            return true;
        }
        return false;
    }

    public static boolean isStartDateTheSameOrBeforeEndDate(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        if (startDate.isEqual(endDate)) {
            return true;
        } else if (startDate.isBefore(endDate)) {
            return true;
        }
        return false;
    }

    //Need to add this to all classes having a date
    private static boolean isNumber(String subStr) {
        char[] charArray = subStr.toCharArray();

        try {
            //Loops through the substring converting each element in the string to an integer
            for (char element : charArray) {
                int parsedNum = Integer.parseInt(String.valueOf(element));
            }
            /*
            for (int i = 0; i < subStr.length(); i++) {
                int parsedNum = Integer.parseInt(String.valueOf(subStr.charAt(i)));
            }

             */
        } catch (NumberFormatException e) {
            //False is returned if an error is thrown when trying to convert the character in the substring to an integer
            return false;
        }
        return true;
    }

    public static boolean isDateANumber(String dateStr) {
        String dateYear = dateStr.substring(0, 4);
        String dateMonth = dateStr.substring(5, 7);
        String dateDay = dateStr.substring(8, 10);

        if (!isNumber(dateYear)) {
            return false;
        } else if (!isNumber(dateMonth)) {
            return false;
        } else if (!isNumber(dateDay)) {
            return false;
        }
        return true;
    }
}
