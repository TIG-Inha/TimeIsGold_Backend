package TimeIsGold.TimeIsGold.service.login;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Optional<Member> login(String userId, String pw){


        return memberRepository.findByUserIdAndPw(userId,pw);
    }

}
