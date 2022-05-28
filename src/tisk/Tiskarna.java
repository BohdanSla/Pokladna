package tisk;

public class Tiskarna {
    private Tiskarna instance;

    private Tiskarna() {};

    public Tiskarna getTiskarna() {
        if (instance == null) {
            instance = new Tiskarna();
        }

        return instance;
    };
}
