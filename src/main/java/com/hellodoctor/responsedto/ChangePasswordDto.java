package com.hellodoctor.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

	private String newpassword;
	private String confirmpassword;
	private String email;
	private String oldpassword;
}
