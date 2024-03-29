package TimeIsGold.TimeIsGold.domain.groupMember;

import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.group.Position;
import TimeIsGold.TimeIsGold.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember {

    @Id
    @GeneratedValue
    @Column(name = "group_member_id")
    private Long id;


    //cascade 속성 미정
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Long timetable_id;

    public static GroupMember create(Member member, Group group, Position position, Long timetable_id) {

        GroupMember groupMember = new GroupMember();
        groupMember.setMember(member);
        groupMember.setGroup(group);
        groupMember.setPosition(position);
        groupMember.setTimetable_id(timetable_id);

        return groupMember;
    }

    private void setPosition(Position position1) {
        position=position1;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setTimetable_id(Long id){this.timetable_id=id;}

    public Long getTimetable_id() {
        return timetable_id;
    }
}
