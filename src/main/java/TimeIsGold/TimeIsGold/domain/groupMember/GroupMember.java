package TimeIsGold.TimeIsGold.domain.groupMember;

import TimeIsGold.TimeIsGold.domain.group.Group;
import TimeIsGold.TimeIsGold.domain.member.Member;

import javax.persistence.*;

@Entity
public class GroupMember {

    @Id
    @GeneratedValue
    @Column(name = "group_member_id")
    private Long id;


    //cascade 속성 미정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;



}
