package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * created_atフィルードとupdated_atフィルードを更新するための共有するModel
 */
@MappedSuperclass
public class BaseModel extends Model {

	@JsonIgnore
	@Column(name = "created_at")
	protected Date createdAt;

	@JsonIgnore
	@Column(name = "updated_at")
	protected Date updatedAt;

	@Override
	public void save() {
		createdAt();
		super.save();
	}

	@Override
	public void update() {
		updatedAt();
		super.update();
	}

	@PrePersist
	void createdAt() {
		this.createdAt = this.updatedAt = new Date();
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = new Date();
	}
}
