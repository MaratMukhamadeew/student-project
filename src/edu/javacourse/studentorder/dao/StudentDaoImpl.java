package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.Address;
import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.domain.StudentOrderStatus;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

public class StudentDaoImpl implements StudentOrderDao {

    public static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, " +
            "h_date_of_birth, h_passport_series, h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
            "h_street_code, h_building, h_extension, h_apartment, w_sur_name, w_given_name, w_patronymic, w_date_of_birth, " +
            "w_passport_series, w_passport_number, w_passport_date, w_passport_office_id, w_post_index, w_street_code, " +
            "w_building, w_extension, w_apartment, certificate_id, register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    //TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD)
        );
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {

        long result = -1L;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"})) {

            // Header
            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            // Husband
            setParamsForAdult(statement, 3, so.getHusband());

            // Wife
            setParamsForAdult(statement,16, so.getWife());

            // Marriage
            statement.setString(29,so.getMarriagesCertificateID());
            statement.setLong(30, so.getMarriagesOffice().getOfficeId());
            statement.setDate(31, java.sql.Date.valueOf(so.getMarriagesDate()));

            statement.executeUpdate();
            ResultSet gKeysRs = statement.getGeneratedKeys();
            if (gKeysRs.next()) {
                result = gKeysRs.getLong(1);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private static void setParamsForAdult(PreparedStatement statement, int start, Adult adult) throws SQLException {
        statement.setString(start, adult.getSurName());
        statement.setString(start + 1, adult.getGivenName());
        statement.setString(start + 2, adult.getPatronymic());
        statement.setDate(start + 3, Date.valueOf(adult.getDayOfBirth()));
        statement.setString(start + 4, adult.getPassportSeries());
        statement.setString(start + 5, adult.getPassportNumber());
        statement.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        statement.setLong(start + 7, adult.getIssueDepartment().getOfficeId());
        Address h_address = adult.getAddress();
        statement.setString(start + 8, h_address.getPostCode());
        statement.setLong(start + 9, h_address.getStreet().getStreetCode());
        statement.setString(start + 10, h_address.getBuilding());
        statement.setString(start + 11, h_address.getExtension());
        statement.setString(start + 12, h_address.getApartment());
    }
}
