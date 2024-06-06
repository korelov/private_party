package org.javaacademy.private_party.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.private_party.dto.GuestDto;
import org.javaacademy.private_party.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    @PostMapping("/add-guest")
    public ResponseEntity<?> addGuest(@RequestHeader String userName,
                                      @RequestHeader String password,
                                      @RequestBody GuestDto dto) {
        try {
            partyService.addGuest(userName, password, dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/all-guests")
    public ResponseEntity<List<GuestDto>> allGuests(@RequestHeader String userName,
                                                    @RequestHeader String password) throws SQLException {
        return ResponseEntity.ok(partyService.allGuests(userName, password));
    }
}
