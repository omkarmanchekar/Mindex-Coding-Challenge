package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
// import com.mindex.challenge.dao.ReportingRepository;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Vector;


@Service
public class ReportingServiceImlp implements ReportingService{
    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImlp.class);


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating report read request for id [{}]", id);
        
        // Used same employeeRepository for reportingStructure 
        Employee employee = employeeRepository.findByEmployeeId(id);
        ReportingStructure report = new ReportingStructure();

        if (employee == null) {
            throw new RuntimeException("Invalid report employeeId: " + id);
        }




        // Used BFS to find total distinct reports for a particular employeeId
        // The below logic will also handel loops in reporting structure 

        Vector<String> queue = new Vector<String>();
        queue.add(employee.getEmployeeId());
        
        Vector<String> visited = new Vector<String>();
        
        
        while(queue.size() != 0 ){
            String top = queue.remove(0);
            visited.add(top);

            Employee nextEmployee = employeeRepository.findByEmployeeId(top);
            
            if (nextEmployee.getDirectReports() != null) {
                for (Employee e: nextEmployee.getDirectReports()) {
                    if(!visited.contains(e.getEmployeeId())){
                        queue.add(e.getEmployeeId());
                    }
                }
            }


        }

        
        // Total distinct reports will be the number of employee visited -1, (-1) will remove the source employee 
        int total_directed_reports = visited.size()-1;


        report.setEmployee(employee);
        report.setNumberOfReports(total_directed_reports);

        return report;
    }

}

