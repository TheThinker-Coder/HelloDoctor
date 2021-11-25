package com.hellodoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellodoctor.entities.Captcha;
import com.hellodoctor.services.CaptchaService;

@RestController
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;

	@GetMapping("/createCaptcha")
	public Captcha createCaptch() {
		return captchaService.generateCaptcha();

	}

}
