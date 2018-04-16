package services;
import services.BaseService;
import models.tables.Log;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import play.db.jpa.JPA;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.sql.Timestamp;
import play.Logger;

@Singleton
public class AppLogger extends BaseService implements BaseLogger {

        @Inject
        private AppLogger() {}

        public void log(String message) {
                Log log = new Log();
                log.setLogging_time(System.currentTimeMillis());
                log.setDescription(message);
                String s = String.valueOf(log.getLogging_time());
                Logger.debug(s);

                this.getSession().save(log);
            }
}