package edu.javacourse.studentorder;

import edu.javacourse.studentorder.dao.DictionaryDaoImpl;
import edu.javacourse.studentorder.domain.Address;
import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.domain.Street;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
    public static void main(String[] args) throws Exception {
        List<Street> streets = new DictionaryDaoImpl().findStreets("sec");
        for (Street street : streets) {
            System.out.println(street.getStreetName());
        }
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
        so.setMarriagesOffice("Отдел ЗАГСа " + id);

        Street street = new Street(999L, "First street");
        Address address = new Address("195000", street,"15","","101");

        // Муж
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич"
                , LocalDate.of(1997,8,24));
        husband.setPassportSeries("" + (10 + id));
        husband.setPassportNumber("" + (100 + id));
        husband.setIssueDate(LocalDate.of(2017,9,15));
        husband.setIssueDepartment("Отдел милиции № "+ id);
        husband.setStudentID("" + (1000 + id));
        husband.setAddress(address);
        // Жена
        Adult wife = new Adult("Петрова", "Вика", "Андреевна"
                , LocalDate.of(1998,7,23));
        wife.setPassportSeries("" + (20 + id));
        wife.setPassportNumber("" + (200 + id));
        wife.setIssueDate(LocalDate.of(2017,9,15));
        wife.setIssueDepartment("Отдел милиции № "+ id);
        wife.setStudentID("" + (2000 + id));
        wife.setAddress(address);
        // Дети
        Child child1 = new Child("Петрова", "Ирина", "Викторовна"
                , LocalDate.of(2018,6,22));
        child1.setCertificateNumber("" + (30 + id));
        child1.setIssueDate(LocalDate.of(2018,7,20));
        child1.setIssueDepartment("Отдел ЗАГСа № "+ id);
        child1.setAddress(address);

        Child child2 = new Child("Петров", "Евгений", "Викторович"
                , LocalDate.of(2018,6,22));
        child2.setCertificateNumber("" + (40 + id));
        child2.setIssueDate(LocalDate.of(2018,7,20));
        child2.setIssueDepartment("Отдел ЗАГСа № "+ id);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChildren(child1);
        so.addChildren(child2);
        return so;
    }
}