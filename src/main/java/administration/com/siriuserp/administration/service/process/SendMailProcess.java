/**
 * Mar 18, 2008 11:20:42 AM
 * com.siriuserp.administration.service.process
 * SendMailProcess.java
 */
package com.siriuserp.administration.service.process;


import org.springframework.stereotype.Component;

import com.siriuserp.sdk.base.Process;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
public class SendMailProcess implements Process<User>
{

    @Override
    public void execute(User user) throws ServiceException
    {
        try
        {
//            if(_admin != null && _admin.getContactInformation() != null && _admin.getContactInformation().getEmail() != null)
//            {
//                String adminEmail = UserHelper.activePerson().getContactInformation().getEmail();
//
//                if(user.getPerson() != null && SiriusValidator.validateParam(user.getPerson().getContactInformation().getEmail()) && SiriusValidator.validateParam(adminEmail))
//                {
//                    StringBuffer sb = new StringBuffer();
//                    sb.append("Dear "+user.getPerson().getFirstName()+" "+user.getPerson().getMiddleName()+" "+user.getPerson().getLastName()+",");
//                    sb.append("\n\n");
//                    sb.append("The administrator has created a new account for you. Here is your login Information:\n\n");
//                    sb.append("User ID: "+user.getUsername()+"\n");
//                    sb.append("Password: "+user.getPassword()+"\n\n");
//                    sb.append("For security reason, please change your password immediately after you log into the system.\n");
//                    sb.append("You can change your password from menu: Tools > Change Password.\n\n");
//                    sb.append("if you are still having login problems please contact the administrator at "+adminEmail);
//                    sb.append("\n\n\n");
//                    sb.append("Regards,\n\n\n\n");
//                    sb.append("Administrator");
//
//                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage(message);
//                    simpleMailMessage.setTo(user.getPerson().getContactInformation().getEmail());
//                    simpleMailMessage.setFrom(adminEmail);
//                    simpleMailMessage.setText(sb.toString());
//                    simpleMailMessage.setSubject("Account Information");
//
//                    mailSender.send(simpleMailMessage);
//
//
//                }
//            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        

    }

}
