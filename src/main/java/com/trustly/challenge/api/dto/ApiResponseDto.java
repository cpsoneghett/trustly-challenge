package com.trustly.challenge.api.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trustly.challenge.api.entity.GitHubRepositoryData;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "repositoryName", "owner", "totalFiles", "extensions" })
public class ApiResponseDto {

	@JsonProperty(value = "repositoryName")
	private String name;

	@JsonProperty
	private String owner;

	@JsonProperty
	private Integer totalFiles;

	@JsonProperty
	private List<FileDto> data;

	public ApiResponseDto(GitHubRepositoryData ghrd, Integer totalFiles, List<FileDto> data) {

		BeanUtils.copyProperties(ghrd, this);

		this.totalFiles = totalFiles;
		this.data = data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
