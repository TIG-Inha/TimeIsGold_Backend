package TimeIsGold.TimeIsGold.api.member;

import TimeIsGold.TimeIsGold.api.login.dto.LoginRequestDto;
import TimeIsGold.TimeIsGold.api.login.dto.LoginResponseDto;
import TimeIsGold.TimeIsGold.api.member.dto.MemberRegisterRequestDto;
import TimeIsGold.TimeIsGold.api.member.dto.MemberRegisterResponseDto;
import TimeIsGold.TimeIsGold.api.member.dto.UserIdCheckResponseDto;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.member.MemberRepository;
import TimeIsGold.TimeIsGold.exception.login.LoginException;
import TimeIsGold.TimeIsGold.exception.member.MemberRegisterException;
import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import TimeIsGold.TimeIsGold.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //sign-up
    @PostMapping("/register")
    public ResponseEntity<MemberRegisterResponseDto> registerMember(@RequestBody @Valid MemberRegisterRequestDto request) {

        MemberRegisterResponseDto dto = new MemberRegisterResponseDto();


        //중복체크 했는지 확인
        if (request.getDuplicatedIdCheck().equals("false")) {
            throw new MemberRegisterException("ID중복확인안함");
        }

        //pw, pwCheck 같은지 확인
        if (!request.pw.equals(request.pwCheck)) {
            throw new MemberRegisterException("패스워드불일치");
        }

        //회원가입 진행
        Member member = Member.create(request.getId(), request.getPw(), request.getName());
        memberService.join(member);
        dto.setHttpStatus(HttpStatus.OK);
        dto.setMemberId(member.getId());

        return new ResponseEntity<>(dto, dto.getHttpStatus());
    }

    @GetMapping("/register")
    public ResponseEntity<UserIdCheckResponseDto> checkDuplicatedId(@RequestParam String userId) {


        boolean isExistUserId = memberRepository.existsByUserId(userId);
//        Optional<Member> optionalMember = memberRepository.findMemberByUserId(userId);

        UserIdCheckResponseDto dto = new UserIdCheckResponseDto();

        if (!isExistUserId) {
            dto.setHttpStatus(HttpStatus.OK);
            dto.setMessage("사용가능한 id");
        } else {
            // 중복된 id가 존재하는 경우 http status code를 뭘로 해야하는지? 403 Forbidden or 409 Conflict?
            dto.setHttpStatus(HttpStatus.CONFLICT);
            dto.setMessage("사용중인 id");
        }
        return new ResponseEntity<>(dto, dto.getHttpStatus());
    }



}
