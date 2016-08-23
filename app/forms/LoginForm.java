package forms;

import javax.persistence.Column;

import play.data.validation.Constraints;

public class LoginForm {
	@Constraints.MaxLength(256)
	@Constraints.Required
	private String username;
	
	@Column
	@Constraints.MaxLength(256)
	@Constraints.Required
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
}
