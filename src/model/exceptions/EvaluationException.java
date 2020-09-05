package model.exceptions;

//When name of variable does not exist
public class EvaluationException extends RuntimeException {
    public EvaluationException(String message) {
        super(message);
    }
}
