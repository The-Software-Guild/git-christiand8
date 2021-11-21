/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster;

import com.sg.classroster.controller.ClassRosterController;
import com.sg.classroster.ui.*;
import com.sg.classroster.dao.*;
import com.sg.classroster.service.*;

/**
 *
 * @author cd
 */
public class App {
    
    public static void main(String[] args) {
        // Instantiate the UserIO implementation
        UserIO myIO = new UserIOConsoleImpl();
        
        // Instantiate teh View and wire the UserIO implementation into it
        ClassRosterView myView = new ClassRosterView(myIO);
        
        // Instantiate the DAO
        ClassRosterDao myDao = new ClassRosterDaoFileImpl();
        
        // Instantiate the Audit DAO
        ClassRosterAuditDao myAuditDao = new ClassRosterAuditDaoFileImpl();
        
        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
        ClassRosterServiceLayer myService = new ClassRosterServiceLayerImpl(myDao, myAuditDao);
        
        // Instantiate the Controller and wire the Service Layer into it
        ClassRosterController controller = new ClassRosterController(myService, myView);
        
        // Kick off the Controller
        controller.run();
    }
}
