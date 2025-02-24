package edu.javacourse.studentorder.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrder {
    private long studentOrderID;
    private StudentOrderStatus studentOrderStatus;
    private LocalDateTime studentOrderDate;
    private Adult husband;
    private Adult wife;
    private List <Child> children;
    private String MarriagesCertificateID;
    private LocalDate MarriagesDate;
    private RegisterOffice MarriagesOffice;

    public long getStudentOrderID() {
        return studentOrderID;
    }

    public void setStudentOrderID(long studentOrderID) {
        this.studentOrderID = studentOrderID;
    }

    public StudentOrderStatus getStudentOrderStatus() {
        return studentOrderStatus;
    }

    public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
        this.studentOrderStatus = studentOrderStatus;
    }

    public LocalDateTime getStudentOrderDate() {
        return studentOrderDate;
    }

    public void setStudentOrderDate(LocalDateTime studentOrderDate) {
        this.studentOrderDate = studentOrderDate;
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

    public RegisterOffice getMarriagesOffice() {
        return MarriagesOffice;
    }

    public void setMarriagesOffice(RegisterOffice marriagesOffice) {
        MarriagesOffice = marriagesOffice;
    }
}
