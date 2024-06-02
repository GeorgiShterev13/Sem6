package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.HelloworldService.business.impl.GetVideoUseCaseImpl;
import com.example.demo.HelloworldService.domain.GetVideoResponse;
import org.junit.jupiter.api.Test;

class GetVideoUseCaseImplTest {

    @Test
    void getAllVideos_returnsExpectedVideo() {
        // Setup the use case
        GetVideoUseCaseImpl getVideoUseCase = new GetVideoUseCaseImpl();

        // Execute the use case
        GetVideoResponse response = getVideoUseCase.getAllVideos();

        // Assert the response is correct
        assertEquals("Hi there, I am a video", response.getVideo(), "The video content should match the expected content.");
    }
}
