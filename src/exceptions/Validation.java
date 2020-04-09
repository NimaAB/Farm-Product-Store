package exceptions;

import dataModels.dataFormats.ItemsFormat;
import exceptions.dataExceptions.InvalidTextInputException;

import java.util.regex.Pattern;

public class Validation {
    public static void textInputValidation (String artikkelNavn, String spesifikasjoner)throws InvalidTextInputException {
        boolean isFalseArtikkel = artikkelNavn.contains(ItemsFormat.DELIMITER)
                || !Pattern.matches("[A-ZÆØÅ]*.",artikkelNavn);
        boolean isFalseSpesifikasjoner = spesifikasjoner.contains(ItemsFormat.DELIMITER)
                || !Pattern.matches("[A-ZÆØÅ]*.",spesifikasjoner);

        if(isFalseArtikkel || isFalseSpesifikasjoner){
            throw new InvalidTextInputException("Teksten din må starte med stor bokstav og ikke inneholde " +
                    ItemsFormat.DELIMITER);
        }
    }
}
