package com.vehiclehub.VehicleInsuranceRentalHub.model.rental;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CompanyVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;
    private String numberPlate;
    private String type;
    private Double price;
    private String status; // Available, Booked, etc.

    
    


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getNumberPlate() {
		return numberPlate;
	}


	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}



	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<RentalBooking> bookings;
	
	public List<RentalBooking> getBookings() {
		return bookings;
	}


	public void setBookings(List<RentalBooking> bookings) {
		this.bookings = bookings;
	}


	@Override
	public String toString() {
		return "CompanyVehicle [id=" + id + ", model=" + model + ", numberPlate=" + numberPlate + ", type=" + type
				+ ", price=" + price + ", status=" + status + ", bookings=" + bookings + "]";
	}


	
	
	
}
