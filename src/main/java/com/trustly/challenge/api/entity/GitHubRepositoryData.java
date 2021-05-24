package com.trustly.challenge.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.cache.annotation.Cacheable;

@Entity
@Cacheable("GitHubRepositoryData")
public class GitHubRepositoryData {

	@Id
	private Long id;

	private String name;

	private String owner;

	@OneToMany(mappedBy = "repository", cascade = CascadeType.ALL)
	private List<GitHubFileData> repositoryFiles;

	public GitHubRepositoryData(String url) {

		String[] s = url.split("/");
		this.name = s[s.length - 1];
		this.owner = s[s.length - 2];
	}

	public List<GitHubFileData> getRepositoryFiles() {
		return this.repositoryFiles;
	}

	public void setRepositoryFiles(List<GitHubFileData> repositoryFiles) {
		this.repositoryFiles = repositoryFiles;
	}

	public String getName() {
		return this.name;
	}

	public String getOwner() {
		return this.owner;
	}

}
