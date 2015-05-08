package com.eoe.store;

import java.util.ArrayList;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ContactsInfo {
	private int Id;
	private String Name;
	private ArrayList<String> PhoneNum;
	private String Address;
	private String Remark;
	private String PinYin;
	private String FirstPinYin;
	

	public ContactsInfo() {
		Id = 0;
		Name = null;
		PhoneNum = new ArrayList<String>();// 如果是=null的话，那么该变量是没有实例化的，就不能调用他的相关函数
		Address = null;
		Remark = null;
		PinYin = null;
		FirstPinYin=null;
	}

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
		if (PinYin == null) {
			try {//挺花时间的
				setPinYin(ToPinYin.getPinYin(Name));
				setFirstPinYin(ToPinYin.getFirstPinYin(Name));
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}// 如果是中文就转换为大写拼音，如果是字母就转换为小写格式
		}
	}

	public int sizeOfPhone() {
		return PhoneNum.size();
		
	}

	public String getPhoneNum(int i) {
		return PhoneNum.get(i);
	}

	public void setPhoneNum(String phoneNum) {
		this.PhoneNum.add(phoneNum);
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String Remark) {
		this.Remark = Remark;
	}

	public String getPinYin() {
		return PinYin;
	}

	public void setPinYin(String PinYin) {
		this.PinYin = PinYin;
	}
	public String getFirstPinYin() {
		return FirstPinYin;
	}

	public void setFirstPinYin(String FirstPinYin) {
		this.FirstPinYin = FirstPinYin;
	}
}
