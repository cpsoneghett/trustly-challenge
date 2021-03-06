package com.trustly.challenge.api.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.cache.annotation.Cacheable;

@Entity
@Cacheable("GitHubFileData")
public class GitHubFileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fileName;

	private String extension;

	private String totalLines;

	private String notEmptyLines;

	private BigDecimal fileSize;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_repository")
	private GitHubRepositoryData repository;

	public GitHubFileData() {
		super();
	}

	public GitHubFileData(GitHubRepositoryData ghrd, String fileName, String extension, String totalLines,
			String notEmptyLines, String fileSize) {
		super();
		this.repository = ghrd;
		this.fileName = fileName;
		this.extension = "".equals(extension) ? fileName : extension;
		this.totalLines = totalLines;
		this.notEmptyLines = notEmptyLines;
		this.fileSize = getSizeInBytes(fileSize);
	}

	private BigDecimal getSizeInBytes(String fileSize) {

		String[] s = fileSize.split(" ");
		BigDecimal actualSize = new BigDecimal(s[0]);

		if (s[1].contains("K"))
			actualSize = actualSize.multiply(new BigDecimal(1024));
		else if (s[1].contains("M"))
			actualSize = actualSize.multiply(new BigDecimal(1024 * 1024));
		else if (s[1].contains("G"))
			actualSize = actualSize.multiply(new BigDecimal(1024 * 1024 * 1024));

		return actualSize;

	}

	public String getExtension() {
		return extension;
	}

	public String getTotalLines() {
		return totalLines;
	}

	public BigDecimal getFileSize() {
		return fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public String getNotEmptyLines() {
		return notEmptyLines;
	}

}
