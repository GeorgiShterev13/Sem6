package com.example.demo;

import com.example.demo.videoService.business.impl.GetVideoUseCaseImpl;
import com.example.demo.videoService.domain.GetVideoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetVideoUseCaseImplTest {

    @Test
    void getAllVideosReturnsCorrectResponse() {
        GetVideoUseCaseImpl getVideoUseCaseImpl = new GetVideoUseCaseImpl();
        GetVideoResponse response = getVideoUseCaseImpl.getAllVideos();

        assertEquals("Hi there, I am a video ", response.getVideo());
    }
}
