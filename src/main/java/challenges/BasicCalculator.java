package challenges;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Have the function Calculator(str) take the str parameter being passed and evaluate
 * the mathematical expression within in. For example, if str were "2+(3-1)*3" the output should be 8.
 * Another example: if str were "(2-0)(6/2)" the output should be 6.
 * There can be parenthesis within the string, so you must evaluate it properly according to the rules of arithmetic.
 * The string will contain the operators: +, -, /, *, (, and ).
 * If you have a string like this: #/#*# or #+#(#)/#, then evaluate from left to right.
 * So divide then multiply, and for the second one multiply, divide, then add.
 * The evaluations will be such that there will not be any decimal operations,
 * so you do not need to account for rounding and whatnot.
 */
public class BasicCalculator {

    public double calculate(String exp) {

        exp = exp.replaceAll("([0-9])\\(", "$1*(");
        exp = exp.replaceAll("\\)\\(", ")*(");

        List<String> symbols = new ArrayList<>();

        for (char c : exp.toCharArray()) {
            symbols.add(String.valueOf(c));
        }

        StringBuilder sumExp = new StringBuilder();

        StringBuilder prodExp = new StringBuilder();

        while (symbols.size() > 0) {
            String symbol = symbols.remove(0);

            if (isDigit(symbol) || isProd(symbol)) {
                prodExp.append(symbol);
            } else if (groupStarts(symbol)) {

                int opens = 1;

                StringBuilder subExp = new StringBuilder();

                while (opens > 0) {

                    if (symbols.isEmpty()) {
                        throw new IllegalStateException(
                                String.format("Sub-expression is not closed in '%s'!", exp));
                    }

                    symbol = symbols.remove(0);
                    subExp.append(symbol);

                    if (groupStarts(symbol)) {
                        opens ++;
                    } else if (groupEnds(symbol)) {
                        opens --;

                        if (opens == 0) {
                            subExp.deleteCharAt(subExp.length() - 1);
                        }
                    }
                }

                double result = calculate(subExp.toString());

                prodExp.append(result);
            } else if(isSum(symbol)) {
                sumExp.append(calcProd(prodExp.toString())).append(symbol);
                prodExp = new StringBuilder();
            } else {
                throw new IllegalStateException(String.format("Unknown character '%s'!", symbol));
            }
        }

        if (prodExp.length() > 0) {
            sumExp.append(calcProd(prodExp.toString()));
        }

        return calcSum(sumExp.toString());
    }

    private double calcProd(String exp) {
        return calcValue(exp, new CalcAdapter() {
            @Override
            public String getSymbol(boolean inc) {
                return inc ? "*" : "/";
            }

            @Override
            public double calc(double a, double b, boolean inc) {
                return inc ? a * b : a / b;
            }
        });
    }

    private double calcSum(String exp) {
        return calcValue(exp, new CalcAdapter() {
            @Override
            public String getSymbol(boolean inc) {
                return inc ? "+" : "-";
            }

            @Override
            public double calc(double a, double b, boolean inc) {
                return inc ? a + b : a - b;
            }
        });
    }

    private double calcValue(String exp, CalcAdapter adapter) {
        if (isNumber(exp)) {
            return Double.parseDouble(exp);
        }

        boolean inc = true;

        int incIndex = exp.lastIndexOf(adapter.getSymbol(true));
        int decIndex = exp.lastIndexOf(adapter.getSymbol(false));

        int opIndex = incIndex;

        if (decIndex > -1 && (opIndex == -1 || decIndex > opIndex)) {
            opIndex = decIndex;
            inc = false;
        }

        double lastNumber = Double.parseDouble(exp.substring(opIndex + 1));
        String nextExp = exp.substring(0, opIndex);

        return adapter.calc(calcValue(nextExp, adapter), lastNumber, inc);
    }

    private boolean isNumber(String exp) {
        return Pattern.matches("^[0-9.]+$", exp);
    }

    private boolean isDigit(String symbol) {
        return Pattern.matches("^[0-9]$", String.valueOf(symbol));
    }

    private boolean isProd(String symbol) {
        return List.of("/", "*").contains(String.valueOf(symbol));
    }

    private boolean isSum(String symbol) {
        return List.of("+", "-").contains(String.valueOf(symbol));
    }

    private boolean groupStarts(String symbol) {
        return symbol.equals("(");
    }

    private boolean groupEnds(String symbol) {
        return symbol.equals(")");
    }

    private interface CalcAdapter {
        String getSymbol(boolean inc);
        double calc(double a, double b, boolean inc);
    }
}
