/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.carhire.rentals;

import dataentities.*;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Kabaji Egara
 */
public class DataModel {

    //@SuppressWarning("unchecked")
    public static List<Branch> getBranches() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("DataEnttiesPU").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT b from Branch b");
        List<Branch> list = (List<Branch>)query.getResultList();
        entityManager.getTransaction().commit();
        return list;
    }
    
    public static List<Vehicle> getVehicles(){
        Installer.EM.getTransaction().begin();
        Query query = Installer.EM.createQuery("SELECT v From Vehicle v");
        List<Vehicle> list = (List<Vehicle>)query.getResultList();
        Installer.EM.getTransaction().commit();
        return list;  
    }
    
    public static List<Staff> getStaffs(){
        Installer.EM.getTransaction().begin();
        Query q = Installer.EM.createQuery("SELECT s from Staff s");
        List<Staff> list = (List<Staff>)q.getResultList();
        Installer.EM.getTransaction().commit();
        return list;
    }

    static List<Customer> getCustomers() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("DataEnttiesPU").createEntityManager();
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("SELECT c from Customer c");
        List<Customer> list = (List<Customer>)q.getResultList();
        entityManager.getTransaction().commit();
        return list;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void deleteVehicle(Vehicle vehicle) {
        Installer.EM.getTransaction().begin();
        Installer.EM.remove(vehicle);
        Installer.EM.getTransaction().commit();
    }

    static void updateVehicle(Vehicle vehicle) {
        Installer.EM.getTransaction().begin();
        Installer.EM.persist(vehicle);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
