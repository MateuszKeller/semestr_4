package dane;

public class Contact {

	String name;
	String company;
	String email;
	String phone;
	
	public Contact(String imieNazwisko) { this.name = imieNazwisko; }
	
	public String getName() { return name; }
	public void setName(String imieNazwisko) { this.name = imieNazwisko; }
	
	public String getCompany() { return company; }
	public void setCompany(String firma) { this.company = firma; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPhone() { return phone; }
	public void setPhone(String telefon) { this.phone = telefon; }
	
	
}