package bookstore.action;

import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.service.AppService;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class AdminStatAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    
    private String categoryId;
    private String bookId;
    private String username;
    private String startDate;
    private String endDate;
    
    private Object retJson;
    
    private AppService appService;
    
    // Getters and setters
    
    public String getCategoryId() {
        return StringUtil.replaceNull(categoryId);
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = StringUtil.replaceNullAndTrim(categoryId);
    }

    public String getBookId() {
        return StringUtil.replaceNull(bookId);
    }

    public void setBookId(String bookId) {
        this.bookId = StringUtil.replaceNullAndTrim(bookId);
    }

    public String getUsername() {
        return StringUtil.replaceNull(username);
    }

    public void setUsername(String username) {
        this.username = StringUtil.replaceNullAndTrim(username);
    }

    public String getStartDate() {
        return StringUtil.replaceNull(startDate);
    }

    public void setStartDate(String startDate) {
        this.startDate = StringUtil.replaceNullAndTrim(startDate);
    }

    public String getEndDate() {
        return StringUtil.replaceNull(endDate);
    }

    public void setEndDate(String endDate) {
        this.endDate = StringUtil.replaceNullAndTrim(endDate);
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
    
    public String statisticsPage() {
        User user = (User) session().getAttribute("user");
        if (user == null)
            return LOGIN;
        if (!user.isAdmin())
            return "forbidden";
        
        setPageTitle("E-bookstore managing system - sales calculate");
        setViewProfile();
        request().setAttribute("categories", appService.getAllCategories(false));
        return SUCCESS;
    }
    
    public String statCategory() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        Validator vd = new Validator(getCategoryId(), "categoryID");
        if (!vd.validateNotEmpty() || !vd.validateNonNegativeInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getStartDate(), "date of beginning");
        if (!vd.validateNotEmpty() || !vd.validateDate()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getEndDate(), "date of end");
        if (!vd.validateNotEmpty() || !vd.validateDate()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        int statCategoryId = Integer.parseInt(getCategoryId());
        
        if (statCategoryId > 0 && appService.getCategoryById(statCategoryId) == null) {
            retJson = new FailureMessage("The categoryID doesn't exist。");
            return NONE;
        }
        
        retJson = appService.statCategory(statCategoryId, getStartDate(), getEndDate());
        return SUCCESS;
    }
    
    public String statBook() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        Validator vd = new Validator(getBookId(), "bookID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getStartDate(), "date of beginning");
        if (!vd.validateNotEmpty() || !vd.validateDate()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getEndDate(), "date of end");
        if (!vd.validateNotEmpty() || !vd.validateDate()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        int statBookId = Integer.parseInt(getBookId());
        
        if (appService.getBookById(statBookId) == null) {
            retJson = new FailureMessage("The bookID doesn't exist。");
            return NONE;
        }
        
        retJson = appService.statBook(statBookId, getStartDate(), getEndDate());
        return SUCCESS;
    }
    
    public String statUser() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        Validator vd = new Validator(getUsername(), "username");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getStartDate(), "date of beginning");
        if (!vd.validateNotEmpty() || !vd.validateDate()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getEndDate(), "date of end");
        if (!vd.validateNotEmpty() || !vd.validateDate()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        if (appService.getUserByUsername(getUsername()) == null) {
            retJson = new FailureMessage("The username doesn't exist。");
            return NONE;
        }
        
        retJson = appService.statUser(getUsername(), getStartDate(), getEndDate());
        return SUCCESS;
    }
    
}
