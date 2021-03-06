package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TicketPaymentAdmin implements Parcelable {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("contact")
	private String contact;

	@SerializedName("jumlah_ticket")
	private int jumlahTicket;

	@SerializedName("name")
	private String name;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private int status;

	protected TicketPaymentAdmin(Parcel in) {
		updatedAt = in.readString();
		userId = in.readInt();
		contact = in.readString();
		jumlahTicket = in.readInt();
		name = in.readString();
		photo = in.readString();
		createdAt = in.readString();
		totalHarga = in.readString();
		id = in.readInt();
		email = in.readString();
		status = in.readInt();
	}

	public static final Creator<TicketPaymentAdmin> CREATOR = new Creator<TicketPaymentAdmin>() {
		@Override
		public TicketPaymentAdmin createFromParcel(Parcel in) {
			return new TicketPaymentAdmin(in);
		}

		@Override
		public TicketPaymentAdmin[] newArray(int size) {
			return new TicketPaymentAdmin[size];
		}
	};

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

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setJumlahTicket(int jumlahTicket){
		this.jumlahTicket = jumlahTicket;
	}

	public int getJumlahTicket(){
		return jumlahTicket;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
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

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"TicketPaymentAdmin{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",contact = '" + contact + '\'' + 
			",jumlah_ticket = '" + jumlahTicket + '\'' + 
			",name = '" + name + '\'' + 
			",photo = '" + photo + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(updatedAt);
		dest.writeInt(userId);
		dest.writeString(contact);
		dest.writeInt(jumlahTicket);
		dest.writeString(name);
		dest.writeString(photo);
		dest.writeString(createdAt);
		dest.writeString(totalHarga);
		dest.writeInt(id);
		dest.writeString(email);
		dest.writeInt(status);
	}
}