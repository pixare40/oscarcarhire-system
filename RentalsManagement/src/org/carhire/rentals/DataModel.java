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
        Installer.EM.getTransaction().begin();
        Query query = Installer.EM.createQuery("SELECT b from Branch b");
        List<Branch> list = (List<Branch>)query.getResultList();
        Installer.EM.getTransaction().commit();
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

    public static List<Customer> getCustomers() {
        Installer.EM.getTransaction().begin();
        Query q = Installer.EM.createQuery("SELECT c from Customer c");
        List<Customer> list = (List<Customer>)q.getResultList();
        Installer.EM.getTransaction().commit();
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

    static void updateCustomer(Customer customer) {
        Installer.EM.getTransaction().begin();
        Installer.EM.persist(customer);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void deleteCustomer(Customer customer) {
        Installer.EM.getTransaction().begin();
        Installer.EM.remove(customer);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void createCustomer(Customer customer) {
        Installer.EM.getTransaction().begin();
        Installer.EM.persist(customer);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void createVehicle(Vehicle vehicle) {
        Installer.EM.getTransaction().begin();
        Installer.EM.persist(vehicle);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static List<Booking> getCustomerBookings(Customer customer) {
        Installer.EM.getTransaction().begin();
        Query q = Installer.EM.createQuery("SELECT b from Booking b WHERE b.customer ="+customer);
        List<Booking> list = (List<Booking>)q.getResultList();
        Installer.EM.getTransaction().commit();
        return list;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void updateBooking(Booking book) {
        Installer.EM.getTransaction().begin();
        Installer.EM.persist(book);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Staff authenticateUser(String username, String password) {
        Installer.EM.getTransaction().begin();
        Query q = Installer.EM.createQuery("SELECT s from Staff s WHERE s.username ="+username);
        Staff mem = (Staff)q.getSingleResult();
        System.out.print(mem);
        if (mem.getPassword()==password){
            return mem;
        }
        else{
            return null;
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void isAdmin(Staff staff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void createStaff(Staff staff) {
        Installer.EM.getTransaction().begin();
        Installer.EM.persist(staff);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static List<Booking> getBookings() {
        Installer.EM.getTransaction().begin();
        Query q = Installer.EM.createQuery("SELECT b from Booking b");
        List<Booking> list = (List<Booking>)q.getResultList();
        Installer.EM.getTransaction().commit();
        return list;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void cancelBooking(Booking book) {
        Installer.EM.getTransaction().begin();
        book.setCancelled(true);
        Installer.EM.persist(book);
        Installer.EM.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static Collection<Booking> getBranchBookings(long branchid) {
        Installer.EM.getTransaction().begin();
        Query q = Installer.EM.createQuery("SELECT b from Booking b WHERE b.branch ="+branchid);
        List<Booking> list = (List<Booking>)q.getResultList();
        Installer.EM.getTransaction().commit();
        return list;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
