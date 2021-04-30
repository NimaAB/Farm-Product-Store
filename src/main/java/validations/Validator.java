package validations;

import validations.customExceptions.EmptyFieldException;
import validations.customExceptions.InvalidTextInputException;

import java.util.regex.Pattern;

public class Validator {
    private static final NumberConversion.StringtoInteger strToIntConvertor =
            new NumberConversion.StringtoInteger();
    private static final NumberConversion.StringToDouble stringToDoubleConvertor =
            new NumberConversion.StringToDouble();

    public static Integer isValidID(String ID){
        return strToIntConvertor.fromString(ID);
    }

    public static Double validatePrice(String priceStr) throws  InvalidTextInputException, EmptyFieldException{
        if(priceStr == null || priceStr.isEmpty() || priceStr.isBlank()){
            throw new EmptyFieldException("Feil: Pris kan ikke være tom!");
        }

        Double price = stringToDoubleConvertor.fromString(priceStr);

        if(price<=0){
            throw new InvalidTextInputException("Feil: Pris må være større enn null!");
        }

        return price;
    }

    public static String validateName(String name) throws InvalidTextInputException, EmptyFieldException{
        if (name == null || name.isEmpty() || name.isBlank()){
            throw new EmptyFieldException("Feil: Ingen navn gitt!");
        }

        if (Character.isDigit(name.charAt(0))) {
            throw new InvalidTextInputException("Feil: Produkt navn, Den må begynne med et stor bokstav" +
                    " og kan slutte med to siffer tall. Navnet må bestå av et ord uten mellomrom!");
        }
        return name;
    }

    public static String validateCategory(String category) throws InvalidTextInputException, EmptyFieldException{
        if (category == null || category.isEmpty() || category.isBlank()) {
            throw new EmptyFieldException("Feil: Velg begge kategoriene!");
        }


        boolean newCategoryOK = Pattern.matches("^[A-ZÅØÆ][a-zåøæ]*", category);
        if (!newCategoryOK){
            throw new InvalidTextInputException("Feil: Kategori må begynne med et stort bokstav" +
                    " og fortsette med små boktaver, tall er ugyldig!");
        }
        return category;
    }

    public static String validateSpecs(String specs) throws EmptyFieldException{
        if(specs == null || specs.isEmpty() || specs.isBlank()){
            throw new EmptyFieldException("Feil: Spesifikasjon teksten kan ikke være tom!");
        }
        return specs;
    }

}
