package com.pratik.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String mobNo;

	private StatusDto status;
	
	private List<RoleDto> roles;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Builder
	public static class RoleDto {
		private Integer id;
		private String name;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Builder
	public static class StatusDto {
		private Integer id;
		private Boolean isActive;
	}
}