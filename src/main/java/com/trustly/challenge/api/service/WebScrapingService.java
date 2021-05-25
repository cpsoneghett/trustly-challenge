package com.trustly.challenge.api.service;

import java.io.IOException;

import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.exceptionhandler.exception.NonExistentOrPrivateRepositoryException;
import com.trustly.challenge.api.exceptionhandler.exception.TooManyRequestsException;

public interface WebScrapingService {

	public GitHubRepositoryData getRepositoryData(String webUrl)
			throws IOException, TooManyRequestsException, InterruptedException, NonExistentOrPrivateRepositoryException;
}
