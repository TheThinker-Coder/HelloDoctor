package com.hellodoctor.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Captcha {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long captchaId;
	private String captcha;

}
