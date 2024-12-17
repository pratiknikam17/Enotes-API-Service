package com.pratik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PswdResetRequest {

	private Integer uid;

	private String newPassword;

}