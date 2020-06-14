package com.trustly.challenge.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trustly.challenge.api.dto.ApiResponseDto;
import com.trustly.challenge.api.dto.FileDto;
import com.trustly.challenge.api.entity.GitHubFileData;
import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.exceptionhandler.exception.NotAGitHubRepositoryUrlException;
import com.trustly.challenge.api.exceptionhandler.exception.RepositoryNameOrOwnerMissingException;
import com.trustly.challenge.api.repository.GitHubRepositoryDataRepository;
import com.trustly.challenge.api.service.GitHubService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GitHubServiceImpl implements GitHubService {

	@Autowired
	private GitHubRepositoryDataRepository repository;

	public GitHubRepositoryData find( String url ) {

		String[] s = url.split( "/" );

		return repository.find( s[ s.length - 1 ], s[ s.length - 2 ] );
	}

	public void saveAllData( GitHubRepositoryData ghrd ) {

		log.info( "Saving the information into database" );
		repository.save( ghrd );
	}

	public ApiResponseDto convertDataToApiResponse( GitHubRepositoryData ghrd ) {

		log.info( "Converting gh repository data into the api response" );

		HashSet<String> uniqueExtensions = ghrd.getRepositoryFiles().stream().map( GitHubFileData::getExtension ).collect( Collectors.toCollection( HashSet::new ) );

		HashMap<String, List<FileDto>> extensions = new HashMap<>();

		for ( String e : uniqueExtensions ) {

			List<FileDto> filesDto = new ArrayList<>();
			ghrd.getRepositoryFiles().stream().filter( f -> e.equals( f.getExtension() ) ).forEach( f -> filesDto.add( new FileDto( f ) ) );
			extensions.put( e, filesDto );

		}

		return new ApiResponseDto( ghrd, ghrd.getRepositoryFiles().size(), extensions );
	}

	@Override
	public void validateRepository( String repositoryUrl ) throws NotAGitHubRepositoryUrlException {

		if ( !repositoryUrl.contains( "https://github.com" ) && !repositoryUrl.contains( "https://www.github.com" ) )
			throw new NotAGitHubRepositoryUrlException();

		String[] repoInfo = repositoryUrl.split( "/" );

		if ( repoInfo.length == 3 )
			throw new RepositoryNameOrOwnerMissingException( "It was not given repository owner and name" );
		else if ( repoInfo.length == 4 )
			throw new RepositoryNameOrOwnerMissingException( "Repository owner was given but no name for an actual repository" );

	}

}
