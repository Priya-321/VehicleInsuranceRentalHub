package com.vehiclehub.VehicleInsuranceRentalHub.repository.rental;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalBookingRepository extends JpaRepository<RentalBooking, Integer>{
//manage  rental bookings
}
