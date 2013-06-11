package models.service.user;

import play.Play;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Http.MultipartFormData.FilePart;
import models.custom_helper.DomainURL;
import models.custom_helper.SendMail;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.SaveToEnum;
import models.data.FileUpload;
import models.data.User;
import models.data.UserContact;
import models.data.UserRole;
import models.data.enumeration.RoleEnum;
import models.form.backendForm.userForm.UserForm;

public class UserCreator {

	FileManager manager;
	SendMail mailer;
	public UserCreator(FileManager manager, SendMail mailer){
		this.manager=manager;
		this.mailer=mailer;
	}
	
	public User saveUser(Form<UserForm> form){
		User user;
		try {
			user = new User();
			UserRole role=new UserRole(RoleEnum.valueOf(form.get().role));
			user.setActive(true);
			user.setFront_name(form.get().front_name);
			user.setLast_name(form.get().last_name);
			user.setEmail(form.get().email);
			user.setCompany(form.get().company);
			user.setCity(form.get().city);
			user.setCountry(form.get().country);
			user.setRole(role);
			user.setPassword(form.get().password);
			user.save();
			sendEmail(user,form.get().password);
			return user;
			
		} catch (Exception e) {
			return null;
		}
	}
	public User updateUser(Form<UserForm> form){
		User user;
		try {
			user =User.find.byId(Integer.parseInt(form.get().id));
			UserRole role=new UserRole(RoleEnum.valueOf(form.get().role));
			
			user.setActive(true);
			user.setFront_name(form.get().front_name);
			user.setLast_name(form.get().last_name);
			user.setEmail(form.get().email);
			user.setCompany(form.get().company);
			user.setCity(form.get().city);
			user.setCountry(form.get().country);
			user.setRole(role);
			user.setPassword(form.get().password);
			user.save();
			
			return user;
			
		} catch (Exception e) {
			return null;
		}	
	}
	
	public boolean saveContact(Form user){
		
		return false;
		
	}
	//Kode Error : 
	//0 : sukses;
	//1 : exception;
	//2 : format nggak sesuai
	public int saveProfilePicture(FilePart part, int id_user){
		try {
			//validasi type
			String filetype=part.getContentType();
			if(filetype.equals("image/png") || 
			   filetype.equals("image/jpeg") || 
			   filetype.equals("image/gif"))  {
			
				User user=User.find.byId(id_user);
				FileUpload upload=manager.saveNew(part, SaveToEnum.PROFILE_PICTURE);
				if(user.getProfile_photo()!=null){
					FileUpload existing=user.getProfile_photo();
					user.setProfile_photo(upload);
					existing.delete();
				}else{
					user.setProfile_photo(upload);			
				}
				user.save();
				manager.saveThumbnail(upload.getId());
				
				return 0;	//sukses			
			} else return 2; //jika ngga sesuai format

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int updateProfilePicture(FilePart part, int id_user){
		return 0;
	}
	
	private boolean sendEmail(User user, String password){
		try {
			String content=Messages.get("email.registered.content", 
										 DomainURL.get()+"/login",
										 password, 
										 user.getRole().getName());
			
			mailer.setRecipient(user.getEmail());
			mailer.setSender(Messages.get("email.sender"));
			mailer.setContent(content);
			mailer.setSubject(Messages.get("email.registered.subject"));
			mailer.setCc(Messages.get("email.sender"));
			
			mailer.sendHTML();
			
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		
		return true;
	}	

}
