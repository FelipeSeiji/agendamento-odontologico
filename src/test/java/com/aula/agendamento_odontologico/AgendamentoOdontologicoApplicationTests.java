package com.aula.agendamento_odontologico;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AgendamentoOdontologicoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void healthIsPublicAndDoesNotExposeInternalDetails() throws Exception {
		mockMvc.perform(get("/actuator/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"))
				.andExpect(jsonPath("$.components").doesNotExist())
				.andExpect(jsonPath("$.details").doesNotExist());
	}

	@Test
	void healthProbesArePublic() throws Exception {
		for (String probe : new String[]{"liveness", "readiness"}) {
			mockMvc.perform(get("/actuator/health/" + probe))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("UP"));
		}
	}

	@Test
	void businessAndOtherManagementEndpointsRequireAuthentication() throws Exception {
		for (String path : new String[]{"/dentista", "/actuator/info", "/actuator/env"}) {
			mockMvc.perform(get(path)).andExpect(status().isForbidden());
		}
	}

}
