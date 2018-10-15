package si.um.feri.produkt;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;

/**
 * Created by Miha on 30/05/2017.
 */
@Stateless
public class ZnackaDao implements ZnackaVmesnik{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean shrani(Znacka novaZnacka) throws SQLException {
        try {
            entityManager.persist(novaZnacka);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
