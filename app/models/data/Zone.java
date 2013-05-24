package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.ZoneTypeEnum;

@Entity
@Table(name="zone",schema="adsmanager")
public class Zone extends Model {
	
	@Id
	private int id_zone;
	@ManyToOne
	private ZoneChannel zone_channel;
	@ManyToOne
	private AdsSize ads_size;
	private ZoneTypeEnum zone_type;
	private DefaultViewEnum zone_default_view;
	@Column(columnDefinition="TEXT")
	private String zone_default_code;
	private boolean isDeleted;
	
	public static Model.Finder<Integer, Zone> find = new Model.Finder(Integer.class, Zone.class);
	
	public int getId_zone() {
		return id_zone;
	}
	public void setId_zone(int id_zone) {
		this.id_zone = id_zone;
	}
	public ZoneChannel getZone_channel() {
		return zone_channel;
	}
	public void setZone_channel(ZoneChannel zone_channel) {
		this.zone_channel = zone_channel;
	}
	public AdsSize getAds_size() {
		return ads_size;
	}
	public void setAds_size(AdsSize ads_size) {
		this.ads_size = ads_size;
	}
	public ZoneTypeEnum getZone_type() {
		return zone_type;
	}
	public void setZone_type(ZoneTypeEnum zone_type) {
		this.zone_type = zone_type;
	}
	public DefaultViewEnum getZone_default_view() {
		return zone_default_view;
	}
	public void setZone_default_view(DefaultViewEnum zone_default_view) {
		this.zone_default_view = zone_default_view;
	}
	public String getZone_default_code() {
		return zone_default_code;
	}
	public void setZone_default_code(String zone_default_code) {
		this.zone_default_code = zone_default_code;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}