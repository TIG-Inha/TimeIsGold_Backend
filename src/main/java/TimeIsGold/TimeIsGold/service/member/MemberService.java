package TimeIsGold.TimeIsGold.service.member;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableForm;
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



}

