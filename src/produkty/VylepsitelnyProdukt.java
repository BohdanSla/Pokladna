package produkty;

import java.util.ArrayList;

import Vylepseni.Vylepseni;
import CenaCalculator.*;

public class VylepsitelnyProdukt extends Produkt implements CenaCalculator {

    private ArrayList<Vylepseni> vylepseni = new ArrayList<>();

    public VylepsitelnyProdukt(String nazevProduktu, int cenaProduktu) {
        super(nazevProduktu, cenaProduktu);
        //TODO Auto-generated constructor stub
    }
    
    public void pridejVylepseni(Vylepseni vylepseni) {
        this.vylepseni.add(vylepseni);
    }

    @Override
    public int calculateCena() {
        return 0;
    }

}
