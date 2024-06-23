package com.example.video.business.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.video.repository.VideoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoCleanupService {
    private final AmazonS3 s3client;
    private final VideoRepository videoRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String bucketName = "my-video-storage-06c01c168bf3da55";

    @Autowired
    public VideoCleanupService(AmazonS3 s3client, VideoRepository videoRepository, RabbitTemplate rabbitTemplate) {
        this.s3client = s3client;
        this.videoRepository = videoRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "user_cleanup_queue")
    public void handleCleanupRequest(String userId) {
        deleteVideoRecords(userId);
        deleteFolderFromS3(userId);
        rabbitTemplate.convertAndSend("user_exchange", "user.cleanup.completed", userId);
    }

    private void deleteVideoRecords(String userId) {
        videoRepository.deleteByUserId(userId);
    }

    private void deleteFolderFromS3(String userId) {
        String prefix = userId + "/"; // Assuming the folder path in S3 is based on the userId
        ObjectListing objectListing = s3client.listObjects(bucketName, prefix);
        while (true) {
            for (S3ObjectSummary file : objectListing.getObjectSummaries()) {
                s3client.deleteObject(bucketName, file.getKey());
            }

            // If the bucket contains many objects, the listObjects() call might paginate the response.
            // Check if there are more pages and retrieve the next page.
            if (objectListing.isTruncated()) {
                objectListing = s3client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }
}
