package com.hellodoctor.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contactUs")

public class ContactUs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactId;
	private String fullName;
	private String email;
	private Long mobileNumber;
	
	@ManyToOne
	private Patient patience ;

}
