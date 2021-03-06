package com.trustly.challenge.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {

	private String extension;
	private int count;
	private long lines;
	private String bytes;

	public FileDto(String extension, int count, long lines, String bytes) {
		super();
		this.extension = extension;
		this.count = count;
		this.lines = lines;
		this.bytes = bytes;
	}

	public String getExtension() {
		return extension;
	}

	public int getCount() {
		return count;
	}

	public long getLines() {
		return lines;
	}

	public String getBytes() {
		return bytes;
	}

}
