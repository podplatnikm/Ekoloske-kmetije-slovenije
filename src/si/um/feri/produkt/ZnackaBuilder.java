package si.um.feri.produkt;

public class ZnackaBuilder {
    private String oznaka;

    public ZnackaBuilder setOznaka(String oznaka) {
        this.oznaka = oznaka;
        return this;
    }

    public Znacka createZnacka() {
        return new Znacka(oznaka);
    }
}