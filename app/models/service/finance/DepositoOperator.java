package models.service.finance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.data.Form;
import models.data.AdsTransaction;
import models.data.Deposito;
import models.data.TransferConfirmation;
import models.data.User;
import models.data.UserContact;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.PaymentMethodEnum;
import models.form.backendForm.financeForm.DepositoForm;
import models.form.backendForm.financeForm.TransferForm;
import models.service.notification.NotificationCenter;

public class DepositoOperator {

	NotificationCenter notif;
	SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy hh:mm");

	public DepositoOperator(NotificationCenter notif){
		this.notif=notif;
	}
	public TransferConfirmation saveConfirmation(Form<TransferForm> input, User user){
		try {
			TransferConfirmation confirmation=new TransferConfirmation();
			confirmation.setAmount(Integer.parseInt(input.get().amount));
			confirmation.setDescription(input.get().description);
			if(input.get().senderBankAccount.length()<5){
				try{
					Integer idContact=Integer.parseInt(input.get().senderBankAccount);
					UserContact contact=UserContact.find.byId(idContact);
					confirmation.setSenderBankAccount(contact.getContact_value());
				}catch(Exception e){
					confirmation.setSenderBankAccount(input.get().senderBankAccount);
					e.printStackTrace();
				}
				
			}
			confirmation.setTimestamp_created(new Date());
			confirmation.setTransfer_date(format.parse(input.get().transfer_date));
			confirmation.setUser(user);
			confirmation.save();
			
			return confirmation;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TransferConfirmation updateConfirmation(Form<TransferForm> form, TransferConfirmation transfer){
		return null;
	}
	public boolean validate(TransferConfirmation transfer, User userValidator){
		Ebean.beginTransaction();
		try {
			Deposito deposito=new Deposito();
			User user=transfer.getUser();
			int current_balance=user.getCurrent_balance();

			//deposito
			deposito.setAmount(transfer.getAmount());
			deposito.setCurrent_balance(current_balance+transfer.getAmount());
			deposito.setPayment_method(PaymentMethodEnum.TRANSFER);
			deposito.setDescription(transfer.getDescription());
			deposito.setTimestamp(new Date());
			deposito.setUser(user);
			deposito.save();
			
			//transfer
			transfer.setTimestamp_validated(new Date());
			transfer.setUser_validator(userValidator);
			transfer.setValidated(true);
			transfer.update();
			
			//user
			user.setCurrent_balance(current_balance+transfer.getAmount());
			user.update();
			Ebean.commitTransaction();
			return true;
		} catch (Exception e) {
			Ebean.rollbackTransaction();
			e.printStackTrace();
			return true;
		}finally{
			Ebean.endTransaction();
		}
	}
	public Deposito newDeposito(Form<DepositoForm> form){
		Ebean.beginTransaction();
		Deposito deposito=null;
		try {
			deposito=new Deposito();
			User user=User.find.byId(Integer.parseInt(form.get().idUser));
			int current_balance=user.getCurrent_balance();
			int amount=Integer.parseInt(form.get().amount);
			
			deposito.setAmount(amount);
			deposito.setCurrent_balance(current_balance+amount);
			deposito.setDescription(form.get().description);
			deposito.setTimestamp(new Date());
			deposito.setPayment_method(PaymentMethodEnum.valueOf(form.get().paymentMethod));
			deposito.setUser(user);
			deposito.save();
			
			user.setCurrent_balance(current_balance+amount);
			user.update();
			Ebean.commitTransaction();
			
		} catch (NumberFormatException e) {
			Ebean.rollbackTransaction();
			e.printStackTrace();
		}catch(Exception e){
			Ebean.rollbackTransaction();
		}finally{
			Ebean.endTransaction();
		}
		
		
		return null;
	}
	public boolean deleteTransfer(TransferConfirmation transfer){
		try {
			transfer.delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<UserContact> getUserBankAccount(User user){
		List<UserContact> result=new ArrayList<UserContact>();
		try{
			result=UserContact.find.where().eq("user",user).
					eq("contact_type", ContactTypeEnum.BANK_ACCOUNT.name().toLowerCase()).
					findList();
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}
}
