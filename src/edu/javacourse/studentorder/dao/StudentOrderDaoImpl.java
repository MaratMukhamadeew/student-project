package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrderDaoImpl implements StudentOrderDao {

    private static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, " +
            "h_date_of_birth, h_passport_series, h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
            "h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, " +
            "w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_series, w_passport_number, " +
            "w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension, " +
            "w_apartment, w_university_id, w_student_number, certificate_id, register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?);";

    private static final String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, " +
            "c_certificate_number, c_certificate_date, c_register_office_id, c_post_index, c_street_code, " +
            "c_building, c_extension, c_apartment)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String SELECT_ORDERS =
            "SELECT * FROM jc_student_order WHERE student_order_status = 0 ORDER BY student_order_date";

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
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            connection.setAutoCommit(false);
            try {
                // Header
                statement.setInt(1, StudentOrderStatus.START.ordinal());
                statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                // Husband
                setParamsForAdult(statement, 3, so.getHusband());
                // Wife
                setParamsForAdult(statement, 18, so.getWife());

                // Marriage
                statement.setString(33, so.getMarriagesCertificateID());
                statement.setLong(34, so.getMarriagesOffice().getOfficeId());
                statement.setDate(35, java.sql.Date.valueOf(so.getMarriagesDate()));

                statement.executeUpdate();
                ResultSet gKeysRs = statement.getGeneratedKeys();
                if (gKeysRs.next()) {
                    result = gKeysRs.getLong(1);
                }

                saveChildren(connection, so, result);
                connection.commit();

            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        List<StudentOrder> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentOrder so = new StudentOrder();
                fillStudentOrder(resultSet, so);

                Adult husband = fillAdult(resultSet, "h_");
                Adult wife = fillAdult(resultSet, "w_");
                so.setHusband(husband);
                so.setWife(wife);

                fillMarriage(resultSet, so);

                result.add(so);
            }

            resultSet.close();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    private void fillStudentOrder(ResultSet resultSet, StudentOrder so) throws SQLException {
        so.setStudentOrderID(resultSet.getLong("student_order_id"));
        so.setStudentOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));
    }

    private Adult fillAdult(ResultSet resultSet, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(resultSet.getString(pref + "sur_name"));
        adult.setGivenName(resultSet.getString(pref + "given_name"));
        adult.setPatronymic(resultSet.getString(pref + "patronymic"));
        adult.setDayOfBirth(resultSet.getDate(pref + "date_of_birth").toLocalDate());
        adult.setPassportSeries(resultSet.getString(pref + "passport_series"));
        adult.setPassportNumber(resultSet.getString(pref + "passport_number"));
        adult.setIssueDate(resultSet.getDate(pref + "passport_date").toLocalDate());

        PassportOffice po = new PassportOffice(resultSet.getLong(pref + "passport_office_id"), "", "");
        adult.setIssueDepartment(po);

        Address address = new Address();
        Street street = new Street(resultSet.getLong(pref + "street_code"), "");
        address.setStreet(street);
        address.setPostCode(resultSet.getString(pref + "post_index"));
        address.setBuilding(resultSet.getString(pref + "building"));
        address.setExtension(resultSet.getString(pref + "extension"));
        address.setApartment(resultSet.getString(pref + "apartment"));
        adult.setAddress(address);

        University university = new University(resultSet.getLong(pref + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentID(resultSet.getString(pref + "student_number"));

        return adult;
    }
    private void fillMarriage(ResultSet resultSet, StudentOrder so) throws SQLException {
        so.setMarriagesCertificateID(resultSet.getString("certificate_id"));
        so.setMarriagesDate(resultSet.getDate("marriage_date").toLocalDate());

        long roId = resultSet.getLong("register_office_id");
        RegisterOffice ro = new RegisterOffice(roId, "", "");
        so.setMarriagesOffice(ro);
    }


    private void saveChildren(Connection connection, StudentOrder so, Long soId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                statement.setLong(1, soId);
                setParamsForChild(statement, child);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private static void setParamsForAdult(PreparedStatement statement, int start, Adult adult) throws SQLException {
        setParamsForPerson(statement, start, adult);
        statement.setString(start + 4, adult.getPassportSeries());
        statement.setString(start + 5, adult.getPassportNumber());
        statement.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        statement.setLong(start + 7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(statement, start, adult);
        statement.setLong(start + 13, adult.getUniversity().getUniversityId());
        statement.setString(start + 14, adult.getStudentID());
    }

    private void setParamsForChild(PreparedStatement statement, Child child) throws SQLException {
        setParamsForPerson(statement, 2, child);
        statement.setString(6, child.getCertificateNumber());
        statement.setDate(7, Date.valueOf(child.getIssueDate()));
        statement.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(statement, 1, child);
    }

    private static void setParamsForPerson(PreparedStatement statement, int start, Person person) throws SQLException {
        statement.setString(start, person.getSurName());
        statement.setString(start + 1, person.getGivenName());
        statement.setString(start + 2, person.getPatronymic());
        statement.setDate(start + 3, Date.valueOf(person.getDayOfBirth()));
    }

    private static void setParamsForAddress(PreparedStatement statement, int start, Person person) throws SQLException {
        Address h_address = person.getAddress();
        statement.setString(start + 8, h_address.getPostCode());
        statement.setLong(start + 9, h_address.getStreet().getStreetCode());
        statement.setString(start + 10, h_address.getBuilding());
        statement.setString(start + 11, h_address.getExtension());
        statement.setString(start + 12, h_address.getApartment());
    }


}
