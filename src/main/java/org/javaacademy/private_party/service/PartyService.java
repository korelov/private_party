package org.javaacademy.private_party.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.private_party.dto.GuestDtoRq;
import org.javaacademy.private_party.entity.Guest;
import org.javaacademy.private_party.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {
    @Value("${app.database.url}")
    private String databaseUrl;
    private final PartyRepository partyRepository;

    public void addGuest(String userName, String password, GuestDtoRq g) {
        Guest guest = convertToEntity(g);
        Connection connection = createConnection(userName, password);
        try {
            partyRepository.save(connection, guest);
        } finally {
            closeConnection(connection);
        }
    }

    private Connection createConnection(String userName, String password) {
        try {
            return DriverManager.getConnection(databaseUrl, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Guest convertToEntity(GuestDtoRq guestDtoRq) {
        Guest guest = new Guest();
        guest.setName(guestDtoRq.getName());
        guest.setEmail(guestDtoRq.getEmail());
        guest.setPhone(guestDtoRq.getPhone());
        return guest;
    }

    public List<String> getGuests(String userName, String password) {
        Connection connection = createConnection(userName, password);
        try {
            return partyRepository.getAllNames(connection);
        } finally {
            closeConnection(connection);
        }
    }
}
