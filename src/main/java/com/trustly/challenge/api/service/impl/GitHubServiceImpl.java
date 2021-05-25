package com.trustly.challenge.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.trustly.challenge.api.dto.ApiResponseDto;
import com.trustly.challenge.api.dto.FileDto;
import com.trustly.challenge.api.entity.GitHubFileData;
import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.exceptionhandler.exception.NotAGitHubRepositoryUrlException;
import com.trustly.challenge.api.exceptionhandler.exception.RepositoryNameOrOwnerMissingException;
import com.trustly.challenge.api.service.GitHubService;

@Service
public class GitHubServiceImpl implements GitHubService {

	private static final Logger log = LoggerFactory.getLogger(GitHubServiceImpl.class);

	@Autowired
	private CacheManager cacheManager;

	public ApiResponseDto convertDataToApiResponse(GitHubRepositoryData ghrd) {

		log.info("Converting gh repository data into the api response");

		HashSet<String> uniqueExtensions = ghrd.getRepositoryFiles().stream().map(GitHubFileData::getExtension)
				.collect(Collectors.toCollection(HashSet::new));

		List<FileDto> data = new ArrayList<>();

		for (String e : uniqueExtensions) {

			long lines = 0L;
			BigDecimal bytes = BigDecimal.ZERO;
			int count = 0;

			List<GitHubFileData> repositoryFilesFiltered = ghrd.getRepositoryFiles().stream()
					.filter(f -> e.equals(f.getExtension())).collect(Collectors.toList());

			for (GitHubFileData gfd : repositoryFilesFiltered) {
				lines += Long.parseLong(gfd.getTotalLines());
				bytes = bytes.add(gfd.getFileSize());
				count++;
			}

			data.add(new FileDto(e, count, lines, bytes.toString()));
		}

		return new ApiResponseDto(ghrd, ghrd.getRepositoryFiles().size(), data);
	}

	@Override
	public void validateRepository(String repositoryUrl) throws NotAGitHubRepositoryUrlException {

		if (!repositoryUrl.contains("https://github.com") && !repositoryUrl.contains("https://www.github.com"))
			throw new NotAGitHubRepositoryUrlException();

		String[] repoInfo = repositoryUrl.split("/");

		if (repoInfo.length == 3)
			throw new RepositoryNameOrOwnerMissingException("It was not given repository owner and name");
		else if (repoInfo.length == 4)
			throw new RepositoryNameOrOwnerMissingException(
					"Repository owner was given but no name for an actual repository");

	}

	@Override
	public void clearCache() {
		cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());

	}

}
