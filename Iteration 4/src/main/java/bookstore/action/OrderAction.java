package bookstore.action;

import bookstore.model.Order;
import bookstore.model.User;
import bookstore.model.result.FailureMessage;
import bookstore.model.result.SuccessMessage;
import bookstore.service.AppService;
import bookstore.util.StringUtil;
import bookstore.util.Validator;

public class OrderAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private Object retJson;
    
    private AppService appService;
    
    // Getters and setters
    
    public String getId() {
        return StringUtil.replaceNull(id);
    }

    public void setId(String id) {
        this.id = StringUtil.replaceNullAndTrim(id);
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
    
    public String ordersView() {
        if (session().getAttribute("user") == null) {
            return LOGIN;
        }
        
        setPageTitle("My history order");
        setViewProfile();
        return SUCCESS;
    }
    
    public String getAllOrders() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        retJson = appService.getUserOrders(user.getId());
        return SUCCESS;
    }
    
    public String getOrderDetail() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        Order order = appService.getOrderById(Integer.parseInt(getId()));
        if (order == null) {
            retJson = new FailureMessage("The order number doesn't exist.");
            return NONE;
        }
        
        if (order.getUserId() != user.getId()) {
            retJson = new FailureMessage("Forbidden.");
            return "forbidden";
        }
        
        if (order.getId() == appService.getUserCart(user.getId()).getId()) {
            retJson = new FailureMessage("Forbidden."); // Cannot get cart detail by this API.
            return "forbidden";
        }
        
        retJson = appService.getOrderItemsByOrder(order, false);
        return SUCCESS;
    }
    
    public String deleteOrder() {
        User user = (User) session().getAttribute("user");
        if (user == null) {
            retJson = new FailureMessage("Please Login");
            return LOGIN;
        }
        
        Validator vd = new Validator(getId(), "number");
        if (!vd.validateNotEmpty() || !vd.validatePositiveInt()) {
            retJson = vd.getFailureMessage();
            return ERROR;
        }
        
        Order order = appService.getOrderById(Integer.parseInt(getId()));
        if (order == null) {
            retJson = new FailureMessage("The order number doesn't exist.");
            return NONE;
        }
        
        if (order.getUserId() != user.getId()) {
            retJson = new FailureMessage("Forbbiden.");
            return "forbidden";
        }
        
        if (order.getId() == appService.getUserCart(user.getId()).getId()) {
            retJson = new FailureMessage("Forbidden."); // Cannot delete cart.
            return "forbidden";
        }
        
        appService.deleteOrder(order);
        retJson = new SuccessMessage();
        return SUCCESS;
    }
}
