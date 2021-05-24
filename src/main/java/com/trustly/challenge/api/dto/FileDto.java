package com.trustly.challenge.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {

	private String extension;
	private int count;
	private long lines;
	private long bytes;

	public FileDto(String extension, int count, long lines, long bytes) {
		super();
		this.extension = extension;
		this.count = count;
		this.lines = lines;
		this.bytes = bytes;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getLines() {
		return lines;
	}

	public void setLines(long lines) {
		this.lines = lines;
	}

	public long getBytes() {
		return bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

}
