package com.example.video.business.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.video.controllers.VideoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(VideoController.class)
class VideoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmazonS3 amazonS3;

    private final String bucketName = "my-video-storage-06c01c168bf3da55";

    @BeforeEach
    void setUp() {
        Mockito.reset(amazonS3);
    }

    @Test
    void uploadVideoSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.mp4", "video/mp4", "video content".getBytes());
        Mockito.doNothing().when(amazonS3).putObject(eq(bucketName), anyString(), any(ByteArrayInputStream.class), any(ObjectMetadata.class));

        MvcResult result = mockMvc.perform(multipart("/video/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Video uploaded successfully with a name: videos/test.mp4", result.getResponse().getContentAsString());
    }

    @Test
    void uploadVideoFailure() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.mp4", "video/mp4", "video content".getBytes());
        Mockito.doThrow(IOException.class).when(amazonS3).putObject(eq(bucketName), anyString(), any(ByteArrayInputStream.class), any(ObjectMetadata.class));

        MvcResult result = mockMvc.perform(multipart("/video/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertEquals("Could not upload the video: test.mp4!", result.getResponse().getContentAsString());
    }
}
