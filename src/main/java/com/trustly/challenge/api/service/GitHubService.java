package com.trustly.challenge.api.service;

import com.trustly.challenge.api.dto.ApiResponseDto;
import com.trustly.challenge.api.entity.GitHubRepositoryData;

public interface GitHubService {

	public GitHubRepositoryData find( String url );

	public void saveAllData( GitHubRepositoryData ghrd );

	public ApiResponseDto convertDataToApiResponse( GitHubRepositoryData ghrd );

}
