package com.example.utp.Wave2.ex1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Calc {
    private static final Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> bigDecimalOperations = new HashMap<>();

    static {
        bigDecimalOperations.put("+", BigDecimal::add);
        bigDecimalOperations.put("-", BigDecimal::subtract);
        bigDecimalOperations.put("*", BigDecimal::multiply);
        bigDecimalOperations.put("/", (x, y) -> {
            BigDecimal result = x.divide(y, 7, RoundingMode.HALF_UP);
            return result.stripTrailingZeros();
        });
    }

    public Calc() {}

    public String doCalc(String cmd) {
        try {
            String[] parts = cmd.trim().split("\\s+");

            BigDecimal a = new BigDecimal(parts[0]);
            BigDecimal b = new BigDecimal(parts[2]);

            String operator = parts[1];

            return bigDecimalOperations.getOrDefault(operator, null)
                    .apply(a, b)
                    .toString();

        } catch(Exception exc) {
            return "Invalid command to calc";
        }

    }
}

