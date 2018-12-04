package com.example.user_pc.semnas_ti.model;

import com.google.gson.annotations.SerializedName;

public class LogVerifikasiResponse{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("booking_email")
	private String bookingEmail;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("booking_detail_id")
	private int bookingDetailId;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private int status;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setBookingEmail(String bookingEmail){
		this.bookingEmail = bookingEmail;
	}

	public String getBookingEmail(){
		return bookingEmail;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setBookingDetailId(int bookingDetailId){
		this.bookingDetailId = bookingDetailId;
	}

	public int getBookingDetailId(){
		return bookingDetailId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LogVerifikasiResponse{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",booking_email = '" + bookingEmail + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",booking_detail_id = '" + bookingDetailId + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}