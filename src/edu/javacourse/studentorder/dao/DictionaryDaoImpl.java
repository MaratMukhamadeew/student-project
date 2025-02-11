package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.domain.Street;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/jc_student";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String GET_STREET = "SELECT street_code, street_name FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STREET)) {

            statement.setString(1, "%" + pattern + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new Street(resultSet.getLong("street_code"), resultSet.getString("street_name")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
