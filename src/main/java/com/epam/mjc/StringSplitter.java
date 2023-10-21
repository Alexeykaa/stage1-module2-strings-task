package com.epam.mjc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StringSplitter {

    private static final String REGEXP_DELIMITER_PATTERN = "([%s])+";

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source     source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        String delimiter = String.format(REGEXP_DELIMITER_PATTERN, String.join("", delimiters));
        String[] result = source.split(delimiter);
        return Arrays.stream(result).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }
}
