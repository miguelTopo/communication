package co.edu.udistrital.common.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.structure.enums.State;

@MappedSuperclass
public abstract class CoreEntity implements Serializable {

	@Id @Column(name = "id", nullable = false, unique = true,
		updatable = false) @GeneratedValue(strategy = GenerationType.IDENTITY) protected Long id;

	@Column(name = "creationDate", nullable = false) protected Calendar creationDate;

	@Column(name = "updateDate") protected Calendar updateDate;

	@Column(name = "creationUserId") protected String creationUserId;

	@Column(name = "updateUserId") protected String updateUserId;

	@Column(name = "state") protected State state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreationUserId() {
		return creationUserId;
	}

	public void setCreationUserId(String creationUserId) {
		this.creationUserId = creationUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void addAuditInfo(boolean isNew, String userId) {
		if (isNew) {
			this.creationDate = DateUtil.getCurrentCalendar();
			this.creationUserId = userId;
			this.state = State.ACTIVE;
		} else {
			this.updateDate = DateUtil.getCurrentCalendar();
			this.updateUserId = userId;
		}
	}
}
