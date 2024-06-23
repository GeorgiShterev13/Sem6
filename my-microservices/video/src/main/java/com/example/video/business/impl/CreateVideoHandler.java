package com.example.video.business.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.video.domain.Video;
import com.example.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class CreateVideoHandler {
    private final AmazonS3 s3client;
    private final VideoRepository videoRepository;

    @Autowired
    public CreateVideoHandler(AmazonS3 s3client, VideoRepository videoRepository) {
        this.s3client = s3client;
        this.videoRepository = videoRepository;
    }

    @RabbitListener(queues = "user_created_queue")
    public void handleUserCreated(String userId) {
        String bucketName = "my-video-storage-06c01c168bf3da55";
        String folderKey = userId + "/";
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderKey, emptyContent, metadata);
        s3client.putObject(putObjectRequest);

        Video video = videoRepository.findByUserId(userId).orElse(new Video());
        video.setUserId(userId);
        video.setFolderName(folderKey);
        videoRepository.save(video);
    }
}
