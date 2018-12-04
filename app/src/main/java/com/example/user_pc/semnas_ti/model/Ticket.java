package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class Ticket implements Parcelable {

	@SerializedName("booking_institution")
	private String bookingInstitution;

	@SerializedName("booking_code")
	private Object bookingCode;

	@SerializedName("booking_name")
	private String bookingName;

	@SerializedName("booking_email")
	private String bookingEmail;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("booking_veget")
	private int bookingVeget;

	@SerializedName("booking_id")
	private int bookingId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("etc")
	private String etc;

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

	public Ticket(Parcel in) {
		bookingInstitution = in.readString();
		bookingName = in.readString();
		bookingEmail = in.readString();
		photo = in.readString();
		createdAt = in.readString();
		bookingVeget = in.readInt();
		bookingId = in.readInt();
		updatedAt = in.readString();
		userId = in.readInt();
		etc = in.readString();
		qrcodePhoto = in.readString();
		bookingPrice = in.readInt();
		id = in.readInt();
		bookingContact = in.readString();
		status = in.readInt();
	}

	public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
		@Override
		public Ticket createFromParcel(Parcel in) {
			return new Ticket(in);
		}

		@Override
		public Ticket[] newArray(int size) {
			return new Ticket[size];
		}
	};

	public Ticket(int id, String qrcode, String name, String email, String contact, int veget, String instansi, int price, String created, int status) {
		this.id=id;
		this.qrcodePhoto=qrcode;
		this.bookingName=name;
		this.bookingEmail=email;
		this.bookingContact=contact;
		this.bookingVeget=veget;
		this.bookingInstitution=instansi;
		this.bookingPrice=price;
		this.createdAt=created;
		this.status=status;
	}

	public void setBookingInstitution(String bookingInstitution){
		this.bookingInstitution = bookingInstitution;
	}

	public String getBookingInstitution(){
		return bookingInstitution;
	}

	public void setBookingCode(Object bookingCode){
		this.bookingCode = bookingCode;
	}

	public Object getBookingCode(){
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
			"Ticket{" + 
			"booking_institution = '" + bookingInstitution + '\'' + 
			",booking_code = '" + bookingCode + '\'' + 
			",booking_name = '" + bookingName + '\'' + 
			",booking_email = '" + bookingEmail + '\'' + 
			",photo = '" + photo + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",booking_veget = '" + bookingVeget + '\'' + 
			",booking_id = '" + bookingId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",etc = '" + etc + '\'' + 
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
		dest.writeString(bookingName);
		dest.writeString(bookingEmail);
		dest.writeString(photo);
		dest.writeString(createdAt);
		dest.writeInt(bookingVeget);
		dest.writeInt(bookingId);
		dest.writeString(updatedAt);
		dest.writeInt(userId);
		dest.writeString(etc);
		dest.writeString(qrcodePhoto);
		dest.writeInt(bookingPrice);
		dest.writeInt(id);
		dest.writeString(bookingContact);
		dest.writeInt(status);
	}

	public static class Entry implements BaseColumns{
		public static final String TABLE_NAME="ticket_details";
		public static final String COLUMN_ID="id";
		public static final String COLUMN_BOOKING_ID="booking_id";
		public static final String COLUMN_QR_CODE="qrcode_photo";
		public static final String COLUMN_BOOKING_NAME="booking_name";
		public static final String COLUMN_BOOKING_EMAIL="booking_email";
		public static final String COLUMN_BOOKING_CONTACT="booking_contact";
		public static final String COLUMN_BOOKING_VEGET="booking_veget";
		public static final String COLUMN_BOOKING_INSTITUTION="booking_institution";
		public static final String COLUMN_BOOKING_PRICE="booking_price";
		public static final String COLUMN_BOOKING_ADD="created_at";
		public static final String COLUMN_STATUS="status";
	}
}