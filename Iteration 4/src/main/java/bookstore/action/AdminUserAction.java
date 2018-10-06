package bookstore.action;

import java.util.List;

import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.model.result.SuccessMessage;
import bookstore.model.result.UserDetail;
import bookstore.service.AppService;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class AdminUserAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String nickname;
    private String avatar;
    private String balance;
    private String role;

    private String addresses;

    private Object retJson;

    private AppService appService;

    // Getters and setters

    public String getId() {
        return StringUtil.replaceNull(id);
    }

    public void setId(String id) {
        this.id = StringUtil.replaceNullAndTrim(id);
    }

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

    public String getBalance() {
        return StringUtil.replaceNull(balance);
    }

    public void setBalance(String balance) {
        this.balance = StringUtil.replaceNullAndTrim(balance);
    }

    public String getRole() {
        return StringUtil.replaceNull(role);
    }

    public void setRole(String role) {
        this.role = StringUtil.replaceNullAndTrim(role);
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

    public String allUsersView() throws Exception {
        User user = (User) session().getAttribute("user");
        if (user == null)
            return LOGIN;
        if (!user.isAdmin())
            return "forbidden";

        setPageTitle("E-bookstore managing system - user management");
        setViewProfile();
        return SUCCESS;
    }

    public String getAllUsers() throws Exception {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        retJson = appService.getAllUsers();
        return SUCCESS;
    }

    public String getUserDetail() throws Exception {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        UserDetail userDetail = appService.getUserDetailById(Integer.parseInt(getId()), true);
        if (userDetail == null) {
            retJson = new FailureMessage("The userID doesn't exist");
            return NONE;
        }

        retJson = userDetail;
        return SUCCESS;
    }

    public String addUser() throws Exception {
        User currentUser = (User) session().getAttribute("user");
        if (currentUser == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!currentUser.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        Validator vd = new Validator(getUsername(), "username");
        if (!vd.validateNotEmpty()
                || !vd.validatePattern("^[-_0-9a-zA-Z]{5,}$", "Username can only be composed with letters,numbers,- and _, and the minumum size is 5.")) {
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
        vd = new Validator(getNickname(), "nickname");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getBalance(), "balance");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^[0-9]+(?:\\.[0-9]{1,2})?$")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getRole(), "role");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^0|1$", "The value of role should only be 0 or 1.")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        if (appService.usernameExists(getUsername())) {
            retJson = new FailureMessage("username " + getUsername() + " already exist");
            return ERROR;
        }

        retJson = new SuccessMessage(
                appService.addUser(getUsername(), getPassword(), getNickname(), getAvatar(), getBalance(), getRole()));
        return SUCCESS;
    }

    public String updateUser() throws Exception {
        User currentUser = (User) session().getAttribute("user");
        if (currentUser == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!currentUser.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getUsername(), "username");
        if (!vd.validateNotEmpty()
                || !vd.validatePattern("^[-_0-9a-zA-Z]{5,}$", "Username can only be composed with letters,numbers,- and _, and the minumum size is 5.")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        if (!getPassword().equals("")) {
            vd = new Validator(getPassword(), "password");
            if (!vd.validatePattern("^.{6,}$", "Minimum size is 6.")) {
                retJson = vd.getFailureMessage();
                return ERROR;
            }
        }
        if (!getPassword().equals(getPasswordConfirm())) {
            retJson = new FailureMessage("password not consistent.");
            return ERROR;
        }
        vd = new Validator(getNickname(), "nickname");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getBalance(), "balance");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^[0-9]+(?:\\.[0-9]{1,2})?$")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getRole(), "role");
        if (!vd.validateNotEmpty() || !vd.validatePattern("^0|1$", "The value of role should only be 0 or 1.")) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        User user = appService.getUserById(Integer.parseInt(getId()));
        if (user == null) {
            retJson = new FailureMessage("The userId not exist");
            return NONE;
        }

        if (user.getId() == currentUser.getId() && getRole().equals("0")) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        if (!getUsername().equals(user.getUsername()) && appService.usernameExists(getUsername())) {
            retJson = new FailureMessage("username " + getUsername() + " already exists");
            return ERROR;
        }

        appService.updateUser(user, getUsername(), getPassword(), getNickname(), getAvatar(), getBalance(), getRole());

        retJson = new SuccessMessage();
        return SUCCESS;
    }

    public String deleteUser() throws Exception {
        User currentUser = (User) session().getAttribute("user");
        if (currentUser == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!currentUser.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        User user = appService.getUserById(Integer.parseInt(getId()));
        if (user == null) {
            retJson = new FailureMessage("The userID doesn't exist.");
            return NONE;
        }

        if (user.getId() == currentUser.getId()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        appService.deleteUser(user);

        retJson = new SuccessMessage();
        return SUCCESS;
    }

    public String getAddress() throws Exception {
        User currentUser = (User) session().getAttribute("user");
        if (currentUser == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!currentUser.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        int userId = Integer.parseInt(getId());
        if (appService.getUserById(userId) == null) {
            retJson = new FailureMessage("The userID doesn't exist。");
            return NONE;
        }

        try {
            retJson = appService.getUserAddress(userId);
            return SUCCESS;
        } catch (Exception e) {
            retJson = new FailureMessage("Can not connect to MongoDB");
            return ERROR;
        }
    }

    public String updateAddress() throws Exception {
        User currentUser = (User) session().getAttribute("user");
        if (currentUser == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!currentUser.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }

        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        int userId = Integer.parseInt(getId());
        if (appService.getUserById(userId) == null) {
            retJson = new FailureMessage("The userID doesn't exist。");
            return NONE;
        }

        List<String> addressArray = StringUtil.JSONStringArrayParse(addresses);
        if (addressArray == null) {
            retJson = new FailureMessage("Address form is not correct.");
            return ERROR;
        }

        appService.updateUserAddress(userId, addressArray);

        retJson = new SuccessMessage();
        return SUCCESS;
    }
}
