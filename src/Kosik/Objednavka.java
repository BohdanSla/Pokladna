package Kosik;

import java.util.*;

import CenaCalculator.CenaCalculator;
import Menu.MenuProduktu;
import produkty.*;

public class Objednavka implements CenaCalculator {
    private ArrayList<VylepsitelnyProdukt> produkty = new ArrayList<>();
    private MenuProduktu menu = new MenuProduktu();

    public void pridejProdukt(VylepsitelnyProdukt p) {
        produkty.add(p);
    }
    public void odeberProdukt(String nazev) {
       VylepsitelnyProdukt odebirany = produkty
            .stream()
            .filter(produkt -> produkt.getNazevProduktu().compareToIgnoreCase(nazev) == 0)
            .findFirst()
            .orElse(null);
        produkty.stream().close();
        produkty.remove(odebirany);
    }
    public void pridejMenu(MenuProduktu menu) {
        this.menu = menu;
    }
    public void odeberMenu() {
        this.menu = null;
    }
    public ArrayList<VylepsitelnyProdukt> getProdukty() {
        return produkty;
    }
    public VylepsitelnyProdukt getProdukt(int index) {
        if (index > produkty.size() || index < 0 ) {
            return null;
        }
        return produkty.get(index);
    }
    public MenuProduktu getMenu() {
        return menu;
    }
    public void smazatObjednavku() {
        produkty.removeAll(produkty);
        odeberMenu();
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
        if (menu == null) {
            return cena;
        }
        return cena + menu.calculateCena();
    }
}
