package com.example.video.business.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.video.controllers.VideoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class VideoControllerTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private VideoController videoController;

    private final String bucketName = "my-video-storage-06c01c168bf3da55";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadVideoSuccess() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.mp4", "video/mp4", "video content".getBytes());
        when(amazonS3.putObject(anyString(), anyString(), any(ByteArrayInputStream.class), any(ObjectMetadata.class)))
                .thenReturn(null); // Mocking the return value of putObject method

        ResponseEntity<String> response = videoController.uploadVideo(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Video uploaded successfully with a name: videos/test.mp4", response.getBody());
    }


}
