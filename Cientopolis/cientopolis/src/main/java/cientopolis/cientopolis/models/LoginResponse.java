package cientopolis.cientopolis.models;
import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String msg;
    private String redirectUrl;
    private ProfileModel userInformation;


    public LoginResponse() {

    }

    public LoginResponse(String msg, String redirectUrl, ProfileModel userInformation) {
        this.msg = msg;
        this.redirectUrl = redirectUrl;
        this.userInformation = userInformation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public ProfileModel getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(ProfileModel userInformation) {
        this.userInformation = userInformation;
    }
}
