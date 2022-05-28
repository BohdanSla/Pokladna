package Kosik;

import java.util.ArrayList;

import CenaCalculator.CenaCalculator;
import Menu.Menu;
import produkty.*;

public class Objednavka implements CenaCalculator {
    ArrayList<Produkt> objednavka = new ArrayList<>();
    ArrayList<Menu> menuObjednavky = new ArrayList<>();

    public void pridejProdukt(Produkt produkt) {
        objednavka.add(produkt);
    }
    public void pridejMenu(Menu menu) {
        menuObjednavky.add(menu);
    }

    @Override
    public int calculateCena() {
        // TODO Auto-generated method stub
        return 0;
    }
}
