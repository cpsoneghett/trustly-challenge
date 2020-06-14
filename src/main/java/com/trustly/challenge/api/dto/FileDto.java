package com.trustly.challenge.api.dto;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trustly.challenge.api.entity.GitHubFileData;

import lombok.Data;

@Data
@JsonInclude( value = JsonInclude.Include.NON_NULL )
@JsonIgnoreProperties( ignoreUnknown = true )
public class FileDto {

	private String fileName;
	private String fileSize;
	private String totalLines;
	private String notEmptyLines;

	public FileDto( GitHubFileData ghfd ) {

		BeanUtils.copyProperties( ghfd, this );
	}
}
