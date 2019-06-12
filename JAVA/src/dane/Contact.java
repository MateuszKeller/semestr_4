package dane;

public class Contact {

	String name;
	String company;
	String email;
	String phone;
	
	public Contact() { }
	public Contact(String imieNazwisko) {
		this.name = imieNazwisko; 
		this.company = "";
		this.email = "";
		this.phone = "";
		}
	
	public String getName() { return name; }
	public void setName(String imieNazwisko) { this.name = imieNazwisko; }
	
	public String getCompany() { return company; }
	public void setCompany(String firma) { this.company = firma; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPhone() { return phone; }
	public void setPhone(String telefon) { this.phone = telefon; }
	
	public String toString()
	{	
		String ret =  name + "\n";
		
		if(company != null)	ret += company + " ";
		if(email != null)	ret+= email + " ";
		if(phone != null)	ret+= phone;
		
		return ret + "\n"; 
	}
}
