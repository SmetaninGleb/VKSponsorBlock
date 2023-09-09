package ru.vksponsorblock.VKSponsorBlock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vksponsorblock.VKSponsorBlock.utils.SegmentType;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "video_segment")
public class VideoSegment extends BaseEntity {

    @Column(name = "video_id")
    @NotNull
    private String videoId;

    @Column(name = "segment_type")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private SegmentType segmentType;

    @Column(name = "start_time_seconds")
    @NotNull
    private Float startTimeSeconds;

    @Column(name = "end_time_seconds")
    @NotNull
    private Float endTimeSeconds;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @NotNull
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "liked_users",
            joinColumns = {@JoinColumn(name = "video_segment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> likedUsers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "disliked_users",
            joinColumns = {@JoinColumn(name = "video_segment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> dislikedUsers;
}
