package com.mindex.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

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
public class ReportingServiceImplTest {
    private String reportingReadURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingReadURL = "http://localhost:" + port + "/reporting/{id}";
    }

    @Test
    public void testRead(){

        Employee employee = new Employee();
        employee.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");

        ReportingStructure testReportingStructure = new ReportingStructure();
        testReportingStructure.setNumberOfReports(4);
        testReportingStructure.setEmployee(employee);

        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingReadURL, ReportingStructure.class, testReportingStructure.getEmployee().getEmployeeId()).getBody();

        assertEquals(testReportingStructure.getEmployee().getEmployeeId(),readReportingStructure.getEmployee().getEmployeeId());
        assertEquals(testReportingStructure.getNumberOfReports(),readReportingStructure.getNumberOfReports());
    }
}
