package Kosik;

import java.util.ArrayList;

import Menu.MenuProduktu;
import produkty.VylepsitelnyProdukt;

public class Kosik  {
    private ArrayList<Objednavka> kosik = new ArrayList<>();
    private int indexPrepisovani = 0;
    private static Kosik instance;

    private Kosik() {
        for (int i = 0; i < 10; i++) {
            kosik.add(new Objednavka());
        }
    }

    public static Kosik getKosik() {
        if (instance == null) {
            instance = new Kosik();
        }
        return instance;
    }
    //                          u noveMenu se nevytvoří nová reference
    public void pridejObjednavku(MenuProduktu noveMenu, ArrayList<VylepsitelnyProdukt> produkty) {
        Objednavka objednavka = new Objednavka(); // při vytvoření novýho objektu se vytvoří nová reference (nové místo v paměti)
        if (noveMenu != null) {
            MenuProduktu menu = new MenuProduktu();
            for ( VylepsitelnyProdukt produkt : noveMenu.getProdukty()) {
                menu.pridejProdukt(produkt);
            }
            objednavka.pridejMenu(menu);
        }
        for (VylepsitelnyProdukt produkt : produkty) {
            objednavka.pridejProdukt(produkt);
        }
        if (indexPrepisovani < 10) {
            kosik.set(indexPrepisovani,objednavka);
            indexPrepisovani++;
        } else {
            indexPrepisovani = 0;
            kosik.set(indexPrepisovani,objednavka);
        }
    }
    public Objednavka ukazObjednavku(int cisloObjednavky) {
        if(cisloObjednavky == -1) {
            return null;
        }
        if(kosik.get(cisloObjednavky).getMenu().getProdukty().size() == 0 && kosik.get(cisloObjednavky).getProdukty().size() == 0) {
            return null;
        }
        return kosik.get(cisloObjednavky);
    }
    public int getVelikostKosiku() {
        return kosik.size();
    }
}
