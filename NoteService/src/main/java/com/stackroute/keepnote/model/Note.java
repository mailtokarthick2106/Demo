package com.stackroute.keepnote.model;

import java.util.Date;
import java.util.List;

public class Note {
	
	/*
	 * This class should have eight fields
	 * (noteId,noteTitle,noteContent,noteStatus,createdAt,
	 * category,reminder,createdBy). This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of createdAt should not be
	 * accepted from the user but should be always initialized with the system date.
	 * 
	 */
	private int id;
	private String title;
	private String text;
	private String state;
	private Date noteCreationDate;
	private String category;
	//private List<Reminder> reminders;
	private String noteCreatedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getNoteCreationDate() {
		return noteCreationDate;
	}
	public void setNoteCreationDate(Date noteCreationDate) {
		Date now = new Date();
		this.noteCreationDate = now;
	}
	public String getNoteCreatedBy() {
		return noteCreatedBy;
	}
	public void setNoteCreatedBy(String noteCreatedBy) {
		this.noteCreatedBy = noteCreatedBy;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}


	    // getters & setters

	
	/*	    public Category getCategory() {
	        return category;
	    }

	    public void setCategory(Category category) {
	       this.category=category;
	    }

	    public List<Reminder> getReminders() {
	        return this.reminders;
	    }

	    public void setReminders(List<Reminder> reminders) {
	    	this.reminders=reminders;
	    
	    }*/
	}
