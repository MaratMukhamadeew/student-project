package edu.javacourse.studentorder.validators;

import edu.javacourse.studentorder.domain.children.AnswerChildren;
import edu.javacourse.studentorder.domain.StudentOrder;

public class ChildrenValidator {
    public AnswerChildren checkChildren(StudentOrder so) {
        AnswerChildren ac = new AnswerChildren();
        System.out.println("Children запущен");
        return ac;
    }
}
