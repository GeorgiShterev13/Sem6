package com.example.demo.HelloworldService.business.impl;

import com.example.demo.HelloworldService.business.interfaces.GetVideoUseCase;
import com.example.demo.HelloworldService.domain.GetVideoResponse;
import com.example.demo.HelloworldService.domain.Video;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetVideoUseCaseImpl implements GetVideoUseCase {


    @Override
    public GetVideoResponse getAllVideos() {
        String video =  "Hi there, I am one video";
        GetVideoResponse getVideoResponse = new GetVideoResponse(video);


        return getVideoResponse;
    }
}