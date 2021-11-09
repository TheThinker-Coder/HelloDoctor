package com.hellodoctor.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
	private Long userId;
	private String email;
	private String password;
	private Long mobile;
	private String role;
}
