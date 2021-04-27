package spring.bot.calculator.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.bot.calculator.component.ExpressionManager;
import spring.bot.calculator.model.ViberMessageIn;
import spring.bot.calculator.services.CalculateService;
import spring.bot.calculator.services.ViberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViberBotController.class)
class ViberBotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ViberService viberService;

    @MockBean
    private CalculateService calculateService;

    @MockBean
    private ExpressionManager expressionManager;

    @Test
    void botProcess() throws Exception {
        mockMvc.perform(post("/bot").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new ViberMessageIn())))
                .andExpect(status().isOk());
    }
}