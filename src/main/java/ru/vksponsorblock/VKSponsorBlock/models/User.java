package ru.vksponsorblock.VKSponsorBlock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vksponsorblock.VKSponsorBlock.utils.RoleType;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<VideoSegment> createdSegments;

    @ManyToMany(mappedBy = "likedUsers", fetch = FetchType.LAZY)
    private List<VideoSegment> likedSegments;

    @ManyToMany(mappedBy = "dislikedUsers", fetch = FetchType.LAZY)
    private List<VideoSegment> dislikedSegments;
}
