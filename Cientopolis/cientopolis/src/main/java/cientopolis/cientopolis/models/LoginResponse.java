package cientopolis.cientopolis.models;
import java.io.Serializable;

public class LoginResponse implements Serializable {
    private Boolean exists;
    private String msg;
    private String redirectUrl;
    private ProfileModel userInformation;

    public LoginResponse(Boolean exists, String redirectUrl, String msg, ProfileModel userInformation) {
        this.exists = exists;
        this.msg = msg;
        this.redirectUrl = redirectUrl;
        this.userInformation = userInformation;
    }


    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ProfileModel getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(ProfileModel userInformation) {
        this.userInformation = userInformation;
    }
}
