package models;
import java.util.Date;

import enums.Gender;

public abstract class Person implements Runnable {
    private String name;
    private String ssn;
    private Date birth_Date;
    private Gender gender;

    /**
     * @param name
     * @param ssn
     * @param birth_Date
     * @param gender
     */
    public Person(String name, String ssn, Date birth_Date, Gender gender) {
        this.name = name;
        this.ssn = ssn;
        this.birth_Date = birth_Date;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getBirth_Date() {
        return birth_Date;
    }

    public void setBirth_Date(Date birth_Date) {
        this.birth_Date = birth_Date;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
