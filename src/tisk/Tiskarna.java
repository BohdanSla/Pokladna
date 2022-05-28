package tisk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import Kosik.Objednavka;
import Menu.MenuProduktu;
import Vylepseni.Vylepseni;
import produkty.Produkt;
import produkty.VylepsitelnyProdukt;

public class Tiskarna {
    private static  Tiskarna instance;
    private BufferedWriter writer;
    private String cesta = "C:\\Users\\Bohdan Slawisch\\Desktop\\programování škola\\JAVA\\projekty\\2.D\\Pokladna\\src\\data\\uctenka.txt";

    private Tiskarna() {};

    public static Tiskarna getTiskarna() {
        if (instance == null) {
            instance = new Tiskarna();
        }

        return instance;
    };

    public void tisk(Objednavka objednavka) {
        ArrayList<VylepsitelnyProdukt> produkty = objednavka.getProdukty();
        MenuProduktu menuProdukty = objednavka.getMenu();
        try {
            writer = new BufferedWriter(new FileWriter(cesta));
            writer.write("========UCTENKA========");
            writer.newLine();
            for (VylepsitelnyProdukt el : produkty) {
                writer.write(el.getNazevProduktu() + " " + el.getCenaProduktu() + " kc");
                writer.newLine();
                for (Vylepseni e : el.getVylepseni()) {
                    writer.write("  +" + e.getNazevVylepseni() + " " + e.getCenaVylepseni() + " kc");
                    writer.newLine();
                }
            }
            writer.write("Menu: ");
            writer.newLine();
            for (VylepsitelnyProdukt el : menuProdukty.getProdukty()) {
                writer.write(el.getNazevProduktu() + " " + el.getCenaProduktu() + " kc");
                writer.newLine();
                for (Vylepseni e : el.getVylepseni()) {
                    writer.write("  +" + e.getNazevVylepseni() + " " + e.getCenaVylepseni() + " kc");
                    writer.newLine();
                }
            }
            writer.write("Cena menu: " + menuProdukty.calculateCena() + " kc");
            writer.newLine();
            writer.write("Celkova cena: " + objednavka.calculateCena() + " kc");
            writer.newLine();
            writer.write("=======================");
            writer.flush();
            
        } catch (Exception e) {
            //TODO: handle exception
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
