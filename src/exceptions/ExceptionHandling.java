package exceptions;

import exceptions.formInputExceptions.InvalidNumberException;

public class ExceptionHandling {
    public static String messege="";

    public static Number[] convertNumberInputsWcheck(String artikkelnrStr, String prisStr){
        Number artikkelnr = null;
        Number pris = null;
        Number sjekkeTall = 1;
        try{
            artikkelnr = Integer.parseInt(artikkelnrStr);
            pris = Double.parseDouble(prisStr);

        }catch (InvalidNumberException e){
            sjekkeTall = 0;
            messege = e.getMessage();
        }
        //sjekkeTall blir parset til (int) ved bruk: når sjekkTall (1 er true og 0 er false)
        return new Number[]{artikkelnr, pris, sjekkeTall};
    }
}
