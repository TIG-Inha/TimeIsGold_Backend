package TimeIsGold.TimeIsGold.domain.group;

import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.Otp;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    @Id @GeneratedValue
    @Column(name = "column_id")
    private Long id;

    private String name;

    @Embedded
    private Otp groupOtp;

    @Embedded
    private TimetableForm compSet;

    @Enumerated(EnumType.STRING)
    private Position position;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers = new ArrayList<>();

    public static Group create(String name) {

        Group group = new Group();

        return group;
    }

}
