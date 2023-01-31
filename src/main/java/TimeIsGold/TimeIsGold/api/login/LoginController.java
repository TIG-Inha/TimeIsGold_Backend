package TimeIsGold.TimeIsGold.api.login;

import TimeIsGold.TimeIsGold.api.login.dto.LoginRequestDto;
import TimeIsGold.TimeIsGold.api.login.dto.LoginResponseDto;
import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.exception.login.LoginException;
import TimeIsGold.TimeIsGold.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request, HttpServletRequest hsRequest){
        LoginResponseDto dto = new LoginResponseDto();

        //dto.setHttpStatus(HttpStatus.CONFLICT);
        //dto.setMessage("id, pw 오류");

        Member member=loginService.login(request.getId(), request.getPw());

        if(member==null){
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
