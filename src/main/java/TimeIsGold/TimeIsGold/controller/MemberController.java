package TimeIsGold.TimeIsGold.controller;

import TimeIsGold.TimeIsGold.controller.apiResponse.ErrorResult;
import TimeIsGold.TimeIsGold.controller.memberRegisterDto.MemberRegisterRequestDto;
import TimeIsGold.TimeIsGold.controller.memberRegisterDto.MemberRegisterResponseDto;
import TimeIsGold.TimeIsGold.controller.memberRegisterDto.UserIdCheckResponseDto;
import TimeIsGold.TimeIsGold.domain.Member;
import TimeIsGold.TimeIsGold.exception.memberRegister.DuplicatedUserIdException;
import TimeIsGold.TimeIsGold.exception.memberRegister.PasswordMismatchException;
import TimeIsGold.TimeIsGold.repository.MemberRepository;
import TimeIsGold.TimeIsGold.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //sign-up
    @PostMapping("/register")
    public ResponseEntity<MemberRegisterResponseDto> registerMember(@RequestBody @Valid MemberRegisterRequestDto request){

        MemberRegisterResponseDto dto = new MemberRegisterResponseDto();

        //중복체크 했는지 확인
        if(request.getDuplicatedIdCheck().equals("false")){
            throw new DuplicatedUserIdException("ID중복확인안함");
        }

        //pw, pwCheck 같은지 확인
        if(!request.pw.equals(request.pwCheck)){
            throw new PasswordMismatchException("패스워드불일치");
        }

        //userId 중복체크, pw확인 체크
        Member member = Member.createMember(request.getId(), request.getPw(), request.getName());
        memberService.join(member);
        dto.setHttpStatus(HttpStatus.OK);
        dto.setMemberId(member.getId());

        return new ResponseEntity<>(dto, dto.getHttpStatus());
    }

    @GetMapping("/register")
    public ResponseEntity<UserIdCheckResponseDto> checkDuplicatedId(@RequestParam Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        UserIdCheckResponseDto dto = new UserIdCheckResponseDto();

        if(optionalMember.isEmpty()){
            dto.setHttpStatus(HttpStatus.OK);
            dto.setMessage("사용가능한 id");
        }
        else{
            // 중복된 id가 존재하는 경우 http status code를 뭘로 해야하는지? 403 Forbidden or 409 Conflict?
            dto.setHttpStatus(HttpStatus.CONFLICT);
            dto.setMessage("사용중인 id");
        }
        return new ResponseEntity<>(dto, dto.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> duplicatedUserIdExHandle(DuplicatedUserIdException e) {

        ErrorResult errorResult = new ErrorResult("ID-중복확인-안함", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> passwordMismatchExHandle(PasswordMismatchException e) {

        ErrorResult errorResult = new ErrorResult("패스워드-불일치", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

}
