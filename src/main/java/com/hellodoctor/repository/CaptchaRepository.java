package com.hellodoctor.repository;

import org.springframework.data.repository.CrudRepository;

import com.hellodoctor.entities.Captcha;

public interface CaptchaRepository extends CrudRepository<Captcha, Long> {

	Captcha findByCaptcha(String captcha);

}
