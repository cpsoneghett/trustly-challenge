package com.trustly.challenge.api.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequestDto {

	@NotBlank(message = "Repository URL is mandatory.")
	private String repositoryUrl;

	public ApiRequestDto() {
		super();
	}

	public ApiRequestDto(@NotBlank(message = "Repository URL is mandatory.") String repositoryUrl) {
		super();
		this.repositoryUrl = repositoryUrl;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
}
