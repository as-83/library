package exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParamNotExistsException extends RuntimeException {
    public ParamNotExistsException(String message) {
        super(message);
    }
}
