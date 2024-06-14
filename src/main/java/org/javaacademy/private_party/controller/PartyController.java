package org.javaacademy.private_party.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.private_party.dto.GuestDtoRq;
import org.javaacademy.private_party.service.PartyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    @PostMapping("/add-guest")
    public void addGuest(@RequestHeader String userName,
                         @RequestHeader String password,
                         @RequestBody GuestDtoRq dtoRq) {
        partyService.addGuest(userName, password, dtoRq);
    }

    @GetMapping("/all-guests")
    public List<String> allGuests(@RequestHeader String userName,
                                  @RequestHeader String password) {
        return partyService.getGuests(userName, password);
    }
}
