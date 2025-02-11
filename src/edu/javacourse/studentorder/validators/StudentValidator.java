package edu.javacourse.studentorder.validators;

import edu.javacourse.studentorder.domain.student.AnswerStudent;
import edu.javacourse.studentorder.domain.StudentOrder;

public class StudentValidator {
    public AnswerStudent checkStudent(StudentOrder so) {
        AnswerStudent as = new AnswerStudent();
        System.out.println("Student запущен");
        return as;
    }
}
