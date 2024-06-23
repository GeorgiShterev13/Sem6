package com.example.video.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Integer videoId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "video_name")
    private String videoName;

}
