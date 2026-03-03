package com.odetto.util;

import java.time.Year;
import java.util.Random;

public class EnrollmentGenerator {
    private static final Random random = new Random();

    public static Long generate() {
        int year = Year.now().getValue();
        int suffix = 10000 + random.nextInt(90000);
        return Long.parseLong(year + "" + suffix);
    }
}