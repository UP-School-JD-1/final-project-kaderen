package models.employee;
import java.util.Date;

import enums.Gender;
import models.Person;

public abstract class Employee extends Person {
    private Date startDateOfWork;
    private int salary;

    /**
     * @param name
     * @param ssn
     * @param birth_Date
     * @param gender
     * @param startDateOfWork
     * @param salary
     */
    public Employee(String name, String ssn, Date birth_Date, Gender gender, Date startDateOfWork, int salary) {
        super(name, ssn, birth_Date, gender);
        this.startDateOfWork = startDateOfWork;
        this.salary = salary;
    }

    public Date getStartDateOfWork() {
        return startDateOfWork;
    }

    public void setStartDateOfWork(Date startDateOfWork) {
        this.startDateOfWork = startDateOfWork;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

}