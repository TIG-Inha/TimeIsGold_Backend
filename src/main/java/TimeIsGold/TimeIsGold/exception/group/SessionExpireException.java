package TimeIsGold.TimeIsGold.exception.group;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionExpireException extends RuntimeException {
    public SessionExpireException(String message){
        super(message);
    }

}
