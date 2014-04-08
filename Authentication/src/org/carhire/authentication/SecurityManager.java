/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carhire.authentication;

import dataentities.Staff;
import org.carhire.rentals.DataModel;

/**
 *
 * @author Kabaji Egara
 */
public class SecurityManager {
    public boolean isAuthenticated;
     public static boolean login(String username, String password) {
      if(username.equals("admin") && password.equals("password")) {
         return true;
      }
      else{
          return false;
      }
   }

    public boolean isIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
    
}
