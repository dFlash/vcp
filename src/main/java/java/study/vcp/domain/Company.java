package java.study.vcp.domain;

/**
 * Describes company model, which uses video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class Company {
	
	private String name;
	
	private String address;
	
	private String contactEmail;
	
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", address=" + address + ", contactEmail=" + contactEmail + ", phone=" + phone
				+ "]";
	}
}
