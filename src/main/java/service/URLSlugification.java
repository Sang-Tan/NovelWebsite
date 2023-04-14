package service;

import static core.StringUtils.removeAccent;
import static core.StringUtils.spaceToDash;

public class URLSlugification {
    /**
     * Convert a string to URL slug
     * @param str string to convert
     * @return URL slug
     */
    public static String sluging(String str)
    {
        return spaceToDash(removeAccent(str).toLowerCase());
    }
    /**
     *
     * @param pathComponent path component of the URI exp : 1-ten-truyen
     * @return id of the path component, -1 if no id is found
     */
    public static int extractId(String pathComponent)
    {
        int numStartIndex = -1;
        int numEndIndex = -1;
        for (int i = 0; i < pathComponent.length(); i++) {
            if (Character.isDigit(pathComponent.charAt(i))) {
                if (numStartIndex == -1) {
                    numStartIndex = i;
                }
                numEndIndex = i;
            } else if (numStartIndex != -1) {
                break;
            }
        }
        try {
            int id = Integer.parseInt(pathComponent.substring(numStartIndex, numEndIndex + 1));
            return id;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
}
