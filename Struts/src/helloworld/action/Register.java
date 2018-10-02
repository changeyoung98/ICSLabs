package helloworld.action;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import helloworld.model.User;
import helloworld.util.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;
 
public class Register extends ActionSupport {
     
    private static final long serialVersionUID = 1L;
     
    private User personBean;
     
 
    @Override
    public String execute() throws Exception {
         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction(); 
        		
//        personBean = (User) session.createQuery(
//                 "select p from User as p where p.firstName = :pfirstname and p.lastName = :plastname").
//                 setParameter("pfirstname", personBean.getFirstName()).
//                 setParameter("plastname", personBean.getLastName()).uniqueResult(); 
//
//        if (personBean !=null) {
//        	session.getTransaction().commit();
//        	return ERROR;
//        }
        personBean.getEmailAddresses().add(personBean.getEmail()); 
        session.save(personBean);
    	session.getTransaction().commit();       
       
        return SUCCESS;
         
    }
     
    public User getPersonBean() {
         
        return personBean;
         
    }
     
    public void setPersonBean(User person) {
         
        personBean = person;
         
    }
    
    public void validate(){
        
        if ( personBean.getFirstName().length() == 0 ){ 
     
            addFieldError( "personBean.firstName", "First name is required." );
             
        }
         
                 
        if ( personBean.getEmail().length() == 0 ){ 
     
            addFieldError( "personBean.email", "Email is required." );
             
        }
         
        if ( personBean.getAge() < 18 ){ 
     
            addFieldError( "personBean.age", "Age is required and must be 18 or older" );
             
        }
         
         
    }
 
}