package uniroma1.fiware.ofnic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="No such Order")
public class InternalErrorException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InternalErrorException(String message) {
        super(message);
    }

}
