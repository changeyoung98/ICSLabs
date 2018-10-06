package bookstore.action;

import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.model.result.SuccessMessage;
import bookstore.service.AppService;
import bookstore.util.PasswordUtil;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class AuthAction extends BaseAction {
    
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String passwordConfirm;
    
    private Object retJson;
    
    private AppService appService;
    
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
    
    public String doRegister() {
        Validator vd = new Validator(getUsername(), "username");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^[-_0-9a-zA-Z]{5,}$", "Username can only be composed with letters,numbers,- and _, and the minumum size is 5.")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getPassword(), "password");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^.{6,}$", "Mininum size is 6.")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getPasswordConfirm(), "certificate password");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        if (!getPassword().equals(getPasswordConfirm())) {
            retJson = new FailureMessage("password not consistent");
            return ERROR;
        }
        
        if (appService.usernameExists(getUsername())) {
            retJson = new FailureMessage("username " + getUsername() + " already exists");
            return ERROR;
        }
            
        Integer newUserId = appService.addUser(getUsername(), getPassword(), getUsername(), "", "0", "0");
        
        session().setAttribute("user", appService.getUserById(newUserId));
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String doLogin() {
        Validator vd = new Validator(getUsername(), "username");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getPassword(), "password");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        User user = appService.getUserByUsername(getUsername());
        if (user == null) {
            retJson = new FailureMessage("username or password error");
            return ERROR;
        }
        
        String hash = user.getPassword();
        if (!PasswordUtil.checkPassword(getPassword(), hash)) {
            retJson = new FailureMessage("username or password error");
            return ERROR;
        }
        
        session().setAttribute("user", user);
        
        retJson = new SuccessMessage(user.isAdmin());
        return SUCCESS;
    }
    
    public String doLogout() {
        session().removeAttribute("user");
        return SUCCESS;
    }
    
}
