package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Timetable;
import TimeIsGold.TimeIsGold.domain.embeded.TimetableForm;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public Long join(Member member) {

        //빈 시간표 생성해서 member에 넣어줌
        TimetableForm timetableForm = TimetableForm.builder().build();

        Timetable timetable = Timetable.createTimetable(timetableForm);
        member.setTimetable(timetable);
        memberRepository.save(member);
        return member.getId();

    }

//    public Member findMemberByOtpCode(String otpCode) {
//        Otp findOtp = otpRepository.findByOtpCode(otpCode);
//        return findOtp.getMember();
//    }

    public boolean checkDuplicatedUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }


    public Member login(String userId, String pw){
        return memberRepository.findByUserIdAndPw(userId,pw);
    }


}

