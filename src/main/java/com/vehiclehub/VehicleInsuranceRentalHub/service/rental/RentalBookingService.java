package com.vehiclehub.VehicleInsuranceRentalHub.service.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;

import java.util.List;

public interface RentalBookingService {

    RentalBooking saveBooking(RentalBooking booking);
    List<RentalBooking> getAllBookings();
    RentalBooking getBookingById(int id);
    void deleteBooking(int id);

    List<RentalBooking> getBookingsByCustomerId(int customerId);
    List<RentalBooking> getBookingsByVehicleId(int vehicleId);
    List<RentalBooking> getBookingsByStatus(String status);
}
