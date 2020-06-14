package com.trustly.challenge.api.service;

import java.io.IOException;

import com.trustly.challenge.api.entity.GitHubRepositoryData;

public interface WebScrapingService {

	public GitHubRepositoryData getRepositoryData( String webUrl ) throws IOException;
}
