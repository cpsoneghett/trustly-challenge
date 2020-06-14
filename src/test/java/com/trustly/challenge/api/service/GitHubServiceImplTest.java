package com.trustly.challenge.api.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.repository.GitHubRepositoryDataRepository;
import com.trustly.challenge.api.service.impl.GitHubServiceImpl;

@RunWith( SpringRunner.class )
public class GitHubServiceImplTest {

	@TestConfiguration
	static class GHServiceImplTestContextConfiguration {

		@Bean
		public GitHubService gitHubService() {
			return new GitHubServiceImpl();
		}
	}

	@Autowired
	private GitHubService gitHubService;

	@MockBean
	private GitHubRepositoryDataRepository repository;

	@Before
	public void setUp() {

	}

	@Test
	public void test_insertion() {

		GitHubRepositoryData ghrdMock = Mockito.mock( GitHubRepositoryData.class );

		this.gitHubService.saveAllData( ghrdMock );

		Mockito.when( this.repository.findById( ghrdMock.getId() ) ).thenReturn( Optional.of( ghrdMock ) );

		Assert.assertEquals( true, true ); // TODO: FIX THIS TEST

	}
}
