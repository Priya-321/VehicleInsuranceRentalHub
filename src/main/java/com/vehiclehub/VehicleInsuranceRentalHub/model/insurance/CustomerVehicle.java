package com.vehiclehub.VehicleInsuranceRentalHub.model.insurance;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class CustomerVehicle {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private String registrationNumber;
	    private String model;
	    private String type;
	    
	    @ManyToOne //many vehicles can be mapped to one customer
	    @JoinColumn(name = "customer_id")
	    private InsuranceCustomer customer;

	    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	    private List<Policy> policies;  //one vehicle can have more policies

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getRegistrationNumber() {
			return registrationNumber;
		}

		public void setRegistrationNumber(String registrationNumber) {
			this.registrationNumber = registrationNumber;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public InsuranceCustomer getCustomer() {
			return customer;
		}

		public void setCustomer(InsuranceCustomer customer) {
			this.customer = customer;
		}

		public List<Policy> getPolicies() {
			return policies;
		}

		public void setPolicies(List<Policy> policies) {
			this.policies = policies;
		}

		@Override
		public String toString() {
			return "CustomerVehicle [id=" + id + ", registrationNumber=" + registrationNumber + ", model=" + model
					+ ", type=" + type + ", customer=" + customer + ", policies=" + policies + "]";
		}
	    
	    
	    
}
