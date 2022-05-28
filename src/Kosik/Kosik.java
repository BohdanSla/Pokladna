package Kosik;

import java.util.ArrayList;

import CenaCalculator.CenaCalculator;

public class Kosik implements CenaCalculator {
    private ArrayList<Objednavka> kosik = new ArrayList<>();
    private int prepisovani = 0;

    public void pridejObjednavku(Objednavka objednavka) {
        if (prepisovani < 10) {
            kosik.set(prepisovani,objednavka);
            prepisovani++;
        } else {
            prepisovani = 0;
            kosik.set(prepisovani,objednavka);
        }
    }
    public void odeberObjednavku(int cisloObjednavky) {
        if(cisloObjednavky < kosik.size() && cisloObjednavky != -1)
            kosik.remove(cisloObjednavky);
    }
    public Objednavka ukazObjednavku(int cisloObjednavky) {
        if(cisloObjednavky < kosik.size() && cisloObjednavky != -1)
            return kosik.get(cisloObjednavky);
        else 
            return null;
    }
    public int getVelikostKosiku() {
        return kosik.size();
    }
    @Override
    public int calculateCena() {
        // TODO Auto-generated method stub
        return 0;
    }
}
