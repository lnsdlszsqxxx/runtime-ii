package com.ascending.training.util;

import java.util.Properties;
import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    public class HibernateUtil {
        private static SessionFactory sessionFactory;
        private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
        /* Define JVM options
        -Ddatabase.driver=org.postgresql.Driver
        -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
        -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db
        -Ddatabase.user=admin
        -Ddatabase.password=Training123!
        */
        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    String[] modelPackages = {"com.ascending.training.model"}; //package name for mapping classes
                    String dbDriver = System.getProperty("database.driver"); //get configuration info from VM option
                    String dbDialect = System.getProperty("database.dialect");
                    String dbUrl = System.getProperty("database.url");
                    String dbUser = System.getProperty("database.user");
                    String dbPassword = System.getProperty("database.password");

                    Configuration configuration = new Configuration(); //generate configuration file, i.e. hibernate.cfg.xml
                    Properties settings = new Properties();
                    settings.put(Environment.DRIVER, dbDriver);
                    settings.put(Environment.DIALECT, dbDialect);
                    settings.put(Environment.URL, dbUrl);
                    settings.put(Environment.USER, dbUser);
                    settings.put(Environment.PASS, dbPassword);
                    settings.put(Environment.SHOW_SQL, "true");
                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
      //              settings.put(Environment.HBM2DDL_AUTO,true); //check
                    configuration.setProperties(settings);

                    EntityScanner.scanPackages(modelPackages).addTo(configuration); //scan mapping class and change configuration file accordingly
                                                                                    //so we need to add fluent-hibernate-core in pom.xml

                    //build sessionFactory
                    StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                    ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                }
                catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
            return sessionFactory;
        }
    }

