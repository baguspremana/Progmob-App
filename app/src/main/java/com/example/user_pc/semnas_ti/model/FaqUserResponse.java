package com.example.user_pc.semnas_ti.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FaqUserResponse implements Parcelable {

	@SerializedName("question")
	private String question;

	@SerializedName("answer")
	private String answer;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	protected FaqUserResponse(Parcel in) {
		question = in.readString();
		answer = in.readString();
		updatedAt = in.readString();
		userId = in.readInt();
		createdAt = in.readString();
		id = in.readInt();
	}

	public static final Creator<FaqUserResponse> CREATOR = new Creator<FaqUserResponse>() {
		@Override
		public FaqUserResponse createFromParcel(Parcel in) {
			return new FaqUserResponse(in);
		}

		@Override
		public FaqUserResponse[] newArray(int size) {
			return new FaqUserResponse[size];
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

	@Override
 	public String toString(){
		return 
			"FaqUserResponse{" + 
			"question = '" + question + '\'' + 
			",answer = '" + answer + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
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
		dest.writeString(createdAt);
		dest.writeInt(id);
	}
}