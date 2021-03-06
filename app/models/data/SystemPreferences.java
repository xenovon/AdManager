package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="system_preferences")
public class SystemPreferences extends Model{

	@Id
	private int id_system_preferences;
	private String key;
	@Column(columnDefinition = "TEXT")
	private String value;
	private String name;
	private String description;
	public static Model.Finder<Integer,SystemPreferences> find = new Model.Finder(Integer.class, SystemPreferences.class);

	
	public int getId_system_preferences() {
		return id_system_preferences;
	}

	public void setId_system_preferences(int id_system_preferences) {
		this.id_system_preferences = id_system_preferences;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
