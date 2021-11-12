package com.hellodoctor.controllers;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hellodoctor.configuration.Utility;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.services.HelloDoctorServices;
import net.bytebuddy.utility.RandomString;

@RestController
public class ForgotPassword {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private HelloDoctorServices userService;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "forgot_password_form";
	}

	@PostMapping("/forgotpassword/{email}")
	public String processForgotPassword(HttpServletRequest request,@PathVariable(value ="email") String email) throws Exception {
		String token = RandomString.make(40);
		try {
			userService.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
		} catch (Exception e) {
			throw new Exception("Error while sending email");
		}
		return null;
	}

	public void sendEmail(String email, String link) throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("contact@HelloDoctor.com", "Hello Doctor Support");
		helper.setTo(email);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		javaMailSender.send(message);

	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token) {
		Doctor user = userService.getByResetPasswordToken(token);
		if (user == null) {
			String message = "Invalid Token";
			return message;
		}

		return "reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(@Param(value = "token") String token,@Param(value = "password") String password) {

		Doctor user = userService.getByResetPasswordToken(token);

		if (user == null) {
			String message = "Invalid Token";
			return message;
		} else {
			userService.updatePassword(user, password);

			String message = "You have successfully changed your password.";
			return message;
		}

	}
}

