package com.hellodoctor.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.exception.EmptyInputException;
import com.hellodoctor.requestdto.UserRequestDto;
import com.hellodoctor.responsedto.JwtResponseDto;
import com.hellodoctor.services.PatientService;
import com.hellodoctor.services.UsersService;

@RestController
public class LoginUserController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PatientService patientService;

	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@RequestBody UserRequestDto userRequestDto) {
		if(ObjectUtils.isEmpty(userRequestDto.getCaptchaId())) {
			throw new EmptyInputException(Constant.CAPTCHA_FIELD);
		}
		JwtResponseDto response = usersService.getByEmail(userRequestDto.getEmail(), userRequestDto.getPassword(),
				userRequestDto.getCaptchaId());
		return ResponseEntity.ok(new JwtResponseDto(response.getJwtToken(), response.getRole()));

	}
	
	@GetMapping("/users/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
         
        List<Patient> listUsers = patientService.listAll();
 
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Patient-ID", "Patient-E-mail", "Patient Name", "Roles", "Patient-Password","Patient-Mobile-Number"};
        String[] nameMapping = {"patientId", "patientEmail", "patientName", "role", "patientPassword","patientMobileNumber"};
         
        csvWriter.writeHeader(csvHeader);
         
        for (Patient patient : listUsers) {
            csvWriter.write(patient, nameMapping);
        }
         
        csvWriter.close();
         
    }

}
