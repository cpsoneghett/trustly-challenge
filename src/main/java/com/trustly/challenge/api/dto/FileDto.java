package com.trustly.challenge.api.dto;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trustly.challenge.api.entity.GitHubFileData;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {

	private String fileName;
	private String fileSize;
	private String totalLines;
	private String notEmptyLines;

	public FileDto(GitHubFileData ghfd) {

		BeanUtils.copyProperties(ghfd, this);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(String totalLines) {
		this.totalLines = totalLines;
	}

	public String getNotEmptyLines() {
		return notEmptyLines;
	}

	public void setNotEmptyLines(String notEmptyLines) {
		this.notEmptyLines = notEmptyLines;
	}

}
