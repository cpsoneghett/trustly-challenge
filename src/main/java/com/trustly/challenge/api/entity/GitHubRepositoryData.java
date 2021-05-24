package com.trustly.challenge.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

@Entity
@Table(name = "gh_repository")
@Cacheable("GitHubRepositoryData")
public class GitHubRepositoryData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "owner")
	private String owner;

	@OneToMany(mappedBy = "repository", cascade = CascadeType.ALL)
	private List<GitHubFileData> repositoryFiles;

	public GitHubRepositoryData(String name, String owner) {
		super();
		this.name = name;
		this.owner = owner;
	}

	public GitHubRepositoryData(String url) {

		String[] s = url.split("/");
		this.name = s[s.length - 1];
		this.owner = s[s.length - 2];
	}

	public List<GitHubFileData> getRepositoryFiles() {
		return repositoryFiles;
	}

	public void setRepositoryFiles(List<GitHubFileData> repositoryFiles) {
		this.repositoryFiles = repositoryFiles;
	}

}
