package poc.mfts.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.mfts.entity.Person;

/**
 *
 * @author subhadip.chatterjee@tcs.com
 */
@Stateless
public class PostOrderSessionBean {

    @PersistenceContext
    private EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param person
     */
    public void save(Person person) {        
        if (logger.isInfoEnabled()) {
            logger.info("save() is beginning...");
        }

        em.persist(person);
        if (logger.isInfoEnabled()) {
            logger.info("RECORD is saved");
        }                
    }
}