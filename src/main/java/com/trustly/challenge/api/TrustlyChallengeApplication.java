package com.trustly.challenge.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class TrustlyChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrustlyChallengeApplication.class, args);
	}

	@Scheduled(fixedRateString = "${cache.cleaning.default.time}")
	@CacheEvict(allEntries = true, value = { "getRepositoryData" })
	public void clearCache() {
		//System.out.println(">>> CACHE CLEARED <<<");
	}

}
