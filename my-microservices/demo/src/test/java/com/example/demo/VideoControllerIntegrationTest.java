package com.example.demo;

import com.example.demo.videoService.business.interfaces.GetVideoUseCase;
import com.example.demo.videoService.domain.GetVideoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetVideoUseCase getVideoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVideoReturnsCorrectResponse() throws Exception {
        GetVideoResponse mockResponse = new GetVideoResponse("Hi there, I am a video ");
        when(getVideoUseCase.getAllVideos()).thenReturn(mockResponse);

        mockMvc.perform(get("/videos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"video\":\"Hi there, I am a video \"}"));
    }
}
