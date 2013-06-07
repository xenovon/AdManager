package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ZoneController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.util.HashMap;

import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.data.Zone;
import models.data.ZoneChannel;
import models.dataWrapper.TemplateData;
import models.form.backendForm.zoneForm.ChannelForm;
import models.form.backendForm.zoneForm.ZoneForm;
import models.form.frontendForm.LoginForm;
import models.service.Authentificator;
import models.service.zone.ChannelProcessor;
import models.service.zone.ZoneProcessor;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.zone_view.*;
import views.html.frontendView.login;

public class ZoneController extends CompressController {

	final static Form<ZoneForm> zoneForm = Form.form(ZoneForm.class);
    final static Form<ChannelForm> channelForm = Form.form(ChannelForm.class);
	
    static ChannelProcessor cp = new ChannelProcessor();
    static ZoneProcessor zp = new ZoneProcessor();


	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showZonePage(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<Zone> zone_data=zp.getZone(page, 10, "", "", "");
		return ok(zone_index.render(data, zone_data));
	}
	@With(DataFiller.class)	
	@SubjectPresent	
	public static Result showZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<Zone> zone_data=zp.getZone(0, 10, "", "", "");
		return ok(zone_index.render(data, zone_data));
	}
	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<ZoneChannel> channel_data=cp.getChannel(0, 10, "", "", "");
		return ok(channel_index.render(data, channel_data));
	}
	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showChannelPage(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<ZoneChannel> channel_data=cp.getChannel(page, 10, "", "", "");		
		return ok(channel_index.render(data, channel_data));
	}	
	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showSingleZone(int id_zone){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(show_single_zone.render(data,zp.getSingleZone(id_zone)));
	}

	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showSingleChannel(int channel){	
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok();
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result createZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(create_zone.render(data,zoneForm,zp.getZoneFormData()));
		
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result createChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(create_channel.render(data, channelForm));
	}
	
	@Restrict(@Group("administrator"))
	@With(DataFiller.class)
	public static Result saveZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<ZoneForm> filledForm=Form.form(ZoneForm.class).bindFromRequest();
		if(filledForm.hasErrors()){
			
			return ok(create_zone.render(data,filledForm, zp.getZoneFormData()));
		}else{
			Zone zona=zp.saveForm(filledForm);
			return ok(create_zone_success.render(data,zona));
			
		}
	}
	
	@Restrict(@Group("administrator"))
	@With(DataFiller.class)
	public static Result saveChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		Form<ChannelForm>  channelForm = Form.form(ChannelForm.class).bindFromRequest();
		if(channelForm.hasErrors()) {
			return ok(create_channel.render(data, channelForm));
        } else {
        	ZoneChannel zoneChannel = cp.saveForm(channelForm);
    		return ok(create_channel_success.render(data, zoneChannel));
        }		
	}

	@With(DataFiller.class)		
	public static Result editZone(int id_zone){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		session("id_edit_zone", Integer.toString(id_zone)); //untuk menyimpan zona mana yang diedit
		return ok(edit_zone.render(data, zoneForm, zp.getZoneFormData(),zp.getSingleZone(id_zone)));
	}
	
	@With(DataFiller.class)		
	public static Result saveEditZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<ZoneForm> filledForm=Form.form(ZoneForm.class).bindFromRequest();
		int id_zone = Integer.parseInt(session("id_edit_zone"));
		
		if(filledForm.hasErrors()){
			return badRequest(edit_zone.render(data, zoneForm, zp.getZoneFormData(), zp.getSingleZone(id_zone)));
		}else{
			//TODO eksekusi ke model berdasar id zone
			flash("success","Zona Berhasil di edit");
			return ok(show_single_zone.render(data,zp.getSingleZone(id_zone)));
		}
	}
	@With(DataFiller.class)		
	public static Result editChannel(){
		return ok();
	}

	public static Result deleteChannel(){
		return ok();
	}
	
	public static Result deleteZone(){
		return ok();
	}
	

}
