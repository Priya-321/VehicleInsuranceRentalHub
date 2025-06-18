package com.vehiclehub.VehicleInsuranceRentalHub.model.rental;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class RentalBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate bookingDate;
    private LocalDate returnDate;
    private String status;

    private Double lateCharges;  //if late return

    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getLateCharges() {
		return lateCharges;
	}

	public void setLateCharges(Double lateCharges) {
		this.lateCharges = lateCharges;
	}

	
	@ManyToOne //many booking can be mapped to one customer
    @JoinColumn(name = "customer_id")
    private RentalCustomer customer;

    @ManyToOne //many bookings can be mapped to one company vehicle
    @JoinColumn(name = "vehicle_id")
    private CompanyVehicle vehicle;
    
    
	public RentalCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(RentalCustomer customer) {
		this.customer = customer;
	}

	public CompanyVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(CompanyVehicle vehicle) {
		this.vehicle = vehicle;
	}


	@Override
	public String toString() {
		return "RentalBooking [id=" + id + ", bookingDate=" + bookingDate + ", returnDate=" + returnDate + ", status="
				+ status + ", lateCharges=" + lateCharges + ", customer=" + customer + ", vehicle=" + vehicle + "]";
	}
    
    
}
