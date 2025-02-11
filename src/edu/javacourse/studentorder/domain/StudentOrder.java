package edu.javacourse.studentorder.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentOrder {
    private long studentOrderID;
    private Adult husband;
    private Adult wife;
    private List <Child> children;
    private String MarriagesCertificateID;
    private LocalDate MarriagesDate;
    private String MarriagesOffice;

    public long getStudentOrderID() {
        return studentOrderID;
    }

    public void setStudentOrderID(long studentOrderID) {
        this.studentOrderID = studentOrderID;
    }

    public Adult getHusband() {
        return husband;
    }

    public void setHusband(Adult husband) {
        this.husband = husband;
    }

    public Adult getWife() {
        return wife;
    }

    public void setWife(Adult wife) {
        this.wife = wife;
    }

    public List <Child> getChildren() {
        return children;
    }

    public void addChildren(Child child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public String getMarriagesCertificateID() {
        return MarriagesCertificateID;
    }

    public void setMarriagesCertificateID(String marriagesCertificateID) {
        MarriagesCertificateID = marriagesCertificateID;
    }

    public LocalDate getMarriagesDate() {
        return MarriagesDate;
    }

    public void setMarriagesDate(LocalDate marriagesDate) {
        MarriagesDate = marriagesDate;
    }

    public String getMarriagesOffice() {
        return MarriagesOffice;
    }

    public void setMarriagesOffice(String marriagesOffice) {
        MarriagesOffice = marriagesOffice;
    }
}
