package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PesertaResponse implements Parcelable {

	@SerializedName("booking_institution")
	private String bookingInstitution;

	@SerializedName("booking_code")
	private String bookingCode;

	@SerializedName("booking_name")
	private String bookingName;

	@SerializedName("booking_email")
	private String bookingEmail;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("booking_veget")
	private int bookingVeget;

	@SerializedName("booking_id")
	private int bookingId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("qrcode_photo")
	private String qrcodePhoto;

	@SerializedName("booking_price")
	private int bookingPrice;

	@SerializedName("id")
	private int id;

	@SerializedName("booking_contact")
	private String bookingContact;

	@SerializedName("status")
	private int status;

	protected PesertaResponse(Parcel in) {
		bookingInstitution = in.readString();
		bookingCode = in.readString();
		bookingName = in.readString();
		bookingEmail = in.readString();
		createdAt = in.readString();
		bookingVeget = in.readInt();
		bookingId = in.readInt();
		updatedAt = in.readString();
		qrcodePhoto = in.readString();
		bookingPrice = in.readInt();
		id = in.readInt();
		bookingContact = in.readString();
		status = in.readInt();
	}

	public static final Creator<PesertaResponse> CREATOR = new Creator<PesertaResponse>() {
		@Override
		public PesertaResponse createFromParcel(Parcel in) {
			return new PesertaResponse(in);
		}

		@Override
		public PesertaResponse[] newArray(int size) {
			return new PesertaResponse[size];
		}
	};

	public void setBookingInstitution(String bookingInstitution){
		this.bookingInstitution = bookingInstitution;
	}

	public String getBookingInstitution(){
		return bookingInstitution;
	}

	public void setBookingCode(String bookingCode){
		this.bookingCode = bookingCode;
	}

	public String getBookingCode(){
		return bookingCode;
	}

	public void setBookingName(String bookingName){
		this.bookingName = bookingName;
	}

	public String getBookingName(){
		return bookingName;
	}

	public void setBookingEmail(String bookingEmail){
		this.bookingEmail = bookingEmail;
	}

	public String getBookingEmail(){
		return bookingEmail;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setBookingVeget(int bookingVeget){
		this.bookingVeget = bookingVeget;
	}

	public int getBookingVeget(){
		return bookingVeget;
	}

	public void setBookingId(int bookingId){
		this.bookingId = bookingId;
	}

	public int getBookingId(){
		return bookingId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setQrcodePhoto(String qrcodePhoto){
		this.qrcodePhoto = qrcodePhoto;
	}

	public String getQrcodePhoto(){
		return qrcodePhoto;
	}

	public void setBookingPrice(int bookingPrice){
		this.bookingPrice = bookingPrice;
	}

	public int getBookingPrice(){
		return bookingPrice;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setBookingContact(String bookingContact){
		this.bookingContact = bookingContact;
	}

	public String getBookingContact(){
		return bookingContact;
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
			"PesertaResponse{" + 
			"booking_institution = '" + bookingInstitution + '\'' + 
			",booking_code = '" + bookingCode + '\'' + 
			",booking_name = '" + bookingName + '\'' + 
			",booking_email = '" + bookingEmail + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",booking_veget = '" + bookingVeget + '\'' + 
			",booking_id = '" + bookingId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",qrcode_photo = '" + qrcodePhoto + '\'' + 
			",booking_price = '" + bookingPrice + '\'' + 
			",id = '" + id + '\'' + 
			",booking_contact = '" + bookingContact + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(bookingInstitution);
		dest.writeString(bookingCode);
		dest.writeString(bookingName);
		dest.writeString(bookingEmail);
		dest.writeString(createdAt);
		dest.writeInt(bookingVeget);
		dest.writeInt(bookingId);
		dest.writeString(updatedAt);
		dest.writeString(qrcodePhoto);
		dest.writeInt(bookingPrice);
		dest.writeInt(id);
		dest.writeString(bookingContact);
		dest.writeInt(status);
	}
}