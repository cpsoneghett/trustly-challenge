package com.trustly.challenge.api.service;

import com.trustly.challenge.api.dto.ApiResponseDto;
import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.exceptionhandler.exception.NotAGitHubRepositoryUrlException;

public interface GitHubService {

	public ApiResponseDto convertDataToApiResponse(GitHubRepositoryData ghrd);

	public void validateRepository(String repositoryUrl) throws NotAGitHubRepositoryUrlException;
	
	public void clearCache();

}
