package com.appsdeveloperblog.app.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfileRest {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String href;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	
	
}
