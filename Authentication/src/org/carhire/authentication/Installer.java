/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carhire.authentication;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall implements ActionListener {
    private LoginPanel panel = new LoginPanel();
    private DialogDescriptor d = null;
    

    @Override
    public void restored() {
        // TODO
        d = new DialogDescriptor(panel, "Login", true, this);
      d.setClosingOptions(new Object[]{});
      d.addPropertyChangeListener(new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent event) {
            if(event.getPropertyName().equals(DialogDescriptor.PROP_VALUE)
               && event.getNewValue() == DialogDescriptor.CLOSED_OPTION) {
               LifecycleManager.getDefault().exit();
            }
         }
      });
        DialogDisplayer.getDefault().notifyLater(d);
    }
    
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==DialogDescriptor.CANCEL_OPTION){
            LifecycleManager.getDefault().exit();
        } else{
            if(!SecurityManager.login(panel.getUsername(),panel.getPassword())){
                panel.setInfo("Wrong Username or Password");
            }else{
                d.setClosingOptions(null);
            }
        }
    }

}
