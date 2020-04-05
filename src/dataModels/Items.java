package dataModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Items implements Serializable {
    private SimpleIntegerProperty artikkelnr;
    private SimpleStringProperty artikkelNavn;
    private SimpleStringProperty spesifikasjoner;
    private SimpleStringProperty kategori;
    private SimpleIntegerProperty pris;

    public Items(int artikkelnr, String artikkelNavn,String kategori
            ,String spesifikasjoner,int pris){
        this.artikkelnr = new SimpleIntegerProperty(artikkelnr);
        this.artikkelNavn= new SimpleStringProperty(artikkelNavn);
        this.kategori = new SimpleStringProperty(kategori);
        this.spesifikasjoner = new SimpleStringProperty(spesifikasjoner);
        this.pris = new SimpleIntegerProperty(pris);
    }
    public void setArtikkelnr(int artikkelnr){
        this.artikkelnr.set(artikkelnr+1);
    }
    public int getArtikkelnr(){
        return this.artikkelnr.getValue();
    }

    public void setArtikkelNavn(String name){
        this.artikkelNavn.set(name);
    }
    public String getArtikkelNavn(){
        return this.artikkelNavn.getValue();
    }

    public void setKategori(String kategori){
        this.kategori.set(kategori);
    }
    public String getKategori(){
        return this.kategori.getValue();
    }

    public void setSpesifikasjoner(String spesifikasjoner) {
        this.spesifikasjoner.set(spesifikasjoner);
    }
    public String getSpesifikasjoner() {
        return spesifikasjoner.getValue();
    }

    public void setPris(int pris){
        this.pris.set(pris);
    }
    public int getPris(){
        return pris.getValue();
    }

    //Serielizing part:
    private void writeObject(ObjectOutputStream st) throws IOException{
        st.defaultWriteObject();
        st.writeInt(getArtikkelnr());
        st.writeUTF(getArtikkelNavn());
        st.writeUTF(getKategori());
        st.writeUTF(getSpesifikasjoner());
        st.writeInt(getPris());
    }
    private void readObject(ObjectInputStream st) throws IOException,ClassNotFoundException{
        int artikkelnr = st.readInt();
        String artikkelNavn = st.readUTF();
        String kategori = st.readUTF();
        String spesifikasjoner = st.readUTF();
        int pris = st.readInt();
        this.artikkelnr = new SimpleIntegerProperty(artikkelnr);
        this.artikkelNavn = new SimpleStringProperty(artikkelNavn);
        this.kategori = new SimpleStringProperty(kategori);
        this.spesifikasjoner = new SimpleStringProperty(spesifikasjoner);
        this.pris = new SimpleIntegerProperty(pris);
    }
}
