package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class CompensationServiceImpl implements CompensationService{
    
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(String id, Compensation compensation) {
        LOG.debug("Creating new compensation [{}]", compensation);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if(employee == null){
            throw new RuntimeException("Invalid employee id" + id);
        }
        
        compensation.setEmployee(employee);
        compensationRepository.insert(compensation);
        
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating request to read compensation with id [{}]", id);
       
        Compensation comp = compensationRepository.findByEmployeeEmployeeId(id);

        if (comp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        
        return comp;
    }
}
