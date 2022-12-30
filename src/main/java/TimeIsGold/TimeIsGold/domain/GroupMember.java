package TimeIsGold.TimeIsGold.domain;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
public class GroupMember {

    @Id
    @GeneratedValue
    @Column(name = "group_member_id")
    private Long id;


    //cascade 속성
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;



}
