package com.example.demo.videoService.business.impl;

import com.example.demo.videoService.business.interfaces.GetVideoUseCase;
import com.example.demo.videoService.domain.GetVideoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetVideoUseCaseImpl implements GetVideoUseCase {


    @Override
    public GetVideoResponse getAllVideos() {
        String video =  "Hi there, I am a video ";
        GetVideoResponse getVideoResponse = new GetVideoResponse(video);


        return getVideoResponse;
    }
}
