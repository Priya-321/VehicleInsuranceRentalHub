package com.vehiclehub.VehicleInsuranceRentalHub.controller.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalBookingService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalCustomerService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.CompanyVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<RentalBooking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "rental_booking/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<RentalBooking> bookings;

        if (query.matches("\\d+")) { // ID Search
            try {
                RentalBooking booking = bookingService.getBookingById(Integer.parseInt(query));
                bookings = List.of(booking);
            } catch (NotFoundException e) {
                bookings = bookingService.getAllBookings(); // fallback list
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else { // Name Search
            bookings = bookingService.searchByCustomerName(query);

            if (bookings.isEmpty()) {
                bookings = bookingService.getAllBookings(); // fallback list
                model.addAttribute("errorMessage", "No bookings found for customer name: " + query);
            }
        }

        model.addAttribute("bookings", bookings);
        return "rentalBooking/list";
    }


    
    @GetMapping("/form")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new RentalBooking());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("vehicles", vehicleService.getVehiclesByStatus("Available")); // only available vehicles
        return "rental_booking/form";
    }

    @PostMapping("/save")
    public String saveBooking(@ModelAttribute RentalBooking booking,
                              @RequestParam int customer,
                              @RequestParam int vehicle) {

        RentalCustomer selectedCustomer = customerService.getCustomerById(customer);
        CompanyVehicle selectedVehicle = vehicleService.getVehicleById(vehicle);

        // Assign relationships
        booking.setCustomer(selectedCustomer);
        booking.setVehicle(selectedVehicle);

        // Optional defaults
        if (booking.getStatus() == null || booking.getStatus().isEmpty())
            booking.setStatus("Booked");

        bookingService.saveBooking(booking);

        // Update vehicle status to Booked
        selectedVehicle.setStatus("Booked");
        vehicleService.saveVehicle(selectedVehicle);

        return "redirect:/rental-booking/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable int id) {
        RentalBooking booking = bookingService.getBookingById(id);
        if (booking != null) {
            CompanyVehicle vehicle = booking.getVehicle();
            vehicle.setStatus("Available"); // release the vehicle
            vehicleService.saveVehicle(vehicle);
        }

        bookingService.deleteBooking(id);
        return "redirect:/rental-booking/list";
    }
}
