package com.vehiclehub.VehicleInsuranceRentalHub.model.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class RentalCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phone;
    private String address;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ManyToOne // Many customers are handled by one staff or agent
    @JoinColumn(name = "agent_id")
    private Agent agent; // staff_attending

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //one customer can have many rental bookings
    private List<RentalBooking> bookings;


	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<RentalBooking> getBookings() {
		return bookings;
	}

	public void setBookings(List<RentalBooking> bookings) {
		this.bookings = bookings;
	}


	@Override
	public String toString() {
		return "RentalCustomer [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", agent=" + agent + ", bookings=" + bookings + "]";
	}

    
}
