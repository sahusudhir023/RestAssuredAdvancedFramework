package com.automation.api.payloads;

public class PostPayLoad {

	private String title;
	private String body;
	private int userId;
	private int id;

	// ==========Constructor==========
	public PostPayLoad() {

	}

	public PostPayLoad(String title, String body, int userId) {
		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	// =========Getter And Setter==========
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;		
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
