package com.mindex.challenge.data;

import java.util.Calendar;

public class Compensation {
    private Employee employee;
    private int salary;
    private Calendar effectiveDate;

    public Compensation(){

    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Calendar getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(Calendar effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
