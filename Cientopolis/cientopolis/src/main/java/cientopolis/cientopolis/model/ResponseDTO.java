package cientopolis.cientopolis.model;

/**
 * Created by nicolas.valentini on 4/7/17.
 */

public class ResponseDTO<T> {
    private String statusCode;
    private String message;
    private T data;
    private Integer errorCode;

    public static ResponseDTO NO_INTERNET_ERROR = new ResponseDTO("-1", "No internet connection", null,23);

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer message) {
        this.errorCode = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseDTO() {

    }

    public ResponseDTO(String statusCode, String message, T data, Integer errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
    }
}
