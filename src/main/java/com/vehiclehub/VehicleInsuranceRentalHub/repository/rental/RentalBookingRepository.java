package com.vehiclehub.VehicleInsuranceRentalHub.repository.rental;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalBooking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentalBookingRepository extends JpaRepository<RentalBooking, Integer>{
//manage  rental bookings
	
	List<RentalBooking> findByCustomerId(int customerId);
	List<RentalBooking> findByVehicleId(int vehicleId);
	List<RentalBooking> findByStatus(String status);
	@Query("SELECT b FROM RentalBooking b WHERE LOWER(b.customer.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<RentalBooking> findByCustomerNameContainingIgnoreCase(String name);

}
