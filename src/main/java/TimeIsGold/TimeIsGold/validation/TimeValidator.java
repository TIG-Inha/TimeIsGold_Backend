package TimeIsGold.TimeIsGold.validation;

import java.util.regex.Pattern;

public class TimeValidator {

    /**
     * pattern: 적용할 패턴
     * value: 비교할 문자열
     */
    // pattern: regex 로 표현한 0000~2359 시각 검증패턴
    private static final String pattern = "^([01]\\d|2[0-3])([0-5]\\d)$";

    public static boolean matches(String value) {

        return Pattern.matches(pattern, value);
    }
}
