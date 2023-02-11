package TimeIsGold.TimeIsGold.domain.group;

import TimeIsGold.TimeIsGold.domain.groupMember.GroupMember;
import TimeIsGold.TimeIsGold.domain.otp.Otp;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    @Id @GeneratedValue
    @Column(name = "column_id")
    private Long id;

    private String name;

    //참여자 수
    private Long num;

    @Embedded
    private Otp groupOtp;

    @Embedded
    private TimetableForm compSet;



    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers = new ArrayList<>();

    public static Group create(String name, Long num) {

        Group group = new Group();
        Otp otp=Otp.createOtp();
        group.groupOtp = otp;
        group.num=num;
        group.name=name;

        return group;
    }

    public void changeOtp(){
        Otp otp = Otp.createOtp();
        groupOtp=otp;
    }

}
