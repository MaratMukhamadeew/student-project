package edu.javacourse.studentorder.validators;

import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.domain.register.AnswerCityRegister;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.register.AnswerCityRegisterItem;
import edu.javacourse.studentorder.domain.register.CityRegisterResponse;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.exception.TransportException;
import edu.javacourse.studentorder.validators.register.CityRegisterChecker;
import edu.javacourse.studentorder.validators.register.FakeCityRegisterChecker;

public class CityRegisterValidator {
    public static final String IN_CODE = "NO_GRN";

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder so) {
        AnswerCityRegister acr = new AnswerCityRegister();
        acr.addItem(checkPerson(so.getHusband()));
        acr.addItem(checkPerson(so.getWife()));
        for (Child child : so.getChildren()) {
            acr.addItem(checkPerson(child));
        }

        return acr;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {
        AnswerCityRegisterItem.CityStatus status;
        AnswerCityRegisterItem.CityError error = null;
        try {
            CityRegisterResponse cans = personChecker.checkPerson(person);
            status = cans.isExisting() ? AnswerCityRegisterItem.CityStatus.YES : AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException e) {
            e.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(e.getCode(),e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE,e.getMessage());
        }

        return new AnswerCityRegisterItem(status, person, error);
    }
}
