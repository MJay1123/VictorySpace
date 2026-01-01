package com.victoryspace.vics.member.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "profile")
    private String profile;

    @Column(name = "point")
    private Integer point;

    @Column(name = "role")
    private String role;

    public static MemberEntity create(
            String name,
            String gender,
            LocalDate birthday,
            String nickname,
            String password,
            String email
    ) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(name);
        memberEntity.setGender(gender);
        memberEntity.setBirthday(birthday);
        memberEntity.setNickname(nickname);
        memberEntity.setPassword(password);
        memberEntity.setEmail(email);
        memberEntity.setCreatedAt(LocalDateTime.now());
        memberEntity.setGradeId(1);
        memberEntity.setPoint(1000);
        memberEntity.setRole("ROLE_USER");
        return memberEntity;
    }

    public void update(String nickname, String password, String profile){
        this.nickname = nickname;
        this.password = password;
        this.profile = profile;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
