package com.tioprasetioa.www.mtsn3rohul.Model;

import com.google.gson.annotations.SerializedName;

public class Model_Login_Data {

	@SerializedName("member_type_id")
	private String memberTypeId;

	@SerializedName("expire_date")
	private String expireDate;

	@SerializedName("gender")
	private String gender;

	@SerializedName("member_id2")
	private String memberId2;

	@SerializedName("member_since_date")
	private String member_since_date;

	@SerializedName("id")
	private String id;

	@SerializedName("member_name")
	private String memberName;

	public String getMember_since_date() {
		return member_since_date;
	}

	public void setMember_since_date(String member_since_date) {
		this.member_since_date = member_since_date;
	}

	public void setMemberTypeId(String memberTypeId){
		this.memberTypeId = memberTypeId;
	}

	public String getMemberTypeId(){
		return memberTypeId;
	}

	public void setExpireDate(String expireDate){
		this.expireDate = expireDate;
	}

	public String getExpireDate(){
		return expireDate;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setMemberId2(String memberId2){
		this.memberId2 = memberId2;
	}

	public String getMemberId2(){
		return memberId2;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMemberName(String memberName){
		this.memberName = memberName;
	}

	public String getMemberName(){
		return memberName;
	}
}