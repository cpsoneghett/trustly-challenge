package com.trustly.challenge.api.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith( SpringRunner.class )
@WebMvcTest
@AutoConfigureMockMvc
public class TrustlyApiRestControllerTest {

	@Autowired
	TrustlyApiRestController apiRestController;

	@Autowired
	private MockMvc mockMvc;

}
