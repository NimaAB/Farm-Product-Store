package dataModels.dataFormats;

import dataModels.Items;

public class ParseItems {
    public static Items parseItem(String itemTxt){
        String [] item = itemTxt.split(";");
        String artikkelnr = item[0];
        String artikkelNavn = item[1];
        String kategori = item[2];
        String spesifikasjoer = item[3];
        String pris = item[4];
        //disse to skal gå gjennom en validering (Det er midlertidig løsning):
        int artikkelnrInt = Integer.parseInt(artikkelnr);
        double prisD = Double.parseDouble(pris);

        return new Items(artikkelnrInt,artikkelNavn,spesifikasjoer,kategori,prisD);
    }
}
