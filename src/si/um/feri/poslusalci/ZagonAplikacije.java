package si.um.feri.poslusalci;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Miha on 31/05/2017.
 */
public class ZagonAplikacije implements SystemEventListener{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
        try {



            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isListenerForSource(Object o) {
        return false;
    }


}

