package com.trustly.challenge.api.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@DataJpaTest
public class GitHubRepositoryDataRepositoryTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private GitHubRepositoryDataRepository dataRepository;

	/*
	@Test
	public void givenRepositoryNameAndOwner_thenReturnIfRepositoryExists() {
		// given
		GitHubRepositoryData ghrd = new GitHubRepositoryData( "t-test-scraping", "t-cpsoneghett" );
		em.persist( ghrd );
		em.flush();

		// when
		Optional<Long> repositoryExists = dataRepository.repositoryExists( ghrd.getName(), ghrd.getOwner() );

		// then
		assertThat( repositoryExists.isPresent() ).isEqualTo( true );
	}
	 */
}
