package com.vehiclehub.VehicleInsuranceRentalHub.model.rental;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent; // Use jakarta.validation for Spring Boot 3+
import jakarta.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Entity
public class RentalBooking {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @NotNull(message = "Customer is required")
	    @ManyToOne
	    private RentalCustomer customer;

	    @NotNull(message = "Vehicle is required")
	    @ManyToOne
	    private CompanyVehicle vehicle;

	    @NotNull(message = "Booking date is required")
	    @FutureOrPresent(message = "Booking date cannot be in the past")
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate bookingDate;

	    @NotNull(message = "Return date is required")
	    @Future(message = "Return date must be in the future")
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate returnDate;

	    private LocalDate actualReturnDate;

	    private Double lateCharges;

	    @NotBlank(message = "Status is required")
	    private String status;

	    // Getters and setters...

    
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


	public LocalDate getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(LocalDate actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	@Override
	public String toString() {
		return "RentalBooking [id=" + id + ", bookingDate=" + bookingDate + ", returnDate=" + returnDate
				+ ", actualReturnDate=" + actualReturnDate + ", status=" + status + ", lateCharges=" + lateCharges
				+ ", customer=" + customer + ", vehicle=" + vehicle + "]";
	}

	
    
    
}
