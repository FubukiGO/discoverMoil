package com.yk.commons.constant;

import java.util.regex.Pattern;

public interface PatternConstant {
    Pattern IP = Pattern.compile("\\d{2,3}\\.(\\d{1,3}\\.?){3}");

    Pattern ROUTE = Pattern.compile("\\d{3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\/\\d{1,2}");

    Pattern MASK = Pattern.compile("(\\d{1,3}(\\.)){1,3}(255(\\.)?){1,3}");
}
