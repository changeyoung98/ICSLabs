package Sample.Entity;

public class Plane {
	 private Long id;
	 private String type;
	 private String manufacturer;

	 public Plane() {}
	 
	 public Long getId() { return id; }
	 private void setId(Long id) { this.id = id; }

	 public String getType() { return type; }
	 public void setType(String type) { this.type = type; } 
	 
	 public String getManufacturer() { return manufacturer; }
	 public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; } 

}
