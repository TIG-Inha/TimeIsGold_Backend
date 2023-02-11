package TimeIsGold.TimeIsGold.domain.member;

import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.otp.Otp;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String pw;

    private String name;

    @Embedded
    private Otp otp;

    @OneToMany(mappedBy = "member")
    private List<Timetable> timetables = new ArrayList<>();

    // GroupMember 클래스 네이밍이 헛갈릴 여지 있을 거같음..
    // 일단 단방향으로 설정하고 추후 양방향 관계 필요 시 수정

    @OneToMany(mappedBy = "member")
    private List<GroupMember> groupMembers = new ArrayList<>();

    public static Member create(String userId, String pw, String name) {

        Member member = new Member();
        member.setUserId(userId);
        member.setPw(pw);
        member.setName(name);
        return member;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    private void setPw(String pw) {
        this.pw = pw;
    }

    private void setName(String name) {
        this.name = name;
    }
}
