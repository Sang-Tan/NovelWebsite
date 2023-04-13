package core;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringCoverter {
    public static String removeAccent(String str)
    {
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
