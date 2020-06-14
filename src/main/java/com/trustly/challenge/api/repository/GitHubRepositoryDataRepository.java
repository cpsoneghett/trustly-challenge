package com.trustly.challenge.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trustly.challenge.api.entity.GitHubRepositoryData;

@Repository
public interface GitHubRepositoryDataRepository extends JpaRepository<GitHubRepositoryData, Long> {

	@Query( "SELECT 1 FROM GitHubRepositoryData g WHERE g.name = ?1 AND g.owner = ?2 AND EXISTS (SELECT 1 FROM GitHubFileData f WHERE f.repository.id = g.id)" )
	public Optional<Long> repositoryExists( String name, String owner );

	@Query( "SELECT g FROM GitHubRepositoryData g WHERE g.name = ?1 AND g.owner = ?2" )
	public GitHubRepositoryData find( String name, String owner );

}
