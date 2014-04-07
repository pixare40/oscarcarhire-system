 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carhire.rentals.bookingwizard;

import dataentities.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.net.ssl.SSLSocketFactory;
//import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.mail.Authenticator;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.carhire.rentals.*;
import org.joda.time.DateTime;
import org.joda.time.Days;
//import org.oracle.javamail;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
 @ActionID(category="Demo", id="org.carhire.rentals.bookingwizard.BookingWizardAction")
 @ActionRegistration(displayName="Open Booking Wizard")
 @ActionReference(path="Menu/Tools", position=10)
public final class BookingWizardAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new BookingWizardPanel1());
        panels.add(new BookingWizardPanel2());
        panels.add(new BookingWizardPanel3());
        panels.add(new BookingWizardPanel4());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Make New Booking");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            // do something
            Customer customer = (Customer) wiz.getProperty("customer");
            Vehicle vehicle = (Vehicle) wiz.getProperty("vehicle");
            String lossDW = (String) wiz.getProperty("lossDW");
            String suppLI = (String) wiz.getProperty("suppLI");
            String pAI = (String) wiz.getProperty("pAI");
            String thirdPI = (String) wiz.getProperty("thirdPI");
            Date dateRented = (Date) wiz.getProperty("daterented");
            Date dateToReturn = (Date) wiz.getProperty("dateToReturn");
            DateTime drent = new DateTime(dateRented);
            DateTime dreturn = new DateTime(dateToReturn);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            String customername = customer.getFullname();
            String customeremail = customer.getEmailaddress();
            String vehicledetails = vehicle.getDescription();
            String paidon = dateFormat.format(dateRented);
            int days = Days.daysBetween(drent, dreturn).getDays();
            String returndate = dateFormat.format(dateToReturn);
            StringBuilder insurance = new StringBuilder();
            insurance.append("Loss Damage Waiver: "+lossDW+"\n");
            insurance.append("Personal Accidental Insurance: "+pAI+"\n");
            insurance.append("Supplemental Loss Insuarnce: "+suppLI);
            insurance.append("Third Party Insuarnce: "+thirdPI+"\n");
            String concatinsurance = insurance.toString();
            int price = 50 * days;
            
            
            sendReceipt(customername, customeremail, vehicledetails,paidon,returndate,concatinsurance,price);
            
            Installer.EM.getTransaction().begin();
            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setVehicle(vehicle);
            booking.setLossDW(lossDW);
            booking.setSuppLI(suppLI);
            booking.setThirdPI(thirdPI);
            booking.setpAI(pAI);
            booking.setDateRented(dateRented);
            booking.setDateToReturn(dateToReturn);
            booking.setDateCreated(new Date());
            booking.setPrice(price);
            Installer.EM.persist(booking);
            Installer.EM.getTransaction().commit();
        }
    }

    private void sendReceipt(String customer,String email, String vehicle, String paidon,String returndate, String insure,int price) {
         boolean issent = true;
            try{
            Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
            
            final String username = "kabzegara@gmail.com";
            final String password = "miriam12";
            
            Session mailsession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
            Message msg = new MimeMessage(mailsession);
            
            InternetAddress fromaddy = new InternetAddress("kabzegara@gmail.com");
            InternetAddress toaddy = new InternetAddress(email);
            msg.setFrom(fromaddy);
            msg.setRecipient(Message.RecipientType.TO, toaddy);
            msg.setSubject("Test Receipt");
            msg.setContent(""
                    + "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
"\n" +
"		<tr><td width=\"600px\">\n" +
"\n" +
"		<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" margin=\"0 0 2em 0\">\n" +
"			<tr><td width=\"600px\" align=\"center\" style=\"font-size:140%;\"><strong>RECEIPT</strong></td></tr> \n" +
"			<tr><td width=\"600px\" align=\"center\" style=\"font-size:140%;\"><strong>Oscar's Car Hire</strong></td></tr>\n" +
"			<tr><td width=\"600px\" align=\"center\" style=\"font-size:140%;\"><strong>Reimagine Home</strong></td></tr>\n" +
"		</table>\n" +
"\n" +
"		<p margin=\"0 0 1.6em 0\" style=\"font-size:120%;\">" + customer + "</p>\n" +
"		<p margin=\"0 0 1.6em 0\"> Paid on: "+paidon +  "</p>\n" +
"\n" +
"		<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
"			<tr><td width=\"150px\"><strong>Return Date:</strong></td><td width=\"150px\">" + returndate +"</td></tr>\n" +
"			<tr><td width=\"150px\"><strong>Departure Date:</strong></td><td width=\"150px\">" + paidon +"</td></tr>\n" +
"			<tr><td width=\"150px\"><strong>Payment Method:</strong></td><td width=\"150px\"> Cash</td></tr>\n" +
"		</table>\n" +
"\n" +
"		<tr><td width=\"600px\"><p margin=\"1.6em 0 1.6em 0\"><hr></p></td></tr>\n" +
"\n" +
"		<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
"		<tr>\n" +
"			<th align=\"left\" width=\"200px\">Vehicle Description</th><th align=\"center\" width=\"200px\">Insurance</th><th align=\"right\" width=\"200px\">Price</th>\n" +
"		</tr>\n" +
"		<tr>\n" +
"			<td align=\"left\" width=\"200px\"><p margin=\"0 0 1.6em 0\">"+vehicle+"</p></td>\n" +
"			<td align=\"center\" width=\"200px\"><p margin=\"0 0 1.6em 0\">"+insure+"</p></td>\n" +
"			<td align=\"right\" width=\"200px\"><p margin=\"0 0 1.6em 0\">$50 a Day</p></td>\n" +
"		</tr>\n" +
"		<tr>\n" +
"			<td colspan=\"3\" align=\"right\" width=\"600px\">Total Paid: $"+price+"</td>\n" +
"		</tr>\n" +
"			<td colspan=\"3\" align=\"right\" width=\"600px\"><strong>Thank you!</strong>\n" +
"		</tr>\n" +
"		</table>\n" +
"\n" +
"		<p margin=\"0 0 0.6em 0\">Have fun on your onward adventures!</p> \n" +
"		<p margin=\"0 0 0.6em 0\">The Oscar's Team</p>\n" +
"\n" +
"		<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" margin=\"0 0 2em 0\">\n" +
"		<tr>\n" +
"			<td width=\"600px\" align=\"center\">:::: Oscar's Car Hire ::::\n" +
"			</td>\n" +
"		</tr>\n" +
"		<tr>\n" +
"			<td width=\"600px\" align=\"center\">:::: Bradford University BD7 1DP, United Kingdom ::::\n" +
"			</td>\n" +
"		</tr>\n" +
"		</table>\n" +
"\n" +
"	</td></tr>\n" +
"</body></table>","text/html");
            
            Transport.send(msg);
            System.out.println("Transporting");
            }
            catch(Exception f){
                System.out.print("Message Not Sent "+ f.getMessage());
                issent = false;
            }
            if (issent==true){
                System.out.print("Message Sent Succesfully");
            }
    }

}
