package produkty;

public class Produkt {
    protected String nazevProduktu;
    protected int cenaProduktu;

    public String getNazevProduktu() {
        return this.nazevProduktu;
    }

    public void setNazevProduktu(String nazevProduktu) {
        this.nazevProduktu = nazevProduktu;
    }

    public int getCenaProduktu() {
        return this.cenaProduktu;
    }

    public void setCenaProduktu(int cenaProduktu) {
        this.cenaProduktu = cenaProduktu;
    }

    public Produkt(String nazevProduktu,int cenaProduktu) {
        this.nazevProduktu = nazevProduktu;
        this.cenaProduktu = cenaProduktu;
    }
}
