package org.javaacademy.private_party.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.private_party.entity.Guest;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PartyRepository {

    public void save(Connection connection, Guest guest) {
        String sql = """
                INSERT INTO guests (name, email, phone) VALUES (?, ?, ?)
                """;
        try (PreparedStatement preparedStatement = createPreparedStatement(sql, connection)) {
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setString(2, guest.getEmail());
            preparedStatement.setString(3, guest.getPhone());
            int count = preparedStatement.executeUpdate();
            if (count != 1) {
                throw new RuntimeException("Не вставлен гость в таблицу");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement createPreparedStatement(String sql, Connection connection) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllNames(Connection connection) {
        String sql = """
                select name from guest_name
                """;
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            ArrayList<String> names = new ArrayList<>();
            while (resultSet.next()) {
                names.add(resultSet.getString(1));
            }
            return names;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
