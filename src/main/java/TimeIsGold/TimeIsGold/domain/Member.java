package TimeIsGold.TimeIsGold.domain;

import TimeIsGold.TimeIsGold.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String pw;

    private String name;

    @OneToOne
    @JoinColumn(name = "otp_id")
    private Otp otp;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    public static Member createMember(String userId, String pw, String name) {

        Member member = new Member();
        member.setUserId(userId);
        member.setPw(pw);
        member.setName(name);
        return member;
    }


}
