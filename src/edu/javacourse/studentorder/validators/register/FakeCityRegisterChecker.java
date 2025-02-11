package edu.javacourse.studentorder.validators.register;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.register.CityRegisterResponse;
import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.exception.TransportException;

import java.util.Objects;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    private static final String GOOD_1 = "10";
    private static final String GOOD_2 = "20";
    private static final String BAD_1 = "11";
    private static final String BAD_2 = "21";
    private static final String ERROR_1 = "12";
    private static final String ERROR_2 = "22";
    private static final String ERROR_T_1 = "13";
    private static final String ERROR_T_2 = "23";
    @Override
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        CityRegisterResponse res = new CityRegisterResponse();
        if (person instanceof Adult adult) {
            if(Objects.equals(adult.getPassportSeries(), GOOD_1) || Objects.equals(adult.getPassportSeries(), GOOD_2)) {
                System.out.println("Всё хорошо с родителем");
                res.setExisting(true);
                res.setTemporal(false);
            }
            if(Objects.equals(adult.getPassportSeries(), BAD_1) || Objects.equals(adult.getPassportSeries(), BAD_2)) {
                System.out.println("Всё не очень хорошо с родителем");
                res.setExisting(false);
            }
            if(Objects.equals(adult.getPassportSeries(), ERROR_1) || Objects.equals(adult.getPassportSeries(), ERROR_2)) {
                System.out.println("Всё плохо с родителем");
                throw new CityRegisterException("code 1", "GRN ERROR " + adult.getPassportSeries());
            }
            if(Objects.equals(adult.getPassportSeries(), ERROR_T_1) || Objects.equals(adult.getPassportSeries(), ERROR_T_2)) {
                System.out.println("Всё плохо с родителем");
                throw new TransportException("Transport ERROR " + adult.getPassportSeries());
            }
        }
        if (person instanceof Child child) {
            System.out.println("Всё хорошо с ребенком");
            res.setExisting(true);
            res.setTemporal(true);
        }
        return res;
    }
}
