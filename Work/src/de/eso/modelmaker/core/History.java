package de.eso.modelmaker.core;

/**
 * History Object, which is used to save History Record
 * 
 * @author bihu8398
 *
 */
public class History 
{
	private Long guideID = 0L;
	
	private String user = null;
	
	private String comment = null;
	
	private String time = null;

	public History(Long guideID, String user, String comment, String time) {
		super();
		this.guideID = guideID;
		this.user = user;
		this.comment = comment;
		this.time = time;
	}

	public History() {
		super();
	}

	public Long getGuideID() {
		return guideID;
	}

	public void setGuideID(Long guideID) {
		this.guideID = guideID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "History [guideID=" + guideID + ", user=" + user + ", comment="
				+ comment + ", time=" + time + "]";
	}
	
	
}
