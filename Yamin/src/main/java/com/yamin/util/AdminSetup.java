package com.yamin.util;

import com.yamin.dao.AdminDAO;

public class AdminSetup {
    // Run this once (as a simple Java main) to create an initial admin account.
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java com.yamin.util.AdminSetup <username> <password>");
            return;
        }
        String user = args[0];
        String pass = args[1];
        AdminDAO dao = new AdminDAO();
        dao.createAdmin(user, pass, "ADMIN");
        System.out.println("Admin created: " + user);
    }
}
