package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.BookingCreationDTO;
import org.example.model.entity.Booking;
import org.example.model.exceptions.BookingNotAvailableException;
import org.example.model.exceptions.BookingNotFoundException;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final BookingService bookingService;


    @GetMapping("/")
    public String user() {
        return "user";
    }

    @PostMapping("/make-booking")
    public String makeBooking(@ModelAttribute @Valid BookingCreationDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "error";
        }
        try {
            bookingService.book(dto);
        } catch (WorkSpaceNotFoundException e) {
            model.addAttribute("error", "WorkSpace with the id " + dto.getWorkSpaceId() + " does not exist");
            return "error";
        } catch (BookingNotAvailableException e) {
            model.addAttribute("error", "Booking with the chosen dates is not available");
            return "error";
        }
        return "redirect:/user/";
    }

    @PostMapping("/cancel-booking")
    public String cancelBooking(@RequestParam("id") @NotNull Long id, Model model) {
        try {
            bookingService.cancelBooking(id);
        } catch (BookingNotFoundException e) {
            model.addAttribute("error", "Booking not found");
            return "error";
        }
        return "redirect:/user/";
    }

    @GetMapping("/all-bookings")
    public String viewAllBookings(Model model) {
        List<Booking> bookings = bookingService.findAll();
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

}
