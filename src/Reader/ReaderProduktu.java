package Reader;
import java.io.*;
import java.util.ArrayList;

import produkty.Produkt;

public class ReaderProduktu implements Reader<Produkt> {
    private BufferedReader reader;
    private String line = "", cestaProduktu = "C:\\Users\\Bohdan Slawisch\\Desktop\\programování škola\\JAVA\\projekty\\2.D\\Pokladna\\src\\data\\produkty.csv";

    public ReaderProduktu() {
    }

    @Override
    public ArrayList<Produkt> nacti() {
        // TODO Auto-generated method stub
        ArrayList<Produkt> produkty = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(cestaProduktu));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String nazevProduktu = row[0];
                int cenaProduktu = Integer.parseInt(row[1]);
                produkty.add(new Produkt(nazevProduktu, cenaProduktu));
                    
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return produkty;
    }

}
