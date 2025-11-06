package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        Month(int days) {
            this.days = days;
        }

        public static Month fromString(String value) {
            Objects.requireNonNull(value);
            Month result = null;

            try {
                result = valueOf(value);
                return result;
            } catch (IllegalArgumentException e) {
                result = null;
                for(Month elem : Month.values()) {
                    if(elem.toString().toLowerCase().contains(value.toLowerCase())) {
                        if(result != null) {
                            throw new IllegalArgumentException("Ambiguos result for the string in input ( "+value+" )");
                        }
                        result = elem;
                    }
                }
            
                if(result == null) {
                    throw new IllegalArgumentException("The string in input ( "+value+" ) was not a Month");
                }

            return result;
            }
            
        }
    }

    private class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(String arg0, String arg1) {
            return Month.fromString(arg0).compareTo(Month.fromString(arg1));
        }
    }

    private class SortByDate implements Comparator<String> {
        @Override
        public int compare(String arg0, String arg1) {
            return Integer.compare(Month.fromString(arg0).days,
                                   Month.fromString(arg1).days );
        }
        
    }
}
