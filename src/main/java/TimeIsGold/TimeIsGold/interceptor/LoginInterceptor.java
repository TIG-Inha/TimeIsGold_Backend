package TimeIsGold.TimeIsGold.interceptor;

import TimeIsGold.TimeIsGold.api.login.SessionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConstants.LOGIN_MEMBER) == null) {
            //세션이 없거나 아직 로그인 안한 상태 -> 로그인 화면으로 리다이렉션
            log.error("unauthorized client");
//            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
        log.info("authorized client: {}", session.getAttribute(SessionConstants.LOGIN_MEMBER));
        return true;
    }

}
