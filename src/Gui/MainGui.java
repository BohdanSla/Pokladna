package Gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import Kosik.Kosik;
import Kosik.Objednavka;
import Menu.MenuProduktu;
import Vylepseni.Vylepseni;
import produkty.Produkt;
import produkty.VylepsitelnyProdukt;
import tisk.Tiskarna;

import java.awt.*;
import java.util.ArrayList;       

public class MainGui extends JFrame {

    private JButton pridatObjednavku,vytisknoutObjednavku;
    private JPanel tlacitka,menuACelkovyCeny;
    private JTable objednavky;
    private JTextArea produkty,menu;
    private JLabel celkovaCenaMenu, celkovaCenaObjednavky;
    private JScrollPane produktyPane, menuPane;

    private DefaultTableModel objednavkyModel;

    private String nadpis[] = {""};

    private boolean jeVypsany = false;

    private Kosik kosik;

    private Tiskarna tiskarna;

    public MainGui() {

        setResizable(false);
        setSize(1200, 700);
        setTitle("Pokladna");
        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,4));

        kosik = Kosik.getKosik();

        tlacitka = new JPanel(new GridLayout(2,1));
        add(tlacitka);

        pridatObjednavku = new JButton("Přidat objednávku");
        pridatObjednavku.addActionListener(e -> {
            MinorGui gui = MinorGui.getMinorGUi();
            gui.setVisible(true);
        });

        vytisknoutObjednavku = new JButton("Vytisknout objednávku");
        vytisknoutObjednavku.addActionListener(e -> {
            String cisloObjednavky = "";
            try {
                do {
                    do {
                            cisloObjednavky = JOptionPane.showInputDialog(null,"Zadej číslo objednávky");
                            if (cisloObjednavky.isEmpty()) {
                                JOptionPane.showMessageDialog(null,"Zadej číslo objednávky");
                            } else if(!cisloObjednavky.matches("[0-9]+")) {
                                JOptionPane.showMessageDialog(null,"Zadej jenom čísla");
                            } 
                    } while (cisloObjednavky.isEmpty() || !cisloObjednavky.matches("[0-9]+"));
                        if(Integer.parseInt(cisloObjednavky) > 10 || Integer.parseInt(cisloObjednavky) < 1) {
                            JOptionPane.showMessageDialog(null,"zadej číslo mezi 1 a 10");
                        }
                } while (Integer.parseInt(cisloObjednavky) > 10 || Integer.parseInt(cisloObjednavky) < 1);
                Objednavka objednavka = kosik.ukazObjednavku(Integer.parseInt(cisloObjednavky) - 1);

                if (objednavka == null) {
                    JOptionPane.showMessageDialog(null, "Zadaná objednávka neexistuje");
                } else {
                    tiskarna = Tiskarna.getTiskarna();
                    tiskarna.tisk(objednavka);
                    JOptionPane.showMessageDialog(null, "Objednávka byla vytisknuta");
                }
            } catch (Exception ex) {
                //TODO: handle exception
            }
        });

        tlacitka.add(pridatObjednavku);
        tlacitka.add(vytisknoutObjednavku);

        objednavkyModel = new DefaultTableModel(nadpis,11);
        objednavky = new JTable(objednavkyModel);
        objednavky.setValueAt("OBJEDNÁVKY", 0, 0);
        for (int i = 1; i < 11; i++) {
            objednavky.setValueAt("Objednávka #" + i, i, 0);
        }
        objednavky.setDefaultEditor(Object.class, null);
        objednavky.setRowHeight(55);
        objednavky.setRowHeight(0, 100);
        objednavky.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO Auto-generated method 
                if (!jeVypsany) {
                    vypisObjednavku(kosik.ukazObjednavku(objednavky.getSelectedRow() - 1));
                    jeVypsany = true;
                } else {
                    jeVypsany = false;
                }
            }
        });
        add(objednavky);

        produkty = new JTextArea();
        produkty.setEditable(false);
        produkty.setBackground(Color.WHITE);
        add(produkty);

        produktyPane = new JScrollPane(produkty,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        produktyPane.setPreferredSize(new Dimension());
        add(produktyPane);

        menuACelkovyCeny = new JPanel();
        menuACelkovyCeny.setLayout(new BoxLayout(menuACelkovyCeny,BoxLayout.PAGE_AXIS));
        add(menuACelkovyCeny);

        menu = new JTextArea();
        menu.setBackground(Color.WHITE);
        menu.setEditable(false);
        menuACelkovyCeny.add(menu);

        menuPane = new JScrollPane(menu,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
        menuPane.setPreferredSize(new Dimension());
        menuACelkovyCeny.add(menuPane);

        celkovaCenaMenu = new JLabel("Celková cena menu: ");
        menuACelkovyCeny.add(celkovaCenaMenu);

        celkovaCenaObjednavky = new JLabel("Celková cena objednávky: ");
        menuACelkovyCeny.add(celkovaCenaObjednavky);
    }

    private void vypisObjednavku(Objednavka objednavka) {
        produkty.setText("");
        menu.setText("");
        celkovaCenaMenu.setText("Celková cena menu: ");
        celkovaCenaObjednavky.setText("Celková cena objednávky: ");
        if(objednavka != null) {
            ArrayList<VylepsitelnyProdukt> produktyObjednavky = objednavka.getProdukty();
            for (VylepsitelnyProdukt produkt : produktyObjednavky) {
                produkty.setText(produkty.getText() + "\n" + produkt.getNazevProduktu() + " " + produkt.getCenaProduktu() + " kč");
                for (Vylepseni el : produkt.getVylepseni()) {
                    produkty.setText(produkty.getText() + "\n  +" + el.getNazevVylepseni() + " " + el.getCenaVylepseni() + " kč");
                }
            }
            produktyObjednavky= objednavka.getMenu().getProdukty();
            for (VylepsitelnyProdukt produkt : produktyObjednavky) {
                menu.setText(menu.getText() + "\n" + produkt.getNazevProduktu() + " " + produkt.getCenaProduktu() + " kč");
                for (Vylepseni el : produkt.getVylepseni()) {
                    menu.setText(menu.getText() + "\n  +" + el.getNazevVylepseni() + " " + el.getCenaVylepseni() + " kč");
                }
            }
            celkovaCenaMenu.setText("Celková cena menu: " + objednavka.getMenu().calculateCena() + " kč");
            celkovaCenaObjednavky.setText("Celková cena objednávky: " + objednavka.calculateCena() + " kč");
        }
    }  
    
}
