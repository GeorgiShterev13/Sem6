package com.example.demo.videoService.controllers;

import com.example.demo.videoService.business.interfaces.GetVideoUseCase;
import com.example.demo.videoService.domain.GetVideoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
@AllArgsConstructor
public class VideoController {

    private final GetVideoUseCase getVideoUseCase;

    @GetMapping
    public ResponseEntity<GetVideoResponse> getVideo() {
        GetVideoResponse response = getVideoUseCase.getAllVideos();
        return ResponseEntity.ok(response);
    }

}
