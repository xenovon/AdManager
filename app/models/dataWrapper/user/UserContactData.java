package models.dataWrapper.user;

import java.util.ArrayList;
import java.util.List;

import models.data.enumeration.ContactTypeEnum;

public class UserContactData {

	private List<String[]> data;
	
	public UserContactData() {
		setData();
	}
	private void setData(){
		data=new ArrayList<String[]>();
		for(ContactTypeEnum contact:ContactTypeEnum.values()){
			String[] data_string= {contact.name(), contact.toString()};
			data.add(data_string);
		}
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
	
}
