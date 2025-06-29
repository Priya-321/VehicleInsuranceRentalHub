package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.ResourceNotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.rental.RentalBookingRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalBookingServiceImpl implements RentalBookingService {

    @Autowired
    private RentalBookingRepository bookingRepository;

    @Override
    public RentalBooking saveBooking(RentalBooking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<RentalBooking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public RentalBooking getBookingById(int id) {
    	return bookingRepository.findById(id)
    	        .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
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
}
