package si.um.feri.produkt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Miha on 30/05/2017.
 */

@Entity
public class Znacka implements Serializable{


    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private long id;

    @Field(index= Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String oznaka;

    public Znacka() {
    }

    public Znacka(String oznaka) {
        this.oznaka = oznaka;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    @Override
    public String toString() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(this);
    }
}
