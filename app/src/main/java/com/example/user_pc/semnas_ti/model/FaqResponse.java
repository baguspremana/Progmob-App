package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FaqResponse implements Parcelable {

	@SerializedName("question")
	private String question;

	@SerializedName("answer")
	private String answer;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	protected FaqResponse(Parcel in) {
		question = in.readString();
		answer = in.readString();
		updatedAt = in.readString();
		userId = in.readInt();
		name = in.readString();
		createdAt = in.readString();
		id = in.readInt();
		email = in.readString();
	}

	public static final Creator<FaqResponse> CREATOR = new Creator<FaqResponse>() {
		@Override
		public FaqResponse createFromParcel(Parcel in) {
			return new FaqResponse(in);
		}

		@Override
		public FaqResponse[] newArray(int size) {
			return new FaqResponse[size];
		}
	};

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return answer;
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
			"FaqResponse{" + 
			"question = '" + question + '\'' + 
			",answer = '" + answer + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
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
		dest.writeString(question);
		dest.writeString(answer);
		dest.writeString(updatedAt);
		dest.writeInt(userId);
		dest.writeString(name);
		dest.writeString(createdAt);
		dest.writeInt(id);
		dest.writeString(email);
	}
}