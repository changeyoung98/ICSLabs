package bookstore.action;

import java.util.List;

import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.model.result.SuccessMessage;
import bookstore.service.AppService;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class ProfileAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String passwordConfirm;
    private String nickname;
    private String avatar;  
    
    private Object retJson;
    
    private AppService appService;

    private String addresses;
    
    // Getters and setters

    public String getUsername() {
        return StringUtil.replaceNull(username);
    }

    public void setUsername(String username) {
        this.username = StringUtil.replaceNullAndTrim(username);
    }

    public String getPassword() {
        return StringUtil.replaceNull(password);
    }

    public void setPassword(String password) {
        this.password = StringUtil.replaceNull(password);
    }

    public String getPasswordConfirm() {
        return StringUtil.replaceNull(passwordConfirm);
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = StringUtil.replaceNull(passwordConfirm);
    }

    public String getNickname() {
        return StringUtil.replaceNull(nickname);
    }

    public void setNickname(String nickname) {
        this.nickname = StringUtil.replaceNullAndTrim(nickname);
    }

    public String getAvatar() {
        return StringUtil.replaceNull(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = StringUtil.replaceNullAndTrim(avatar);
    }
    
    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
    
    public Object getRetJson() {
        return retJson;
    }

    public void setRetJson(Object retJson) {
        this.retJson = retJson;
    }
    
    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }
    
    
    // Actions
    
    public String getMyProfile() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        retJson = appService.getUserDetailById(user.getId(), false);
        return SUCCESS;
    }
    
    public String updateMyProfile() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        Validator vd = new Validator(getUsername(), "username");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^[-_0-9a-zA-Z]{5,}$", "Username can only be composed with letters,numbers,- and _, and the minumum size is 5.")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        if (!getPassword().equals("")) {
            vd = new Validator(getPassword(), "password");
            if (!vd.validatePattern("^.{6,}$", "Mininum size is 6.")) {
                retJson = vd.getFailureMessage();
                return ERROR;
            }
        }
        if (!getPassword().equals(getPasswordConfirm())) {
            retJson = new FailureMessage("Not consistent with password");
            return ERROR;
        }
        vd = new Validator(getNickname(), "nickname");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        if (!getUsername().equals(user.getUsername()) && appService.usernameExists(getUsername())) {
            retJson = new FailureMessage("Username " + getUsername() + " already exist");
            return ERROR;
        }
        
        appService.updateUser(user, getUsername(), getPassword(), getNickname(), getAvatar());
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String getMyAddress() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        try {
            retJson = appService.getUserAddress(user.getId());
            return SUCCESS;
        } catch (Exception e) {
            retJson = new FailureMessage("Can not connect to MongoDB");
            return ERROR;
        }
        
    }
    
    public String updateMyAddress() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        List<String> addressArray = StringUtil.JSONStringArrayParse(addresses);
        if (addressArray == null) {
            retJson = new FailureMessage("Address form not correct.");
            return ERROR;
        }
        
        appService.updateUserAddress(user.getId(), addressArray);
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }

}
