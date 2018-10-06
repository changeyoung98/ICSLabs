package bookstore.action;

import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.model.result.SuccessMessage;
import bookstore.service.AppService;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class CartAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    
    private String bookId;
    private String quantity;
    private String itemId;
    
    private Object retJson;
    
    private AppService appService;
    
    // Getters and setters
    
    public String getBookId() {
        return StringUtil.replaceNull(bookId);
    }

    public void setBookId(String bookId) {
        this.bookId = StringUtil.replaceNullAndTrim(bookId);
    }

    public String getQuantity() {
        return StringUtil.replaceNull(quantity);
    }

    public void setQuantity(String quantity) {
        this.quantity = StringUtil.replaceNullAndTrim(quantity);
    }
    
    public String getItemId() {
        return StringUtil.replaceNull(itemId);
    }

    public void setItemId(String itemId) {
        this.itemId = StringUtil.replaceNullAndTrim(itemId);
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
    
    public String cartView() {
        if (session().getAttribute("user") == null) {
            return LOGIN;
        }
        
        setPageTitle("My shopping cart");
        setViewProfile();
        return SUCCESS;
    }
    
    public String getCartCount() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        retJson = new SuccessMessage(appService.getUserCartCount(user.getId()));
        return SUCCESS;
    }
    
    
    public String addToCart() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        Validator vd = new Validator(getBookId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getQuantity(), "quantity");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        int bookId = Integer.parseInt(getBookId());
        
        if (appService.getBookById(bookId) == null) {
            retJson = new FailureMessage("The bookID doesn't exist");
            return NONE;
        }
        
        if (appService.userCartHasBook(user.getId(), bookId)) {
            retJson = new FailureMessage("The book is already in the cart.");
            return ERROR;
        }
        
        appService.addItemToCart(user, bookId, Integer.parseInt(getQuantity()));
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String getCartDetail() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        retJson = appService.getUserCartItems(user.getId());
        return SUCCESS;
    }
    
    public String updateCartItem() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        Validator vd = new Validator(getItemId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        vd = new Validator(getQuantity(), "quantity");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        OrderItem item = appService.getOrderItemById(Integer.parseInt(getItemId()));
        if (item == null) {
            retJson = new FailureMessage("The order item doesn't exist.");
            return NONE;
        }
        
        Order order = appService.getOrderById(item.getOrderId());
        
        if (order.getUserId() != user.getId()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        if (order.getIsPaid()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        appService.updateOrderItem(item, Integer.parseInt(getQuantity()));
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String deleteCartItem() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        Validator vd = new Validator(getItemId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        OrderItem item = appService.getOrderItemById(Integer.parseInt(getItemId()));
        if (item == null) {
            retJson = new FailureMessage("The order itm doesn't exist.");
            return NONE;
        }
        
        Order order = appService.getOrderById(item.getOrderId());
        
        if (order.getUserId() != user.getId()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        if (order.getIsPaid()) {
            retJson = new FailureMessage("Forbidden");
            return "forbidden";
        }
        
        appService.deleteOrderItem(item);
        
        retJson = new SuccessMessage();
        return SUCCESS;
    }
    
    public String getCartSummary() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        retJson = appService.getUserCartSummary(user.getId());
        return SUCCESS;
    }
    
    public String payCart() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        retJson = appService.payCart(user);
        return SUCCESS;
    }

}
