package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.controller.apiResponse.ErrorResult;
import TimeIsGold.TimeIsGold.controller.memberRegisterDto.*;
import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.exception.memberRegister.LoginException;
import TimeIsGold.TimeIsGold.exception.memberRegister.MemberRegisterException;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import TimeIsGold.TimeIsGold.repository.SessionConstants;
import TimeIsGold.TimeIsGold.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
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
        Member member = Member.createMember(request.getId(), request.getPw(), request.getName());
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request, HttpServletRequest hsRequest){
        LoginResponseDto dto = new LoginResponseDto();

        //dto.setHttpStatus(HttpStatus.CONFLICT);
        //dto.setMessage("id, pw 오류");

        Optional<Member> member=memberService.login(request.getId(), request.getPw());

        if(member.isEmpty()){
            throw new LoginException("id, pw 불일치");
        }
        else{
            HttpSession session = hsRequest.getSession();
            session.setAttribute(SessionConstants.LOGIN_MEMBER, member);
            session.setMaxInactiveInterval(60);

            dto.setHttpStatus(HttpStatus.OK);
            dto.setMessage("성공했습니다.");
        }

        return new ResponseEntity<>(dto, dto.getHttpStatus());
    }

    @GetMapping("/logout")
    public ResponseEntity<LoginResponseDto> logout(HttpServletRequest HsRequest){
        HttpSession session = HsRequest.getSession(false);
        LoginResponseDto dto = new LoginResponseDto();

        if(session!=null){
            session.invalidate();
        }

        dto.setMessage("Session 삭제 완료");
        dto.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(dto, dto.getHttpStatus());
    }

}
