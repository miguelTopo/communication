package co.edu.udistrital.common.model;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.structure.enums.State;


public abstract class CoreEntity implements Serializable {

	@Id @Indexed protected String id;

	protected Calendar creationDate;

	protected Calendar updateDate;

	protected String creationUserId;

	protected String updateUserId;

	protected State state;


	public String getId() {
		return id;
	}

	public void setId(String id) {
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
