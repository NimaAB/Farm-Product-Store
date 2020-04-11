package dataModels.data;

// Enummen er som en liste.
// Vi kan velge fra den senere i AdminController med en choiser input.
public enum Kategories {
    KATEGORI1("name1"),
    KATEGORI2("name2"),
    KATEGORI3("name3"),
    KATEGORI4("name4");

    private String kategoriName;
    Kategories(String kategoriname){
        this.kategoriName = kategoriname;
    }
    @Override
    public String toString() {
        return kategoriName;
    }
}
