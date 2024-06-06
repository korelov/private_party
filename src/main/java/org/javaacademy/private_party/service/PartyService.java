package org.javaacademy.private_party.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.private_party.dto.GuestDto;
import org.javaacademy.private_party.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;

    public void addGuest(String userName, String password, GuestDto dto) throws SQLException {
        partyRepository.addGuest(userName, password, dto);
    }

    public List<GuestDto> allGuests(String userName, String password) throws SQLException {
        return partyRepository.allGuests(userName, password);
    }
}
