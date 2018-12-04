package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class TicketPayment implements Parcelable {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("etc")
	private String etc;

	@SerializedName("jumlah_ticket")
	private int jumlahTicket;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private int status;

	public TicketPayment(Parcel in) {
		updatedAt = in.readString();
		userId = in.readInt();
		etc = in.readString();
		jumlahTicket = in.readInt();
		photo = in.readString();
		createdAt = in.readString();
		totalHarga = in.readString();
		id = in.readInt();
		status = in.readInt();
	}

	public static final Creator<TicketPayment> CREATOR = new Creator<TicketPayment>() {
		@Override
		public TicketPayment createFromParcel(Parcel in) {
			return new TicketPayment(in);
		}

		@Override
		public TicketPayment[] newArray(int size) {
			return new TicketPayment[size];
		}
	};

	public TicketPayment(int id, int user_id, int status, String photo, String etc, int jumlah, String total, String created, String updated) {
		this.id=id;
		this.userId=user_id;
		this.status=status;
		this.photo=photo;
		this.etc=etc;
		this.jumlahTicket=jumlah;
		this.totalHarga=total;
		this.createdAt=created;
		this.updatedAt=updated;
	}

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

	public void setEtc(String etc){
		this.etc = etc;
	}

	public String getEtc(){
		return etc;
	}

	public void setJumlahTicket(int jumlahTicket){
		this.jumlahTicket = jumlahTicket;
	}

	public int getJumlahTicket(){
		return jumlahTicket;
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

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"TicketPayment{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",etc = '" + etc + '\'' + 
			",jumlah_ticket = '" + jumlahTicket + '\'' + 
			",photo = '" + photo + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",id = '" + id + '\'' + 
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
		dest.writeString(etc);
		dest.writeInt(jumlahTicket);
		dest.writeString(photo);
		dest.writeString(createdAt);
		dest.writeString(totalHarga);
		dest.writeInt(id);
		dest.writeInt(status);
	}

	public static class Entry implements BaseColumns{
		public static final String TABLE_NAME="tickets";
		public static final String COLUMN_ID="id";
		public static final String COLUMN_USER_ID="user_id";
		public static final String COLUMN_STATUS="status";
		public static final String COLUMN_PHOTO="photo";
		public static final String COLUMN_ETC="etc";
		public static final String COLUMN_JUMLAH="jumlah_ticket";
		public static final String COLUMN_TOTAL_HARGA="total_harga";
		public static final String COLUMN_CREATED_AT="created_at";
		public static final String COLUMN_UPDATED_AT="updated_at";
	}
}