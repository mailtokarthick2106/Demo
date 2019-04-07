package com.stackroute.keepnote.model;

import java.util.Date;

public class Category {

	/*
	 * This class should have five fields
	 * (categoryId,categoryName,categoryDescription,
	 * categoryCreatedBy,categoryCreationDate). This class should also contain the
	 * getters and setters for the fields along with the toString method. The value
	 * of categoryCreationDate should not be accepted from the user but should be
	 * always initialized with the system date.
	 */
	private String categoryId;
	private String categoryName;
	private String categoryDescription;
	private String categoryCreatedBy;
	private Date categoryCreationDate;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}
    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
       this.categoryId=categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName=categoryName;
    }

    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
    	this.categoryDescription=categoryDescription;
      
    }

    public String getCategoryCreatedBy() {
        return this.categoryCreatedBy;
    }

    public void setCategoryCreatedBy(String categoryCreatedBy) {
       this.categoryCreatedBy=categoryCreatedBy;
    }

    public Date getCategoryCreationDate() {
        return this.categoryCreationDate;
    }

    public void setCategoryCreationDate(Date categoryCreationDate) {
    	this.categoryCreationDate=categoryCreationDate;
      
    }

}
