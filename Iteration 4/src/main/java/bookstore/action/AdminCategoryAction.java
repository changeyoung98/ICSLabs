package bookstore.action;

import bookstore.model.Book;
import bookstore.model.BookCategory;
import bookstore.model.Category;
import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.model.result.SuccessMessage;
import bookstore.service.AppService;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class AdminCategoryAction extends BaseAction {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String bookId;
    private String name;
    
    private Object retJson;
    
    private AppService appService;
    
    // Getters and setters
    
    public String getId() {
        return StringUtil.replaceNull(id);
    }

    public void setId(String id) {
        this.id = StringUtil.replaceNullAndTrim(id);
    }
    
    public String getBookId() {
        return StringUtil.replaceNull(bookId);
    }

    public void setBookId(String bookId) {
        this.bookId = StringUtil.replaceNullAndTrim(bookId);
    }
    
    public String getName() {
        return StringUtil.replaceNull(name);
    }

    public void setName(String name) {
        this.name = StringUtil.replaceNullAndTrim(name);
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

    public String allCategoriesView() {
        User user = (User) session().getAttribute("user");
        if (user == null)
            return LOGIN;
        if (!user.isAdmin())
            return "forbidden";
        
        setPageTitle("E-bookstore managing system - category management");
        setViewProfile();
        return SUCCESS;
    }
    
    public String getAllCategories() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        retJson = appService.getAllCategories(true);
        return SUCCESS;
    }
    
    public String getCategoryDetail() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        Validator vd = new Validator(getId(), "ID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        Category category = appService.getCategoryById(Integer.parseInt(getId()));
        if (category == null) {
            retJson = new FailureMessage("The categoryID doesn't exist。");
            return NONE;
        }
        
        retJson = appService.getCategoryBooks(Integer.parseInt(getId()));
        return SUCCESS;
    }
    
    public String addCategory() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        Validator vd = new Validator(getName(), "category");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        retJson = new SuccessMessage(appService.addCategory(getName()));
        return SUCCESS;
    }
    
    public String updateCategory() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        Validator vd = new Validator(getId(), "ID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getName(), "category");
        if (!vd.validateNotEmpty()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }

        Category category = appService.getCategoryById(Integer.parseInt(getId()));
        if (category == null) {
            retJson = new FailureMessage("The categoryID doesn't exist。");
            return NONE;
        }
        
        appService.updateCategory(category, getName());
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String deleteCategory() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        Validator vd = new Validator(getId(), "ID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        Category category = appService.getCategoryById(Integer.parseInt(getId()));
        if (category == null) {
            retJson = new FailureMessage("The categoryID doesn't exist。");
            return NONE;
        }
        
        appService.deleteCategory(category);
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String addBookToCategory() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        Validator vd = new Validator(getId(), "categoryID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getBookId(), "bookID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        Category category = appService.getCategoryById(Integer.parseInt(getId()));
        if (category == null) {
            retJson = new FailureMessage("The categoryID doesn't exist。");
            return NONE;
        }
        
        Book book = appService.getBookById(Integer.parseInt(getBookId()));
        if (book == null) {
            retJson = new FailureMessage("The bookID doesn't exist。");
            return NONE;
        }
        
        BookCategory bc = appService.findBC(category.getId(), book.getId());
        if (bc != null) {
            retJson = new FailureMessage("The book already belongs to the category。");
            return ERROR;
        }
        
        appService.addBC(category.getId(), book.getId());
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String deleteBookFromCategory() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("");
            return LOGIN;
        }
        if (!user.isAdmin()) {
            retJson = new FailureMessage("");
            return "forbidden";
        }
        
        Validator vd = new Validator(getId(), "relatedID");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        BookCategory bc = appService.getBCById(Integer.parseInt(getId()));
        if (bc == null) {
            retJson = new FailureMessage("The relatedID doesn't exist。");
            return NONE;
        }
        
        appService.deleteBC(bc);
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
}
