package com.trustly.challenge.api.dto;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trustly.challenge.api.entity.GitHubRepositoryData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude( value = JsonInclude.Include.NON_NULL )
@JsonIgnoreProperties( ignoreUnknown = true )
@JsonPropertyOrder( { "repositoryName", "owner", "totalFiles", "extensions" } )
public class ApiResponseDto {

	@JsonProperty( value = "repositoryName" )
	private String name;

	@JsonProperty( )
	private String owner;

	@JsonProperty( )
	private Integer totalFiles;

	@JsonProperty( )
	private Map<String, List<FileDto>> extensions;

	public ApiResponseDto( GitHubRepositoryData ghrd, Integer totalFiles, Map<String, List<FileDto>> extensions ) {

		BeanUtils.copyProperties( ghrd, this );

		this.totalFiles = totalFiles;
		this.extensions = extensions;
	}

}
