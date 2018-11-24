package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataAdmin implements Parcelable {

	@SerializedName("role")
	private int role;

	@SerializedName("gender")
	private int gender;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("photo_profile")
	private String photoProfile;

	@SerializedName("contact")
	private String contact;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	protected DataAdmin(Parcel in) {
		role = in.readInt();
		gender = in.readInt();
		updatedAt = in.readString();
		photoProfile = in.readString();
		contact = in.readString();
		name = in.readString();
		createdAt = in.readString();
		id = in.readInt();
		email = in.readString();
	}

	public static final Creator<DataAdmin> CREATOR = new Creator<DataAdmin>() {
		@Override
		public DataAdmin createFromParcel(Parcel in) {
			return new DataAdmin(in);
		}

		@Override
		public DataAdmin[] newArray(int size) {
			return new DataAdmin[size];
		}
	};

	public void setRole(int role){
		this.role = role;
	}

	public int getRole(){
		return role;
	}

	public void setGender(int gender){
		this.gender = gender;
	}

	public int getGender(){
		return gender;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPhotoProfile(String photoProfile){
		this.photoProfile = photoProfile;
	}

	public String getPhotoProfile(){
		return photoProfile;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public Object getContact(){
		return contact;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"DataAdmin{" + 
			"role = '" + role + '\'' + 
			",gender = '" + gender + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",photo_profile = '" + photoProfile + '\'' + 
			",contact = '" + contact + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(role);
		dest.writeInt(gender);
		dest.writeString(updatedAt);
		dest.writeString(photoProfile);
		dest.writeString(contact);
		dest.writeString(name);
		dest.writeString(createdAt);
		dest.writeInt(id);
		dest.writeString(email);
	}
}