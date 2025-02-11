package edu.javacourse.studentorder.validators;

import edu.javacourse.studentorder.domain.wedding.AnswerWedding;
import edu.javacourse.studentorder.domain.StudentOrder;

public class WeddingValidator {
    public AnswerWedding checkWedding(StudentOrder so) {
        AnswerWedding aw = new AnswerWedding();
        System.out.println("Wedding запущен");
        return aw;
    }
}
