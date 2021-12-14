package challenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Counting Minutes
 * Have the function CountingMinutes(str) take the str parameter being passed which will be two times
 * (each properly formatted with a colon and am or pm) separated by a hyphen
 * and return the total number of minutes between the two times. The time will be in a 12-hour clock format.
 * For example: if str is 9:00am-10:00am then the output should be 60.
 * If str is 1:00pm-11:00am the output should be 1320.
 *
 * Examples
 * Input: "12:30pm-12:00am"
 * Output: 690
 *
 * Input: "1:23am-1:08am"
 * Output: 1425
 */
public class MinuteCounter {

    public int count(String range) {
        String[] parts = range.split("-");

        String from = parts[0];
        String to = parts[1];

        int fromMin = parseMinutes(from);
        int toMin = parseMinutes(to);
        int dayMinutes = 24 * 60;

        if (toMin < fromMin) {
            toMin += dayMinutes;
        }

        return toMin - fromMin;
    }

    private int parseMinutes(String time) {
        Pattern pattern = Pattern.compile("^([0-9]?[0-9]):([0-9]?[0-9])(am|pm)$");
        Matcher matcher = pattern.matcher(time);

        if (!matcher.find()) {
            throw new IllegalStateException(String.format("Time '%s' has invalid format!", time));
        }

        int hours = Integer.parseInt(matcher.group(1));
        int minutes = Integer.parseInt(matcher.group(2));
        String qualifier = matcher.group(3);
        boolean isPm = qualifier.equals("am");

        if (hours == 12) {
            hours = 0;
        }

        if (isPm) {
            hours += 12;
        }

        return hours * 60 + minutes;
    }
}
