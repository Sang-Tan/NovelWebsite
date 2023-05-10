package service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class SearchNovelService {

    /**
     * Extract a list of interger from a string of interger separated by comma
     * @param genresIDString string to extract interger
     * @return list of interger
     */

    public static HashSet<Integer> extractGenresId(String genresIDString)  {

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
