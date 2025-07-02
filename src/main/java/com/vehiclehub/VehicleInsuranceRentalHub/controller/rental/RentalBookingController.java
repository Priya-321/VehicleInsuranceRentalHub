package com.vehiclehub.VehicleInsuranceRentalHub.controller.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.CompanyVehicleService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalBookingService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalCustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/rental-booking")
public class RentalBookingController {

    @Autowired
    private RentalBookingService bookingService;

    @Autowired
    private RentalCustomerService customerService;

    @Autowired
    private CompanyVehicleService vehicleService;

    @GetMapping("/list")
    public String listBookings(Model model) {
        try {
            List<RentalBooking> bookings = bookingService.getAllBookings();
            model.addAttribute("bookings", bookings);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading bookings: " + e.getMessage());
        }
        return "rental_booking/list";
    }


    @GetMapping("/search")
    public String searchBookings(@RequestParam("query") String query, Model model) {
        List<RentalBooking> results;

        try {
            // Search by ID if numeric
            if (query.matches("\\d+")) {
                RentalBooking booking = bookingService.getBookingById(Integer.parseInt(query));
                results = List.of(booking);
            } else {
                // Search by customer name
                results = bookingService.searchByCustomerName(query);
            }

            if (results.isEmpty()) {
                model.addAttribute("errorMessage", "No bookings found for: " + query);
            } else {
                model.addAttribute("bookings", results);
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "rental_booking/list";  // ensure this matches your template location
    }

    
    //STEP 4: Show booking form with customerId and vehicleId
    @GetMapping("/form")
    public String showBookingForm(@RequestParam("customerId") int customerId,
                                  @RequestParam("vehicleId") int vehicleId,
                                  Model model) {
        RentalCustomer customer = customerService.getCustomerById(customerId);
        CompanyVehicle vehicle = vehicleService.getVehicleById(vehicleId);

        RentalBooking booking = new RentalBooking();
        booking.setCustomer(customer);
        booking.setVehicle(vehicle);

        model.addAttribute("booking", booking);
        return "rental_booking/form";
    }

    //STEP 5: Save booking and update vehicle status
    @PostMapping("/save")
    public String saveBooking(@ModelAttribute("booking") @Valid RentalBooking booking,
                              BindingResult result,
                              Model model) {

        // Custom logic: Ensure bookingDate <= returnDate
        if (booking.getBookingDate() != null && booking.getReturnDate() != null) {
            if (booking.getReturnDate().isBefore(booking.getBookingDate())) {
                result.rejectValue("returnDate", "error.booking", "Return date must be after booking date.");
            }
        }

        if (result.hasErrors()) {
            // Re-fetch customer and vehicle to display them again
            model.addAttribute("booking", booking);
            return "rental_booking/form";
        }

        // Set vehicle status
        CompanyVehicle vehicle = vehicleService.getVehicleById(booking.getVehicle().getId());
        vehicle.setStatus("Booked");
        vehicleService.saveVehicle(vehicle);

        bookingService.saveBooking(booking);
        return "redirect:/rental-booking/list";
    }




    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return "redirect:/rental-booking/list";
    }
    
 // Show Return Form
    @GetMapping("/return-form/{id}")
    public String showReturnForm(@PathVariable int id, Model model) {
        RentalBooking booking = bookingService.getBookingById(id);
        booking.setActualReturnDate(LocalDate.now()); // default to today
        model.addAttribute("booking", booking);
        return "rental_booking/return-form";
    }

    // Handle Return Submission
    @PostMapping("/return-form")
    public String processReturn(@ModelAttribute RentalBooking bookingForm) {
    	System.out.println("Return POST handler triggered for booking ID: " + bookingForm.getId());

        RentalBooking booking = bookingService.getBookingById(bookingForm.getId());
        LocalDate actualReturnDate = bookingForm.getActualReturnDate();
        LocalDate scheduledReturnDate = booking.getReturnDate();

        if (actualReturnDate == null || scheduledReturnDate == null) {
            throw new IllegalArgumentException("Actual or Scheduled Return Date cannot be null");
        }

        booking.setActualReturnDate(actualReturnDate);

        long daysLate = 0;
        if (actualReturnDate.isAfter(scheduledReturnDate)) {
            daysLate = ChronoUnit.DAYS.between(scheduledReturnDate, actualReturnDate);
        }

        double lateCharge = daysLate * 100; // or 1000
        booking.setLateCharges(lateCharge);
        booking.setStatus("Returned");

        // ✅ Mark vehicle as Available
        CompanyVehicle vehicle = booking.getVehicle();
        vehicle.setStatus("Available");
        vehicleService.saveVehicle(vehicle);

        bookingService.saveBooking(booking);
        
        return "redirect:/rental-booking/list";
        
    }


}
