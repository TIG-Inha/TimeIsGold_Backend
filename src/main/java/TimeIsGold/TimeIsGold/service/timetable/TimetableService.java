package TimeIsGold.TimeIsGold.service.timetable;

import TimeIsGold.TimeIsGold.domain.member.Member;
import TimeIsGold.TimeIsGold.domain.timetable.Timetable;
import TimeIsGold.TimeIsGold.domain.timetable.TimetableRepository;
import TimeIsGold.TimeIsGold.exception.timetable.DuplicatedNameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableService {

    private final TimetableRepository timetableRepository;

    @Transactional
    public Long create(Member member, String timetableName) {

        if(isExistDuplicatedName(member, timetableName)){
            throw new DuplicatedNameException("이미 존재하는 시간표 이름입니다.");
        }

        Timetable timetable = Timetable.create(member, timetableName);

        Timetable saveTimetable = timetableRepository.save(timetable);

        return saveTimetable.getId();
    }

    private Boolean isExistDuplicatedName(Member member, String timetableName) {
        return timetableRepository.existsByMemberAndName(member, timetableName);
    }
}
