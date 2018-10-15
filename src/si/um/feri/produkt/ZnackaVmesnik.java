package si.um.feri.produkt;

import javax.ejb.Local;
import java.sql.SQLException;

/**
 * Created by Miha on 30/05/2017.
 */
@Local
public interface ZnackaVmesnik {

    boolean shrani(Znacka novaZnacka) throws SQLException;
}
