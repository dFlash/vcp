package java.study.vcp.domain;

/**
 * Describes user model, which uses video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class User {
	
	private String name;
	
	private String surname;
	
	private String login;
	
	private String email;
	
	private Company company;
	
	private byte [] avatar;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", login=" + login + ", email=" + email + ", company="
				+ company + "]";
	}

}
