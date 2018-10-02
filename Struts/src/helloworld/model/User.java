package helloworld.model;

import java.util.HashSet;
import java.util.Set;

public class User
{
	private Long id;
	private String firstName;
    private String lastName;
    private String email;
    private int age;
	private Set emailAddresses = new HashSet();
    
	public User() {
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}
 
    public String getFirstName()
    {
        return firstName;
    }
 
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
 
    public String getLastName()
    {
        return lastName;
    }
 
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
 
    public String getEmail()
    {
        return email;
    }
 
    public void setEmail(String email)
    {
        this.email = email;
    }
 
    public int getAge()
    {
        return age;
    }
 
    public void setAge( int age)
    {
        this.age = age;
    }
 
	public Set getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(Set emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
 
    public String toString()
    {
        return "First Name: " + getFirstName() + " Last Name:  " + getLastName() + 
        " Email:      " + getEmail() + " Age:      " + getAge() ;
    }
}
