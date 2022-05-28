package Menu;

import java.util.ArrayList;

import CenaCalculator.CenaCalculator;
import produkty.Produkt;
import produkty.VylepsitelnyProdukt;


public class MenuProduktu implements CenaCalculator {
    private ArrayList<VylepsitelnyProdukt> produkty = new ArrayList<>();

    public void pridejProdukt(VylepsitelnyProdukt produkt) {
        produkty.add(produkt);
    }
    public ArrayList<VylepsitelnyProdukt> getProdukty() {
        return produkty;
    }
    public void odeberProdukty() {
        produkty.removeAll(produkty);
    }

    @Override
    public int calculateCena() {
        // TODO Auto-generated method stub
        int cena = 0;
        cena = produkty
            .stream()
            .mapToInt(Produkt::getCenaProduktu)
            .sum();
        cena += produkty
            .stream()
            .mapToInt(e -> e.calculateCena())
            .sum();
        produkty.stream().close();
        return cena;
            
    }

}
