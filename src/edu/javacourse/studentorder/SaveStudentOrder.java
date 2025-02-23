package edu.javacourse.studentorder;

import edu.javacourse.studentorder.dao.DictionaryDaoImpl;
import edu.javacourse.studentorder.dao.StudentDaoImpl;
import edu.javacourse.studentorder.dao.StudentOrderDao;
import edu.javacourse.studentorder.domain.*;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
    public static void main(String[] args) throws Exception {
//        List<Street> streets = new DictionaryDaoImpl().findStreets("про");
//        for (Street street : streets) {
//            System.out.println(street.getStreetName());
//        }
//
//        List<PassportOffice> passportOffices = new DictionaryDaoImpl().findPassportOffices("010020000000");
//        for (PassportOffice passportOffice : passportOffices) {
//            System.out.println(passportOffice.getOfficeName());
//        }
//
//        List<RegisterOffice> registerOffices = new DictionaryDaoImpl().findPRegisterOffices("010010000000");
//        for (RegisterOffice registerOffice : registerOffices) {
//            System.out.println(registerOffice.getOfficeName());
//        }
//
//        List<CountryArea> countryAreas1 = new DictionaryDaoImpl().findAreas("020020020000");
//        for (CountryArea countryArea : countryAreas1) {
//            System.out.println(countryArea);
//        }

        StudentOrder studentOrder = buildStudentOrder(10);
        StudentOrderDao dao = new StudentDaoImpl();
        Long id = dao.saveStudentOrder(studentOrder);
        System.out.println(id);
    }

    public static long saveStudentOrder(StudentOrder so) {
        long ans = 123;
        System.out.println("saveStudentOrder 1");
        return ans;
    }

    static StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderID(id);
        so.setMarriagesCertificateID("" + (12345 + id));
        so.setMarriagesDate(LocalDate.of(2016,6,16));
        so.setMarriagesOffice(new RegisterOffice(1L, "", ""));

        Street street = new Street(1L, "First street");
        Address address = new Address("195000", street,"12","","142");

        // Муж
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич"
                , LocalDate.of(1997,8,24));
        husband.setPassportSeries("" + (10 + id));
        husband.setPassportNumber("" + (100 + id));
        husband.setIssueDate(LocalDate.of(2017,9,15));
        husband.setIssueDepartment(new PassportOffice(1L, "", ""));
        husband.setStudentID("" + (1000 + id));
        husband.setAddress(address);
        // Жена
        Adult wife = new Adult("Петрова", "Вика", "Андреевна"
                , LocalDate.of(1998,7,23));
        wife.setPassportSeries("" + (20 + id));
        wife.setPassportNumber("" + (200 + id));
        wife.setIssueDate(LocalDate.of(2017,9,15));
        wife.setIssueDepartment(new PassportOffice(2L, "", ""));
        wife.setStudentID("" + (2000 + id));
        wife.setAddress(address);
        // Дети
        Child child1 = new Child("Петрова", "Ирина", "Викторовна"
                , LocalDate.of(2018,6,22));
        child1.setCertificateNumber("" + (30 + id));
        child1.setIssueDate(LocalDate.of(2018,7,20));
        child1.setIssueDepartment(new RegisterOffice(2L, "", ""));
        child1.setAddress(address);

        Child child2 = new Child("Петров", "Евгений", "Викторович"
                , LocalDate.of(2018,6,22));
        child2.setCertificateNumber("" + (40 + id));
        child2.setIssueDate(LocalDate.of(2018,7,20));
        child2.setIssueDepartment(new RegisterOffice(3L, "", ""));
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChildren(child1);
        so.addChildren(child2);

        return so;
    }
}