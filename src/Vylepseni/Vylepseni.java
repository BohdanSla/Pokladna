package Vylepseni;

public class Vylepseni {
    protected String nazevVylepseni;
    protected int cenaVylepseni;

    public String getNazevVylepseni() {
        return this.nazevVylepseni;
    }

    public void setNazevVylepseni(String nazevVylepseni) {
        this.nazevVylepseni = nazevVylepseni;
    }

    public int getCenaVylepseni() {
        return this.cenaVylepseni;
    }

    public void setCenaVylepseni(int cenaVylepseni) {
        this.cenaVylepseni = cenaVylepseni;
    }

    public Vylepseni(String nazevVylepseni,int cenaVylepseni) {
        this.nazevVylepseni = nazevVylepseni;
        this.cenaVylepseni = cenaVylepseni;
    }
}
