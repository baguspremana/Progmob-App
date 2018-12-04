package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class InfoSeminarResponse implements Parcelable {

	@SerializedName("seminar_schedule")
	private String seminarSchedule;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("seminar_description")
	private String seminarDescription;

	@SerializedName("seminar_location")
	private String seminarLocation;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("seminar_theme")
	private String seminarTheme;

	@SerializedName("id")
	private int id;

	@SerializedName("seminar_name")
	private String seminarName;

	@SerializedName("ticket_available")
	private int ticketAvailable;

	public InfoSeminarResponse(Parcel in) {
		seminarSchedule = in.readString();
		updatedAt = in.readString();
		seminarDescription = in.readString();
		seminarLocation = in.readString();
		createdAt = in.readString();
		seminarTheme = in.readString();
		id = in.readInt();
		seminarName = in.readString();
		ticketAvailable = in.readInt();
	}

	public static final Creator<InfoSeminarResponse> CREATOR = new Creator<InfoSeminarResponse>() {
		@Override
		public InfoSeminarResponse createFromParcel(Parcel in) {
			return new InfoSeminarResponse(in);
		}

		@Override
		public InfoSeminarResponse[] newArray(int size) {
			return new InfoSeminarResponse[size];
		}
	};

	public InfoSeminarResponse(int id, String name, String theme, String desc, String schedule, String location, int tiket) {
		this.id=id;
		this.seminarName=name;
		this.seminarTheme=theme;
		this.seminarDescription=desc;
		this.seminarSchedule=schedule;
		this.seminarLocation=location;
		this.ticketAvailable=tiket;
	}

	public void setSeminarSchedule(String seminarSchedule){
		this.seminarSchedule = seminarSchedule;
	}

	public String getSeminarSchedule(){
		return seminarSchedule;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSeminarDescription(String seminarDescription){
		this.seminarDescription = seminarDescription;
	}

	public String getSeminarDescription(){
		return seminarDescription;
	}

	public void setSeminarLocation(String seminarLocation){
		this.seminarLocation = seminarLocation;
	}

	public String getSeminarLocation(){
		return seminarLocation;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setSeminarTheme(String seminarTheme){
		this.seminarTheme = seminarTheme;
	}

	public String getSeminarTheme(){
		return seminarTheme;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSeminarName(String seminarName){
		this.seminarName = seminarName;
	}

	public String getSeminarName(){
		return seminarName;
	}

	public void setTicketAvailable(int ticketAvailable){
		this.ticketAvailable = ticketAvailable;
	}

	public int getTicketAvailable(){
		return ticketAvailable;
	}

	@Override
 	public String toString(){
		return 
			"InfoSeminarResponse{" + 
			"seminar_schedule = '" + seminarSchedule + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",seminar_description = '" + seminarDescription + '\'' + 
			",seminar_location = '" + seminarLocation + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",seminar_theme = '" + seminarTheme + '\'' + 
			",id = '" + id + '\'' + 
			",seminar_name = '" + seminarName + '\'' + 
			",ticket_available = '" + ticketAvailable + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(seminarSchedule);
		dest.writeString(updatedAt);
		dest.writeString(seminarDescription);
		dest.writeString(seminarLocation);
		dest.writeString(createdAt);
		dest.writeString(seminarTheme);
		dest.writeInt(id);
		dest.writeString(seminarName);
		dest.writeInt(ticketAvailable);
	}

	public static class Entry implements BaseColumns{
		public static final String TABLE_NAME="info_seminar";
		public static final String COLUMN_ID="id";
		public static final String COLUMN_NAME="seminar_name";
		public static final String COLUMN_THEME="seminar_theme";
		public static final String COLUMN_DESC="seminar_desc";
		public static final String COLUMN_LOC="seminar_loc";
		public static final String COLUMN_SCHEDULE="seminar_schedule";
		public static final String COLUMN_TICKET="ticket_available";
	}
}