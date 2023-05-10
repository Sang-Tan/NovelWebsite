package core.string_process;

public class HTMLParser {
    public static String wrapEachLineWithTag(String text, String tag) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            String thisLine = line.trim();
            result.append(String.format("<%s>%s</%s>", tag, thisLine, tag));
        }
        return result.toString();
    }
}
