package com.ascending.training.Interceptor;


import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import java.util.Arrays;


public class HibernateInterceptor extends EmptyInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types){
        logger.info(">>>>>>>onload: "+entity.toString());
        logger.info(">>>>>>>onload: "+id);
        logger.info(">>>>>>>onload, state is the value of each column: "+Arrays.deepToString(state));
        logger.info(">>>>>>>onload, property is column names: "+ Arrays.deepToString(propertyNames));
        logger.info(">>>>>>>onload, type is the data type of column variable: "+ Arrays.deepToString(types));

        return false; //when the data is not changed
//        return true; //when the data is changed
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] curruntState, Object[] previousState, String[] propertyName, Type[] types){

        return false;
    }

}
