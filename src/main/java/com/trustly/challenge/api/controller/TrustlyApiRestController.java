package com.trustly.challenge.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trustly.challenge.api.dto.ApiRequestDto;
import com.trustly.challenge.api.dto.ApiResponseDto;
import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.exceptionhandler.exception.NotAGitHubRepositoryUrlException;
import com.trustly.challenge.api.service.GitHubService;
import com.trustly.challenge.api.service.WebScrapingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class TrustlyApiRestController {

	@Autowired
	private WebScrapingService webScrapingService;

	@Autowired
	private GitHubService ghRepositoryService;

	@PostMapping("/list-repository-files")
	@ApiOperation(value = "Get the information of each file from repository")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Repository Data obtained successfully!"),
			@ApiResponse(code = 400, message = "Bad request") })
	public synchronized ResponseEntity<ApiResponseDto> listAllRepositoryFiles(@Valid @RequestBody ApiRequestDto request)
			throws IOException, NotAGitHubRepositoryUrlException {

		ghRepositoryService.validateRepository(request.getRepositoryUrl());

		GitHubRepositoryData repositoryData = webScrapingService.getRepositoryData(request.getRepositoryUrl());

		ApiResponseDto response = ghRepositoryService.convertDataToApiResponse(repositoryData);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

}
