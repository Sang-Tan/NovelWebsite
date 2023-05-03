package core;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
    /**
     * Remove accent from a string
     * @param str string to remove accent
     * @return string without accent
     */
    public static String removeAccent(String str)
    {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**
     * Remove all space in a string
     * @param str string to replace space to dash
     * @return
     */
    public static String spaceToDash(String str)
    {
        return str.replaceAll("\\s+", "-");
    }
    /**
     * Truncate a string to a maximum length
     * @param text string to truncate
     * @param maxLength maximum length of the string
     * @return truncated string have maximum length while keeping the last word before the maximum length
     */
    public static String truncate(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        int lastSpaceIndex = text.substring(0, maxLength).lastIndexOf(' ');
        if (lastSpaceIndex == -1) {
            return text.substring(0, maxLength) + "...";
        }
        return text.substring(0, lastSpaceIndex) + "...";
    }

    /**
     *
     * @param string
     * @return get first interger from string, -1 if no interger is found
     */
    public static int extractFirstInt(String string)
    {
        int numStartIndex = -1;
        int numEndIndex = -1;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                if (numStartIndex == -1) {
                    numStartIndex = i;
                }
                numEndIndex = i;
            } else if (numStartIndex != -1) {
                break;
            }
        }
        try {
            int id = Integer.parseInt(string.substring(numStartIndex, numEndIndex + 1));
            return id;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
    public static String removeSpecialCharacter(String str)
    {
        String specialCharsRegex = "[^a-zA-Z0-9\s]";
        return str.replaceAll(specialCharsRegex, "");
    }
    public static String removeDuplicateDash(String str)
    {
        return str.replaceAll("^-+$", "-").replaceAll("^-", "").replaceAll("-$", "");
    }

    /**
     * Extract a list of interger from a string of interger separated by comma
     * @param genresIDString string to extract interger
     * @return list of interger
     */

    public static HashSet<Integer> extractInt(String genresIDString)  {

        HashSet<Integer> genresIDList = null;
        String regex = "^[0-9,]+$";
        if (!(genresIDString == null ) && !genresIDString.isEmpty() && genresIDString.matches(regex) ) {
            String[] arrGenresIDString = genresIDString.split(",");
            // convert string array to hashset
            genresIDList = Arrays.stream(arrGenresIDString).map(Integer::parseInt).collect(Collectors.toCollection(HashSet::new));
        }
        return genresIDList;
    }
}
