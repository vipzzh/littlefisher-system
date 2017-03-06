package com.yc.room1000.jyn.cancellease.service.impl;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

import com.yc.room1000.core.activiti.IProcessAction;
import com.yc.room1000.core.utils.Room1000Logger;

/**
 * 
 * Description: 
 *  
 * Created on 2017年1月22日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class RentalAccountServiceImpl implements IProcessAction {

    /**
     * logger
     */
    private static Room1000Logger logger = Room1000Logger.getLogger(ApplyToPayServiceImpl.class);
    
    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.debug("租务核算");
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // TODO Auto-generated method stub

    }

}