package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService{
    
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating new compensation [{}]", compensation);
        
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setDepartment(compensation.getEmployee().getDepartment());
        employee.setFirstName(compensation.getEmployee().getFirstName());
        employee.setLastName(compensation.getEmployee().getLastName());
        employee.setPosition(compensation.getEmployee().getPosition());
        employee.setDirectReports(compensation.getEmployee().getDirectReports());
        
        compensation.setEmployee(employee);
        compensationRepository.insert(compensation);
        
        return compensation;
    }

    // POST body will be in the following structure :
    // {
    //     "employee": {"firstName" : "Omkar",
    //                  "lastName" : "Manchekar",
    //                  "position" : "Developer II",
    //                  "department" : "Engineering"},
    //     "salary":1000,
    //     "effectiveDate":"2022-10-01"
    // }


    @Override
    public Compensation read(String id) {
        LOG.debug("Creating request to read compensation with id [{}]", id);
        
        // Finds all the compensations Instances from database and stores in List
        // Iterating over all the compensationAllList we find the required compensation whose compensation.employeeId matches to the given id 
        List<Compensation> compensationAllList = compensationRepository.findBy(id);

        if (compensationAllList == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }


        for(Compensation compensation:compensationAllList){
            if(compensation.getEmployee().getEmployeeId().equals(id)){
                return compensation;
            }
        }

        
        // Will return null compensation if no compensation.employeeId is found in database
        Compensation null_compensation = new Compensation();
        return null_compensation;
    }
}
