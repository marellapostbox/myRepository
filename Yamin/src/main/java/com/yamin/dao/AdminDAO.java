package com.yamin.dao;

import com.yamin.model.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public Admin findByUsername(String username) throws Exception {
        String sql = "SELECT id, name, username, password_hash, role FROM admins WHERE username = ?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
            }
        }
        return null;
    }

    public void createAdmin(String name, String username, String plainPassword, String role) throws Exception {
        String sql = "INSERT INTO admins (name, username, password_hash, role) VALUES (?,?,?,?)";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, name);
            ps.setString(2, username);
            // store a BCrypt hash of the password
            String hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
            ps.setString(3, hash);
            ps.setString(4, role != null ? role : "ADMIN");
            ps.executeUpdate();
        }
    }

    // convenience overload for callers that don't supply a role
    public void createAdmin(String name, String username, String plainPassword) throws Exception {
        createAdmin(name, username, plainPassword, null);
    }

    public boolean verifyPassword(String username, String plainPassword) throws Exception {
        Admin a = findByUsername(username);
        if (a == null) return false;
        if(a.getPasswordHash().equals(plainPassword)) {
        	return true;
        }else {
        	return false;
        }
    }

    public List<Admin> findAll() throws Exception {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT id,name,username,password_hash,role FROM admins";
        try (Connection conn = ConnectionManager.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }
        return list;
    }
}
