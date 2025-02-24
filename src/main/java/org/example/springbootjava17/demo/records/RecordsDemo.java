package org.example.springbootjava17.demo.records;

public class RecordsDemo {
    public static void main(String[] args) {
        EmployeeClass employeeClass = new EmployeeClass("Chris",100);
        System.out.println(employeeClass);
        EmployeeRecord employeeRecord = new EmployeeRecord("Peter",200);
        System.out.println(employeeRecord);
    }
}
