import play.*;
import play.libs.*;

import java.util.*;

import javax.naming.Context;

import com.avaje.ebean.*;

import models.*;
import models.custom_helper.SetInitialData;
import models.custom_helper.setting.SettingDefault;

public class Global extends GlobalSettings {

	public void onStart(Application app){
		SettingDefault default2=new SettingDefault();
		default2.setAll();
//		SetInitialData data=new SetInitialData();
//		data.setDataUser();
//		data.setBannerSize();
//		data.setZoneChannel();
//		data.setCampaignData();
//		data.createBanner();
//		data.simulasi();
	}
}
