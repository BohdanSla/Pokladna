package Gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import Kosik.Kosik;
import Kosik.Objednavka;

import java.awt.*;       

public class MainGui extends JFrame {

    private JButton pridatObjednavku,odeberObjednavku,vytisknoutObjednavku;
    private JPanel tlacitka,inforamce;
    private JTable objednavky,produkty,vylepseni;

    private DefaultTableModel objednavkyModel,produktyModel,vylepseniModel;

    private String nadpis[] = {""};

    private Kosik kosik = new Kosik();

    public MainGui() {

        setResizable(false);
        setSize(1200, 700);
        setTitle("Pokladna");
        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,5));

        tlacitka = new JPanel(new GridLayout(3,1));
        add(tlacitka);

        pridatObjednavku = new JButton("Přidat objednávku");
        pridatObjednavku.addActionListener(e -> {
            MinorGui gui = MinorGui.getMinorGUi();
            gui.setVisible(true);
        });

        odeberObjednavku = new JButton("Odebrat objednávku");
        odeberObjednavku.addActionListener(e -> {
            String cisloObjednavkyInput = JOptionPane.showInputDialog("Zadej číslo objednávky");
            int cisloObjednavky = Integer.parseInt(cisloObjednavkyInput);
            kosik.odeberObjednavku(cisloObjednavky);
        });
        vytisknoutObjednavku = new JButton("Vytisknout objednávku");

        tlacitka.add(pridatObjednavku);
        tlacitka.add(odeberObjednavku);
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
                vypisObjednavku(kosik.ukazObjednavku(objednavky.getSelectedRow() - 1),kosik.getVelikostKosiku());
            }
        });

        produktyModel = new DefaultTableModel(nadpis,1);
        produkty = new JTable(produktyModel);
        produkty.setValueAt("PRODUKTY/MENU",0,0);
        produkty.setRowHeight(55);
        produkty.setRowHeight(0, 100);

        vylepseniModel = new DefaultTableModel(nadpis,1);
        vylepseni = new JTable(vylepseniModel);
        vylepseni.setValueAt("VYLEPŠENÍ",0,0);
        vylepseni.setRowHeight(55);
        vylepseni.setRowHeight(0, 100);

        add(objednavky);
        add(produkty);
        add(vylepseni);

        inforamce = new JPanel();
        add(inforamce);

    }
    private void vypisObjednavku(Objednavka objednavka,int velikostObjednavky) {

    }
    
}
