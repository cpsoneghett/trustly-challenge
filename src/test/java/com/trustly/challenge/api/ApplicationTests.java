package com.trustly.challenge.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import com.trustly.challenge.api.dto.ApiRequestDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);

	private static final String TEST_URL = "https://github.com/cpsoneghett/test-scraping";
	private static final String NO_OWNER_GITHUB_URL = "https://github.com/";
	private static final String NO_PAGE_GITHUB_URL = "https://github.com/cpsoneghett";
	private static final String NON_GITHUB_URL = "http://pudim.com.br/";

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Test
	void accessingNonGitHubPageTest() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/list-repository-files";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();

		ApiRequestDto requestBody = new ApiRequestDto(NON_GITHUB_URL);

		HttpEntity<ApiRequestDto> request = new HttpEntity<ApiRequestDto>(requestBody, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals(true, result.getBody()
				.contains("This is not a valid GitHub Repository. The URL must contain 'https://github.com'"));

	}

	@Test
	void accessingGithubPageWithNoOwnerOrRepositoryTest() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/list-repository-files";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();

		ApiRequestDto requestBody = new ApiRequestDto(NO_OWNER_GITHUB_URL);

		HttpEntity<ApiRequestDto> request = new HttpEntity<ApiRequestDto>(requestBody, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		assertEquals(true, result.getBody().contains("It was not given repository owner and name"));

		request.getBody().setRepositoryUrl(NO_PAGE_GITHUB_URL);

		result = this.restTemplate.postForEntity(uri, request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals(true,
				result.getBody().contains("Repository owner was given but no name for an actual repository"));

	}

	@Test
	void listAllRepositoryFilesTest() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/list-repository-files";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();

		ApiRequestDto requestBody = new ApiRequestDto(TEST_URL);

		HttpEntity<ApiRequestDto> request = new HttpEntity<ApiRequestDto>(requestBody, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(true, result.getBody().contains("Dockerfile"));

	}

	/**
	 * PERFORMANCE TEST METHOD
	 * 
	 */
	void listAllRepositoryFilesPerformanceTest() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/list-repository-files";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();

		ApiRequestDto requestBody = new ApiRequestDto(TEST_URL);

		HttpEntity<ApiRequestDto> request = new HttpEntity<ApiRequestDto>(requestBody, headers);

		StopWatch stopWatch = new StopWatch("Testing Scraping API performances");

		for (int i = 0; i < 5; i++) {
			stopWatch.start("Test iteration " + i);

			ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

			assertEquals(true, result.getBody().contains("Dockerfile"));
			stopWatch.stop();
		}

		log.info(stopWatch.prettyPrint());
	}

}
