package com.tioprasetioa.www.mtsn3rohul.Model;

import com.google.gson.annotations.SerializedName;

public class ModelData_TampilData {

	@SerializedName("member_id")
	private String memberId;

	@SerializedName("item_code")
	private String itemCode;

	@SerializedName("loan_date")
	private String loanDate;

	@SerializedName("actual")
	private Object actual;

	@SerializedName("due_date")
	private String dueDate;

	@SerializedName("loan_rules_id")
	private int loanRulesId;

	@SerializedName("is_lent")
	private int isLent;

	@SerializedName("renewed")
	private int renewed;

	@SerializedName("uid")
	private int uid;

	@SerializedName("is_return")
	private int isReturn;

	@SerializedName("last_update")
	private String lastUpdate;

	@SerializedName("input_date")
	private String inputDate;

	@SerializedName("loan_id")
	private int loanId;

	@SerializedName("return_date")
	private String returnDate;

	public void setMemberId(String memberId){
		this.memberId = memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	public String getItemCode(){
		return itemCode;
	}

	public void setLoanDate(String loanDate){
		this.loanDate = loanDate;
	}

	public String getLoanDate(){
		return loanDate;
	}

	public void setActual(Object actual){
		this.actual = actual;
	}

	public Object getActual(){
		return actual;
	}

	public void setDueDate(String dueDate){
		this.dueDate = dueDate;
	}

	public String getDueDate(){
		return dueDate;
	}

	public void setLoanRulesId(int loanRulesId){
		this.loanRulesId = loanRulesId;
	}

	public int getLoanRulesId(){
		return loanRulesId;
	}

	public void setIsLent(int isLent){
		this.isLent = isLent;
	}

	public int getIsLent(){
		return isLent;
	}

	public void setRenewed(int renewed){
		this.renewed = renewed;
	}

	public int getRenewed(){
		return renewed;
	}

	public void setUid(int uid){
		this.uid = uid;
	}

	public int getUid(){
		return uid;
	}

	public void setIsReturn(int isReturn){
		this.isReturn = isReturn;
	}

	public int getIsReturn(){
		return isReturn;
	}

	public void setLastUpdate(String lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdate(){
		return lastUpdate;
	}

	public void setInputDate(String inputDate){
		this.inputDate = inputDate;
	}

	public String getInputDate(){
		return inputDate;
	}

	public void setLoanId(int loanId){
		this.loanId = loanId;
	}

	public int getLoanId(){
		return loanId;
	}

	public void setReturnDate(String returnDate){
		this.returnDate = returnDate;
	}

	public String getReturnDate(){
		return returnDate;
	}
}