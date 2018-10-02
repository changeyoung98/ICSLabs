package sample;

import org.hibernate.Session;
import java.util.*;

public class UserManager {

    public static void main(String[] args) {
        UserManager mgr = new UserManager();

        if (args[0].equals("store")) {
            mgr.createAndStoreUser("MH370", "missing", "13800000000", "bless@vip.com");
        }else if (args[0].equals("list")) {
            List users = mgr.listUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = (User) users.get(i);
                System.out.println(
                        "ID: " + user.getId() + " User: " + user.getFirstname() + " " 
                        + user.getLastname() + " Phone: " + user.getPhone() + 
                        " email: " + user.getEmail()
                );
            }
        }
        HibernateUtil.getSessionFactory().close();
    }

    @SuppressWarnings("unchecked")
	private List<User> listUsers() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User").list();
        session.getTransaction().commit();
        return result;
	}

	private void createAndStoreUser(String firstname, String lastname, String phone, String email) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhone(phone);
        user.setEmail(email);
        session.save(user);

        session.getTransaction().commit();
    }

}
