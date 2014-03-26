/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carhire.rentals;

import dataentities.Branch;
import org.openide.modules.ModuleInstall;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Installer extends ModuleInstall {

   public static final EntityManagerFactory EMF;
   public static final EntityManager        EM;
   
   static {
      try {
         //System.setProperty("derby.system.home", System.getProperty("netbeans.user"));
         EMF = Persistence.createEntityManagerFactory("DataEnttiesPU");
         EM = EMF.createEntityManager();
      } catch (Throwable ex) {
         System.err.println("Initial EntityManagerFactory creation failed." + ex);
         throw new ExceptionInInitializerError(ex);
      }
   }

   @Override
   public void close() {
      EM.close();
      EMF.close();
   }

   @Override
   public void restored() {
      
   }

}
