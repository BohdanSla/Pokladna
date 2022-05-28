package Reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import produkty.Produkt;

public class ReaderPriloh implements Reader<Produkt> {
    private BufferedReader reader;
    private String line = "", cestaPriloh = "C:\\Users\\Bohdan Slawisch\\Desktop\\programování škola\\JAVA\\projekty\\2.D\\Pokladna\\src\\data\\prilohy.csv";

    public ReaderPriloh() {
    }

    @Override
    public ArrayList<Produkt> nacti() {
        // TODO Auto-generated method stub
        ArrayList<Produkt> prilohy = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(cestaPriloh));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String nazevPrilohy = row[0];
                int cenaPrilohy = Integer.parseInt(row[1]);
                prilohy.add(new Produkt(nazevPrilohy, cenaPrilohy));
                    
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
        return prilohy;
    }
}
