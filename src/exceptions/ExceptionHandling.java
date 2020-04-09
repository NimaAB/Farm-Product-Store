package exceptions;

import exceptions.dataExceptions.InvalidNumberException;
import exceptions.dataExceptions.InvalidTextInputException;

public class ExceptionHandling {
    public static String messege="";

    public static Number[] convertNumberInputsWcheck(String artikkelnrStr, String prisStr){
        Number artikkelnr = null;
        Number pris = null;
        Number sjekkeTall = 1;
        String newPrisStr="";
        try{
            artikkelnr = Integer.parseInt(artikkelnrStr);
            if(prisStr.contains(",")){ //If someone uses coma instead of dot for desimal values.
                newPrisStr = prisStr.replace(",",".");
                pris = Double.parseDouble(newPrisStr);
            }else {
                pris = Double.parseDouble(prisStr);
            }
        }catch (InvalidNumberException e){
            sjekkeTall = 0;
            messege = e.getMessage();
        }
        //sjekkeTall blir parset til (int) ved bruk: når sjekkTall (1 er true og 0 er false)
        return new Number[]{artikkelnr, pris, sjekkeTall};
    }
    public static boolean isValidTextInput(String artikkelNavn,String spesifikasjoner){
        try{
            Validation.textInputValidation(artikkelNavn,spesifikasjoner);
        }catch (InvalidTextInputException e){
            messege = e.getMessage();
            return false;
        }
        return true;
    }

}
