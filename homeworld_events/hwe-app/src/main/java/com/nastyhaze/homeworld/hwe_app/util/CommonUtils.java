package com.nastyhaze.homeworld.hwe_app.util;

import java.util.Objects;

/**
 *  Static Utility class for common application operations.
 */
public class CommonUtils {

    /**
     * Utility method for String validation.
     * Returns true: String contains a null value or only whitespace.
     * Returns false: String contains valid data.
     * @param str
     * @return boolean
     */
    public static boolean isNullOrBlankString(String str) {
        return Objects.isNull(str) || str.isBlank();
    }
}
