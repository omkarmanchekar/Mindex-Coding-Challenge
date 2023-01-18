package com.mindex.challenge.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.Calendar;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    
    private String compensationCreateUrl;
    private String compensationReadUrl;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationCreateUrl = "http://localhost:" + port + "/compensation/{id}";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {
        Employee testEmployee = new Employee();
        
        testEmployee.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Lennon");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Development Manager");


        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(2000);        
        testCompensation.setEffectiveDate(Calendar.getInstance().getTime());

        Compensation resultCompensation = new Compensation();
        resultCompensation.setSalary(testCompensation.getSalary());
        resultCompensation.setEffectiveDate(testCompensation.getEffectiveDate());
        resultCompensation.setEmployee(testEmployee);
    
        // Create Compensation
        Compensation createdCompensation = restTemplate.postForEntity(compensationCreateUrl, testCompensation, Compensation.class,testEmployee.getEmployeeId()).getBody();
        assertNotNull(createdCompensation.getEmployee().getEmployeeId());
        assertCompensationEquivalence(resultCompensation,createdCompensation);

        // Read Compensation
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertCompensationEquivalence(createdCompensation, readCompensation);

    }
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
        assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
        assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
        assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
