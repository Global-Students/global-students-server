package com.example.globalStudents.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_image")
public class PostImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(200)")
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public void setPost(PostEntity post) {
        if (this.post != null) {
            this.post.getPostImageList().remove(this);
        }
        this.post = post;
        post.getPostImageList().add(this);
    }

}
