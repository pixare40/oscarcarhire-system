/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carhire.authentication;

/**
 *
 * @author Kabaji Egara
 */
public class SecurityManager {
     public static boolean login(String username, String password) {
      if(username.equals("admin") && password.equals("password")) {
         return true;
      } else {
         return false;
      }
   }
    
}
