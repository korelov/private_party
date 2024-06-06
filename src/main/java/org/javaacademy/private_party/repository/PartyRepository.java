package org.javaacademy.private_party.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.private_party.dto.GuestDto;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PartyRepository {
    private static final String JDBC_POSTGRESQL_LOCALHOST_5432_PARTY =
            "jdbc:postgresql://localhost:5432/party";

    private Connection connectBd(String userName, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL driver not found.", e);
        }
        return DriverManager.getConnection(JDBC_POSTGRESQL_LOCALHOST_5432_PARTY, userName, password);
    }

    public void addGuest(String userName, String password, GuestDto dto) throws SQLException {
        String sql = "INSERT INTO guests (name, email, phone) VALUES (?, ?, ?)";
        try (Connection connection = connectBd(userName, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dto.getName());
            preparedStatement.setString(2, dto.getEmail());
            preparedStatement.setString(3, dto.getPhone());
            preparedStatement.executeUpdate();
        }
    }

    public List<GuestDto> allGuests(String userName, String password) throws SQLException {
        String sql = "select * from guest_name";
        if ("manager".equals(userName)) {
            sql = "select * from guests";
        }
        List<GuestDto> guestDtoList = new ArrayList<>();
        try (Connection connection = connectBd(userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                GuestDto guestDto = new GuestDto();
                guestDto.setName(resultSet.getString("name"));
                if ("manager".equals(userName)) {
                    guestDto.setEmail(resultSet.getString("email"));
                    guestDto.setPhone(resultSet.getString("phone"));
                }
                guestDtoList.add(guestDto);
            }
        }
        return guestDtoList;
    }
}
