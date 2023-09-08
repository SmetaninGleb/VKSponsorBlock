package ru.vksponsorblock.VKSponsorBlock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<VideoSegment> createdSegments;

    @ManyToMany(mappedBy = "likedUsers", fetch = FetchType.LAZY)
    private List<VideoSegment> likedSegments;

    @ManyToMany(mappedBy = "dislikedUsers", fetch = FetchType.LAZY)
    private List<VideoSegment> dislikedSegments;
}
