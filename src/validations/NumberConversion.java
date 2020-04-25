package validations;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class NumberConversion {
    public static class StringtoInteger extends IntegerStringConverter {
        @Override
        public Integer fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException ignored) {
                Alerts.warning("Ugyldig verdi: " + value);
            }
            return null;
        }
    }

    public static class StringToDouble extends DoubleStringConverter {
        @Override
        public Double fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException ignored) {
                Alerts.warning("Ugyldig verdi: " + value);
            }
            return null;
        }
    }
}
