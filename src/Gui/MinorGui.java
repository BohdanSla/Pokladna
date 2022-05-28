package Gui;

import javax.swing.*;

import Reader.ReaderPriloh;
import Reader.ReaderProduktu;
import Reader.ReaderVylepseni;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MinorGui extends JFrame {
        private static MinorGui gui;

        private ReaderProduktu readerProduktu;
        private ReaderVylepseni readerVylepseni;
        private ReaderPriloh readerPriloh;

        private JButton pridatProdukt, pridatMenuDoObjednavky, pridatVyberDoMenu, pridatObjednavku;
        private JPanel tlacitka,informace,produkty,vylepseni,prilohy;

        private int pocetZaskrtnutychBoxu;

        private MinorGui() {
            setResizable(false);
            setSize(1200, 700);
            setTitle("Přídávání produktů/menu");
            setLayout(new GridLayout(1,5));

            //readerProduktu.nacti(); // :(
            //readerVylepseni.nacti();

            pridatProdukt = new JButton("Přidat produkt do objednávky");
            pridatVyberDoMenu = new JButton("Přidat Výběr do Menu");
            pridatMenuDoObjednavky = new JButton("Přidat Menu do objednávky");
            pridatObjednavku = new JButton("Přidat objednávku");

            tlacitka = new JPanel(new GridLayout(4,1));
            add(tlacitka);

            tlacitka.add(pridatProdukt);
            tlacitka.add(pridatVyberDoMenu);
            tlacitka.add(pridatMenuDoObjednavky);
            tlacitka.add(pridatObjednavku);

            informace = new JPanel(new GridLayout(2,1));
            add(informace);

            readerProduktu = new ReaderProduktu();

            produkty = new JPanel(new GridLayout(readerProduktu.nacti().size(),1));
            add(produkty);

            pocetZaskrtnutychBoxu = 0;

            for (int i = 0; i < readerProduktu.nacti().size(); i++) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(readerProduktu.nacti().get(i).getNazevProduktu() + " " + readerProduktu.nacti().get(i).getCenaProduktu() + " kč");
                produkty.add(checkBox);
                checkBox.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        // TODO Auto-generated method stub
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                pocetZaskrtnutychBoxu++;
                                if (pocetZaskrtnutychBoxu < 2) {
                                    vylepseni.removeAll();
                                    vypisVylepseni();
                                } else {
                                    vylepseni.removeAll();
                                }
                            } else if(e.getStateChange() == ItemEvent.DESELECTED) {
                                vylepseni.removeAll();
                                pocetZaskrtnutychBoxu--;
                            } 
                        revalidate();
                        repaint();
                    }
                    
                });
            }
            
            readerPriloh = new ReaderPriloh();

            prilohy = new JPanel(new GridLayout(readerPriloh.nacti().size(),1));
            add(prilohy);


            for (int i = 0; i < readerPriloh.nacti().size(); i++) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(readerPriloh.nacti().get(i).getNazevProduktu() + " " + readerPriloh.nacti().get(i).getCenaProduktu() + " kč");
                prilohy.add(checkBox);
            }

            readerVylepseni = new ReaderVylepseni();

            vylepseni = new JPanel(new GridLayout(readerVylepseni.nacti().size(),1));
            add(vylepseni);
        }

        private void vypisVylepseni() {
            for (int i = 0; i < readerVylepseni.nacti().size(); i++) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(readerVylepseni.nacti().get(i).getNazevVylepseni() + " " + readerVylepseni.nacti().get(i).getCenaVylepseni() + " kč");
                vylepseni.add(checkBox);
            }
        }

        public static MinorGui getMinorGUi() {
            if (gui == null) {
                gui = new MinorGui();
            }
            return gui;
        }
    }
