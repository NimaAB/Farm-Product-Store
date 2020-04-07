package exceptions;

import exceptions.formInputExceptions.InvalidNumberException;

public class ExceptionHandling {
    public static String messege="";

    public static Number[] numberInputs(String artikkerNr, String pris){
        Number artikkelnr = null;
        Number prisD = null;
        Number sjekkeTall = 1;
        try{
            artikkelnr = Integer.parseInt(artikkerNr);
            prisD = Double.parseDouble(pris);

        }catch (InvalidNumberException e){
            sjekkeTall = 0;
            messege = e.getMessage();
        }
        return new Number[]{artikkelnr, prisD, sjekkeTall};
    }
}
