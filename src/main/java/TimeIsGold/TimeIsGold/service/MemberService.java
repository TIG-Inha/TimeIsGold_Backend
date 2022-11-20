package TimeIsGold.TimeIsGold.service;

import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.domain.Otp;
import TimeIsGold.TimeIsGold.domain.Timetable;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import TimeIsGold.TimeIsGold.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final OtpRepository otpRepository;

    public boolean checkDuplicatedUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    @Transactional
    public Long join(Member member) {

        //빈 시간표 생성해서 member에 넣어줌
        Timetable timetable = Timetable.createTimetable();
        member.setTimetable(timetable);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findMemberByOtpCode(String otpCode) {
        Otp findOtp = otpRepository.findByOtpCode(otpCode);
        return findOtp.getMember();
    }

}
