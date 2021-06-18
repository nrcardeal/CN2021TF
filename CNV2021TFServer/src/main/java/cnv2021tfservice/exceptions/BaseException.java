package cnv2021tfservice.exceptions;

public class BaseException extends Throwable{
    private final String msg;

    public BaseException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
