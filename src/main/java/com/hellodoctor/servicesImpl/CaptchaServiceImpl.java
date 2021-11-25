package com.hellodoctor.servicesImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Captcha;
import com.hellodoctor.repository.CaptchaRepository;
import com.hellodoctor.services.CaptchaService;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	@Autowired
	private CaptchaRepository captchaRepository;

	@Override
	public Captcha generateCaptcha() {

		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		Captcha captcha = new Captcha();
		for (int i = 0; i < 7; i++) {
			int length = random.nextInt(Constant.CAPTCHA_VALUE.length());

			builder.append(Constant.CAPTCHA_VALUE.charAt(length));
			captcha.setCaptcha(builder.toString());
		}
		return captchaRepository.save(captcha);

	}
}
