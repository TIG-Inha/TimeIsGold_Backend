package TimeIsGold.TimeIsGold;
import TimeIsGold.TimeIsGold.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1() {
            em.persist(createMember(0));
            em.persist(createMember(1));
            em.persist(createMember(2));
            em.persist(createMember(3));
        }

        private Member createMember(int n) {
            return Member.createMember("id" + n, "1234", "user" + n);
        }

    }

}
