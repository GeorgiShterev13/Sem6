package com.example.demo.HelloworldService.controllers;

import com.example.demo.HelloworldService.business.interfaces.GetVideoUseCase;
import com.example.demo.HelloworldService.domain.GetVideoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.HelloworldService.business.interfaces.GetVideoUseCase;

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
