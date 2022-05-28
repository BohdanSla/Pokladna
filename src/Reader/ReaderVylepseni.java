package Reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Vylepseni.Vylepseni;

public class ReaderVylepseni implements Reader<Vylepseni> {
    private BufferedReader reader;
    private String line = "", cesta = "C:\\Users\\Bohdan Slawisch\\Desktop\\programování škola\\JAVA\\projekty\\2.D\\Pokladna\\src\\data\\vylepseni.csv";

    public ReaderVylepseni() {}

    @Override
    public ArrayList<Vylepseni> nacti() {
        ArrayList<Vylepseni> vylepseni = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(cesta));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String nazevVylepseni = row[0];
                int cenaVylepseni = Integer.parseInt(row[1]);
                vylepseni.add(new Vylepseni(nazevVylepseni, cenaVylepseni));
                    
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
        return vylepseni;
    }
    
}
