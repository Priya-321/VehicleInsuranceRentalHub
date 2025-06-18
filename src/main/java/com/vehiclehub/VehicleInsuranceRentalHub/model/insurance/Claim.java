package com.vehiclehub.VehicleInsuranceRentalHub.model.insurance;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Claim {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private LocalDate claimDate;
	    private String description;
	    private Double claimAmount;
	    private String status; // e.g., Pending, Approved, Rejected

	    @ManyToOne //many claims can be made against one policy
	    @JoinColumn(name = "policy_id")
	    private Policy policy;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public LocalDate getClaimDate() {
			return claimDate;
		}

		public void setClaimDate(LocalDate claimDate) {
			this.claimDate = claimDate;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Double getClaimAmount() {
			return claimAmount;
		}

		public void setClaimAmount(Double claimAmount) {
			this.claimAmount = claimAmount;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Policy getPolicy() {
			return policy;
		}

		public void setPolicy(Policy policy) {
			this.policy = policy;
		}

		@Override
		public String toString() {
			return "Claim [id=" + id + ", claimDate=" + claimDate + ", description=" + description + ", claimAmount="
					+ claimAmount + ", status=" + status + ", policy=" + policy + "]";
		}
	    
	    
}
