package si.um.feri.produkt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;
import si.um.feri.uporabnik.Uporabnik;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
@Indexed
@AnalyzerDef(name = "analizatorpomeri",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "Russian")
                })
        })
public class Produkt implements Serializable{

    @Id
    @GeneratedValue (strategy=GenerationType.AUTO)
    private long id;

    @Field(index= Index.YES, analyze= Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String naziv;

    private double cena;

    private int kolicina;

    private Calendar datumKreacije;

    @Transient
    private String nizOznak;


    @Field(index= Index.YES, analyze= Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String opis;

    @Field(index= Index.YES, analyze= Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String kategorija;

    @IndexedEmbedded
    @ManyToOne (cascade = {CascadeType.ALL})
    private Uporabnik kmetija;

    @IndexedEmbedded
    @OneToMany(orphanRemoval=true)
    private List<Znacka> znacke;

    public Produkt(ProduktBuilder produktBuilder) {
        this.naziv = produktBuilder.getNaziv();
        this.cena = produktBuilder.getCena();
        this.kolicina = produktBuilder.getKolicina();
        this.opis = produktBuilder.getOpis();
        this.kategorija = produktBuilder.getKategorija();
        this.kmetija = produktBuilder.getKmetija();
        this.znacke = produktBuilder.getZnacke();
        this.datumKreacije = produktBuilder.getDatumPridruzitve();
        this.nizOznak = produktBuilder.getNizOznak();
    }

    public Produkt(){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }


    public Uporabnik getKmetija() {
        return kmetija;
    }

    public void setKmetija(Uporabnik kmetija) {
        this.kmetija = kmetija;
    }

    public List<Znacka> getZnacke() {
        return znacke;
    }

    public void setZnacke(List<Znacka> znacke) {
        this.znacke = znacke;
    }

    @Override
    public String toString() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(this);
    }

    public Calendar getDatumKreacije() {
        return datumKreacije;
    }

    public void setDatumKreacije(Calendar datumKreacije) {
        this.datumKreacije = datumKreacije;
    }

    public String getNizOznak() {
        return nizOznak;
    }

    public void setNizOznak(String nizOznak) {
        this.nizOznak = nizOznak;
    }

    public String formatirajZnacke(){
        StringBuilder s = new StringBuilder();
        for(Znacka z : znacke){
            s.append("#"+z.getOznaka()+", ");
        }
        return s.toString();
    }
}