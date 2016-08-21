package models;

import play.data.validation.Constraints;
import utils.CipherUtil;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class User extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	private String authToken;

	@Column
	@Constraints.MaxLength(256)
	@Constraints.Required
	@Constraints.Email
	private String username;

	@Column
	@Constraints.Required
	@Constraints.MaxLength(256)
	private String password;

	public static Finder<Long, User> find = new Finder<>(User.class);

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String emailAddress) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = CipherUtil.getSha512(password);
	}

	public String createToken() {
		authToken = UUID.randomUUID().toString();
		save();
		return authToken;
	}

	public void deleteAuthToken() {
		authToken = null;
		save();
	}

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public static User findByAuthToken(String authToken) {
		if (authToken == null) {
			return null;
		}
		return find.where().eq("authToken", authToken).findUnique();
	}

	public static User findByLoginInfo(String username, String password) {
		return find.where().eq("username", username.toLowerCase()).eq("password", CipherUtil.getSha512(password))
				.findUnique();
	}
}