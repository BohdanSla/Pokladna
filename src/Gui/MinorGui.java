package Gui;

import javax.swing.*;

import Kosik.Kosik;
import Kosik.Objednavka;
import Menu.MenuProduktu;
import Reader.ReaderPriloh;
import Reader.ReaderProduktu;
import Reader.ReaderVylepseni;
import Vylepseni.Vylepseni;
import produkty.Produkt;
import produkty.VylepsitelnyProdukt;
import tisk.Tiskarna;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MinorGui extends JFrame {
        private static MinorGui gui;

        private ReaderProduktu readerProduktu;
        private ReaderVylepseni readerVylepseni;
        private ReaderPriloh readerPriloh;

        private JButton pridatProdukt, pridatMenuDoObjednavky, pridatVyberDoMenu, pridatObjednavku,smazatProdukt,smazatMenu,smazatVyber;
        private JPanel tlacitka,informace,produkty,vylepseni,prilohy;
        private JTextArea informaceOProduktech,informaceOPridanemMenu,informaceOMenu;
        private JScrollPane textFieldPaneObjednavka,textFieldPaneMenu,textFieldPanePridaneMenu;
        private JLabel celkovaCenaObjednavkyLabel,celkovaCenaMenuLabel;

        private int pocetZaskrtnutychBoxu, pocetProduktuVMenu;
        private boolean jeVylepsenitelny;

        private ArrayList<JCheckBox> boxProdukty,boxPrilohy,boxVylepseni;
        private ArrayList<Vylepseni> vylepseniArray;

        private Objednavka objednavka;
        private MenuProduktu noveMenu;
        private Kosik kosik;

        private Tiskarna tiskarna;

        private MinorGui() {
            setResizable(false);
            setSize(1200, 700);
            setTitle("Produkty/menu");
            setLayout(new GridLayout(1,5));

            boxProdukty = new ArrayList<>();
            boxPrilohy = new ArrayList<>();
            boxVylepseni = new ArrayList<>();
            noveMenu = new MenuProduktu();
            objednavka = new Objednavka();
            kosik = Kosik.getKosik();
            tiskarna = Tiskarna.getTiskarna();

            String[] moznosti = {"Potvrdit","Zahodit","Cancel"};

            pridatProdukt = new JButton("Přidat produkt do objednávky");
            pridatProdukt.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    if (pocetZaskrtnutychBoxu < 2) {
                        for ( JCheckBox check : boxProdukty) {
                            if (check.isSelected()) {
                                String[] rozdeleny = regexProduktu(check.getText());
                                int cena = Integer.parseInt(rozdeleny[1]);
                                VylepsitelnyProdukt p = vytvorProdukt(rozdeleny[0], cena);
                                objednavka.pridejProdukt(p);
                            }
                        }
                        for (JCheckBox check : boxPrilohy) {
                            if(check.isSelected()) {
                                String[] rozdeleny = regexProduktu(check.getText());
                                int cena = Integer.parseInt(rozdeleny[1]);
                                objednavka.pridejProdukt(new VylepsitelnyProdukt(rozdeleny[0], cena));
                            }
                        }
                        vypisProdukty();
                        vypisCelkovouCenuObjednavky();
                    }
                }});
            pridatVyberDoMenu = new JButton("Přidat Výběr do Menu");
            pridatVyberDoMenu.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    for (JCheckBox check : boxProdukty) {
                        if(check.isSelected()) {
                            if (pocetProduktuVMenu == 5) {
                                break;
                            }
                            pocetProduktuVMenu++;
                            String[] rozdeleny = regexProduktu(check.getText());
                            int cena = pocetProduktuVMenu == 3 ? 0 : Integer.parseInt(rozdeleny[1]);
                            VylepsitelnyProdukt p = vytvorProdukt(rozdeleny[0], cena);
                            noveMenu.pridejProdukt(p);
                        }
                    }
                    for (JCheckBox check : boxPrilohy) {
                        if(check.isSelected()) {
                            if (pocetProduktuVMenu == 5) {
                                break;
                            }
                            pocetProduktuVMenu++;
                            String[] rozdeleny = regexProduktu(check.getText());
                            int cena = pocetProduktuVMenu == 3 ? 0 : Integer.parseInt(rozdeleny[1]);
                            noveMenu.pridejProdukt(new VylepsitelnyProdukt(rozdeleny[0], cena));
                        }
                    }
                    vypisMenu();
                    vypisCelkovouCenuMenu();
                }
            });
            pridatMenuDoObjednavky = new JButton("Přidat Menu do objednávky");
            pridatMenuDoObjednavky.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    objednavka.pridejMenu(noveMenu);
                    vypisPridaneMenu();
                }

            });
            smazatProdukt = new JButton("Smazat Produkt z objednávky");
            smazatProdukt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    String odebirany = null;
                    do {
                        try {
                            odebirany = JOptionPane.showInputDialog(null, "Zadej nazev produktu");
                        } catch (Exception ex) {
                            //TODO: handle exception
                        }
                        if(odebirany.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Zadej název prosím");
                        }
                    } while (odebirany.isEmpty());
                    objednavka.odeberProdukt(odebirany);
                    vypisProdukty();
                    vypisCelkovouCenuObjednavky();

                }});
            smazatMenu = new JButton("Smazat Menu z objednávky");
            smazatMenu.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    objednavka.odeberMenu();
                    informaceOPridanemMenu.setText("");
                }});
            pridatObjednavku = new JButton("Přidat objednávku");
            pridatObjednavku.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    int i = JOptionPane.showOptionDialog(null, "Potvrdit nebo zahodit objednávku?", "Objednávka",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,moznosti,moznosti[2]);
                    if (i == JOptionPane.YES_OPTION) {
                        kosik.pridejObjednavku(objednavka.getMenu(),objednavka.getProdukty());
                        tiskarna.tisk(objednavka);
                    } else if(i == JOptionPane.NO_OPTION) {
                        pocetProduktuVMenu = 0;
                        objednavka.smazatObjednavku();
                        noveMenu.odeberProdukty();
                        informaceOMenu.setText("");
                        informaceOPridanemMenu.setText("");
                        informaceOProduktech.setText("");
                        celkovaCenaMenuLabel.setText("Celková cena menu: ");
                        celkovaCenaObjednavkyLabel.setText("Celková cena objednávky: ");
                    }
                }
                
            });

            smazatVyber = new JButton("Smazat výběr");
            smazatVyber.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    informaceOMenu.setText("");
                    pocetProduktuVMenu =0;
                    noveMenu.odeberProdukty();
                }

            });
            
            tlacitka = new JPanel(new GridLayout(7,1));
            add(tlacitka);
            
            tlacitka.add(pridatProdukt);
            tlacitka.add(pridatVyberDoMenu);
            tlacitka.add(pridatMenuDoObjednavky);
            tlacitka.add(pridatObjednavku);
            tlacitka.add(smazatProdukt);
            tlacitka.add(smazatMenu);
            tlacitka.add(smazatVyber);
            
            informace = new JPanel();
            informace.setLayout(new BoxLayout(informace,BoxLayout.PAGE_AXIS));
            add(informace);

            informaceOProduktech = new JTextArea();
            informaceOProduktech.setEditable(false);
            informace.add(informaceOProduktech);
            
            textFieldPaneObjednavka = new JScrollPane(informaceOProduktech,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            textFieldPaneObjednavka.setPreferredSize(new Dimension());
            informace.add(textFieldPaneObjednavka);

            informaceOPridanemMenu = new JTextArea();
            informaceOPridanemMenu.setEditable(false);
            informace.add(informaceOPridanemMenu);

            textFieldPanePridaneMenu = new JScrollPane(informaceOPridanemMenu,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            textFieldPanePridaneMenu.setPreferredSize(new Dimension());
            informace.add(textFieldPanePridaneMenu);


            celkovaCenaObjednavkyLabel = new JLabel("Celková cena objednávky: ");
            informace.add(celkovaCenaObjednavkyLabel);

            informaceOMenu = new JTextArea();
            informaceOMenu.setEditable(false);
            informace.add(informaceOMenu);

            textFieldPaneMenu = new JScrollPane(informaceOMenu,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            textFieldPaneMenu.setPreferredSize(new Dimension());
            informace.add(textFieldPaneMenu);

            celkovaCenaMenuLabel = new JLabel("Celková cena Menu: ");
            informace.add(celkovaCenaMenuLabel);

            readerProduktu = new ReaderProduktu();

            produkty = new JPanel(new GridLayout(boxProdukty.size(),1));
            add(produkty);

            for (int i = 0; i < readerProduktu.nacti().size(); i++) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(readerProduktu.nacti().get(i).getNazevProduktu() + "," + readerProduktu.nacti().get(i).getCenaProduktu() + " kč");
                produkty.add(checkBox);
                boxProdukty.add(checkBox);
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
                                pocetZaskrtnutychBoxu--;
                                if (pocetZaskrtnutychBoxu < 2) {
                                    vylepseni.removeAll();
                                }
                                vypisVylepseni();
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
                checkBox.setText(readerPriloh.nacti().get(i).getNazevProduktu() + "," + readerPriloh.nacti().get(i).getCenaProduktu() + " kč");
                prilohy.add(checkBox);
                boxPrilohy.add(checkBox);
                checkBox.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        // TODO Auto-generated method stub
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                pocetZaskrtnutychBoxu++;
                            } else if(e.getStateChange() == ItemEvent.DESELECTED) {
                                pocetZaskrtnutychBoxu--;
                            } 
                    }
                    
                });
            }

            readerVylepseni = new ReaderVylepseni();

            vylepseni = new JPanel(new GridLayout(readerVylepseni.nacti().size(),1));
            add(vylepseni);

            vylepseniArray = readerVylepseni.nacti();

        }

        private VylepsitelnyProdukt vytvorProdukt(String string, int cena) {
            VylepsitelnyProdukt produkt = new VylepsitelnyProdukt(string, cena);
            if (jeVylepsenitelny) {
                for (JCheckBox el : boxVylepseni) {
                    if (el.isSelected()) {
                        String[] rozdeleny = regexProduktu(el.getText());
                        int cenaVylepseni = pocetProduktuVMenu == 3 ? 0 : Integer.parseInt(rozdeleny[1]);
                        Vylepseni vylepseniProduktu = new Vylepseni(rozdeleny[0], cenaVylepseni);
                        produkt.pridejVylepseni(vylepseniProduktu);
                    }
                }
                return produkt;
            } 
            return produkt;
        }

        private void vypisVylepseni() {
            jeVylepsenitelny = false;
            boxVylepseni.removeAll(boxVylepseni);
            for (JCheckBox el : boxProdukty) {
                String produkt[] = regexProduktu(el.getText());
                if (el.isSelected() && (produkt[0].compareToIgnoreCase("hovezi burger") == 0 || produkt[0].compareToIgnoreCase("kureci burger") == 0 || produkt[0].compareToIgnoreCase("cheeseburger") == 0  || produkt[0].compareToIgnoreCase("big mac") == 0)) {
                    for (int i = 0; i < vylepseniArray.size(); i++) {
                        JCheckBox checkBox = new JCheckBox();
                        checkBox.setText(vylepseniArray.get(i).getNazevVylepseni() + "," + vylepseniArray.get(i).getCenaVylepseni() + " kč");
                        boxVylepseni.add(checkBox);
                        vylepseni.add(checkBox);
                    }
                    jeVylepsenitelny = true;
                } else if(el.isSelected() && (produkt[0].compareToIgnoreCase("zahradni salat") == 0) ) {
                    for (int i = 0; i < vylepseniArray.size(); i++) {
                        if (vylepseniArray.get(i).getNazevVylepseni().compareToIgnoreCase("salat") == 0 || vylepseniArray.get(i).getNazevVylepseni().compareToIgnoreCase("okurky") == 0) {
                            JCheckBox checkBox = new JCheckBox();
                            boxVylepseni.add(checkBox);
                            checkBox.setText(vylepseniArray.get(i).getNazevVylepseni() + "," + vylepseniArray.get(i).getCenaVylepseni() + " kč");
                            vylepseni.add(checkBox);
                        }
                    }
                    jeVylepsenitelny = true;
                } else if(el.isSelected() && (produkt[0].compareToIgnoreCase("Twister") == 0 || produkt[0].compareToIgnoreCase("qurrito") == 0)) {
                    for (int i = 0; i < vylepseniArray.size(); i++) {
                        if (vylepseniArray.get(i).getNazevVylepseni().compareToIgnoreCase("slanina") == 0 || vylepseniArray.get(i).getNazevVylepseni().compareToIgnoreCase("salat") == 0) {
                            JCheckBox checkBox = new JCheckBox();
                            boxVylepseni.add(checkBox);
                            checkBox.setText(vylepseniArray.get(i).getNazevVylepseni() + "," + vylepseniArray.get(i).getCenaVylepseni() + " kč");
                            vylepseni.add(checkBox);
                        }
                    }
                    jeVylepsenitelny = true;
                }
                if (jeVylepsenitelny) {
                    break;
                }
            }
        }
        private void vypisProdukty() {
            informaceOProduktech.setText("");
            for (VylepsitelnyProdukt e : objednavka.getProdukty()) {
                    informaceOProduktech.setText(informaceOProduktech.getText() + "\n " + e.getNazevProduktu() + " " + e.getCenaProduktu() + " kč");
                    for (Vylepseni el : e.getVylepseni()) {
                        informaceOProduktech.setText(informaceOProduktech.getText() + "\n    +" + el.getNazevVylepseni() + " " + el.getCenaVylepseni() + " kč");
                    }
            }
        }
        private void vypisMenu() {
            informaceOMenu.setText("");
            for (VylepsitelnyProdukt e : noveMenu.getProdukty()) {
                informaceOMenu.setText(informaceOMenu.getText() + "\n " + e.getNazevProduktu() + " " + e.getCenaProduktu() + " kč");
                for (Vylepseni el : e.getVylepseni()) {
                    informaceOMenu.setText(informaceOMenu.getText() + "\n    +" + el.getNazevVylepseni() + " " + el.getCenaVylepseni() + " kč");
                }
            }
        }
        private void vypisPridaneMenu() {
            informaceOPridanemMenu.setText("Přidané menu: ");
            MenuProduktu menu = objednavka.getMenu();
            for (VylepsitelnyProdukt e : menu.getProdukty()) {
                informaceOPridanemMenu.setText(informaceOPridanemMenu.getText() + "\n   " + e.getNazevProduktu() + " " + e.getCenaProduktu() + " kč");
                for (Vylepseni el : e.getVylepseni()) {
                    informaceOPridanemMenu.setText(informaceOPridanemMenu.getText() + "\n    +" + el.getNazevVylepseni() + " " + el.getCenaVylepseni() + " kč");
                }
            }
            informaceOPridanemMenu.setText(informaceOPridanemMenu.getText() + "\nCelková cena menu: " + menu.calculateCena() + " kč");
            vypisCelkovouCenuObjednavky();
        }
        private void vypisCelkovouCenuObjednavky() {
            celkovaCenaObjednavkyLabel.setText("Celková cena objednávky: " + objednavka.calculateCena() + " kč");
        }
        private void vypisCelkovouCenuMenu() {
            celkovaCenaMenuLabel.setText("Celková cena menu: " + noveMenu.calculateCena() + " kč");
        }
        private String[] regexProduktu(String produkt) {
            produkt = produkt.replaceFirst("kč$", "");
            String[] novy = produkt.split(",");
            novy[1] = novy[1].replaceAll(" ", "");
            return novy;       
        }

        public static MinorGui getMinorGUi() {
            if (gui == null) {
                gui = new MinorGui();
            }
            return gui;
        }
    }
