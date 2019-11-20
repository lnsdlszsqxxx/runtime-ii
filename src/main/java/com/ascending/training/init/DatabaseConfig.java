package com.ascending.training.init;

import com.ascending.training.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    //use this been to invoke the Hibernate validate
    @Bean(name = "sessionFactory1")
    public SessionFactory getFactory1() throws Exception {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if(sf==null) throw new Exception("Building session factory exception");
        return sf;
    }

    @Bean(name = "sessionFactory2")
    public SessionFactory getFactory2() throws Exception {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if(sf==null) throw new Exception("Building session factory2 exception");
        return sf;
    }

}
