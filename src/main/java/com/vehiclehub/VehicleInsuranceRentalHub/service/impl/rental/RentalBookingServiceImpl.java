package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.rental.RentalBookingRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.CompanyVehicleService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalBookingService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalCustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RentalBookingServiceImpl implements RentalBookingService {

    @Autowired
    private RentalBookingRepository bookingRepository;

    @Autowired
    private RentalCustomerService customerService;

    @Autowired
    private CompanyVehicleService vehicleService;
    
    @Transactional
    public RentalBooking saveBooking(RentalBooking booking) {
        // Get full customer and vehicle using the IDs
        RentalCustomer fullCustomer = customerService.getCustomerById(booking.getCustomer().getId());
        CompanyVehicle fullVehicle = vehicleService.getVehicleById(booking.getVehicle().getId());

        booking.setCustomer(fullCustomer);
        booking.setVehicle(fullVehicle);

        return bookingRepository.save(booking);
    }

    @Override
    public List<RentalBooking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public RentalBooking getBookingById(int id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Rental booking with ID " + id + " not found."));
    }


    @Override
    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public RentalBooking updateBooking(RentalBooking booking) {
        return bookingRepository.save(booking);
    }

    
    @Transactional
    @Override
    public RentalBooking processReturn(int bookingId) {
        RentalBooking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));

        if (booking.getCustomer() == null || booking.getVehicle() == null) {
            throw new IllegalStateException("Customer or Vehicle is null in booking record.");
        }

        LocalDate actualReturn = booking.getActualReturnDate(); // from form
        LocalDate scheduledReturn = booking.getReturnDate();

        if (actualReturn == null) {
            actualReturn = LocalDate.now();
            booking.setActualReturnDate(actualReturn);
        }

        double lateCharge = 0.0;
        if (scheduledReturn != null && actualReturn.isAfter(scheduledReturn)) {
            long lateDays = ChronoUnit.DAYS.between(scheduledReturn, actualReturn);
            lateCharge = Math.max(0.0, lateDays * 100);
        }

        booking.setLateCharges(lateCharge);
        booking.setStatus("Returned");

        // Update vehicle status
        CompanyVehicle vehicle = vehicleService.getVehicleById(booking.getVehicle().getId()); // <- Refetched
        vehicle.setStatus("Available");
        vehicleService.saveVehicle(vehicle);

        booking.setVehicle(vehicle); // Re-attach to updated one

        return bookingRepository.save(booking);
    }




    @Override
    public List<RentalBooking> getBookingsByCustomerId(int customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<RentalBooking> getBookingsByVehicleId(int vehicleId) {
        return bookingRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<RentalBooking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }
  
    @Override
    public List<RentalBooking> searchByCustomerName(String name) {
        return bookingRepository.findByCustomerNameContainingIgnoreCase(name);
    }
    
    

}
