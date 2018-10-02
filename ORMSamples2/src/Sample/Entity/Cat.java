package Sample.Entity;

import java.util.Date;

public class Cat {
	
	 private Long uid;
	 private Date birthday;
	 private String color;
	 private String sex;
	 private int weight;

	 public Cat() {}
	 
	 public Long getUid() { return uid; }
	 public void setUid(Long uid) { this.uid = uid; }
	 
	 public Date getBirthday() { return birthday; }
	 public void setBirthday(Date birthday) { this.birthday = birthday; }
	 
	 public String getColor() { return color; }
	 public void setColor(String color) { this.color = color; }
	 
	 public String getSex() { return sex; }
	 public void setSex(String sex) { this.sex = sex; }
	 
	 public int getWeight() { return weight; }
	 public void setWeight(int weight) { this.weight = weight; }

}
