package org.app.validation;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/** Brukes til å konvertere en verdi til enten Integer eller Double
 *  Den har vi brukt til å konvertere og validere nr og pris til komponentene
 *  når de blir oppdatert direkte i tableView-en */

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
