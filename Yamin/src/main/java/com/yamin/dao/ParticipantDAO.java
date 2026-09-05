package com.yamin.dao;

import com.yamin.model.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class ParticipantDAO {

    public void create(Participant p) throws Exception {
        String sql = "INSERT INTO participants (name,email,password,batch_id) VALUES (?,?,?,?)";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            // store bcrypt hash of password
            String hash = BCrypt.hashpw(p.getPassword(), BCrypt.gensalt());
            ps.setString(3, hash);
            if (p.getBatchId() != null) ps.setInt(4, p.getBatchId()); else ps.setNull(4, Types.INTEGER);
            ps.executeUpdate();
        }
    }

    public void update(Participant p) throws Exception {
        String sql = "UPDATE participants SET name=?, email=?, password=?, batch_id=? WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection()) {
            // determine password to store: if caller didn't supply a new password, keep existing hash
            String passwordToStore = p.getPassword();
            if (passwordToStore == null || passwordToStore.isEmpty()) {
                String q = "SELECT password FROM participants WHERE id=?";
                try (PreparedStatement qps = conn.prepareStatement(q)) {
                    qps.setInt(1, p.getId());
                    try (ResultSet rs = qps.executeQuery()) {
                        if (rs.next()) passwordToStore = rs.getString(1); else passwordToStore = null;
                    }
                }
            } else {
                // hash new password
                passwordToStore = BCrypt.hashpw(passwordToStore, BCrypt.gensalt());
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getEmail());
                ps.setString(3, passwordToStore);
                if (p.getBatchId() != null) ps.setInt(4, p.getBatchId()); else ps.setNull(4, Types.INTEGER);
                ps.setInt(5, p.getId());
                ps.executeUpdate();
            }
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM participants WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Participant findById(int id) throws Exception {
        String sql = "SELECT id,name,email,password,batch_id FROM participants WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getObject(5) != null ? rs.getInt(5) : null);
            }
        }
        return null;
    }

    public List<Participant> findAll() throws Exception {
        List<Participant> list = new ArrayList<>();
        String sql = "SELECT id,name,email,password,batch_id FROM participants";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getObject(5) != null ? rs.getInt(5) : null));
            }
        }
        return list;
    }
    

    public List<Participant> findByBatchId(int batchId) throws Exception {
        List<Participant> list = new ArrayList<>();
        String sql = "SELECT id,name,email,password,batch_id FROM participants WHERE batch_id=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, batchId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getObject(5) != null ? rs.getInt(5) : null));
                }
            }
        }
        return list;
    }

    public Participant findByEmailAndPassword(String email, String plainPassword) throws Exception {
        String sql = "SELECT id,name,email,password,batch_id FROM participants WHERE email=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString(4);
                    if (storedHash != null && BCrypt.checkpw(plainPassword, storedHash)) {
                        return new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), storedHash, rs.getObject(5) != null ? rs.getInt(5) : null);
                    }
                }
            }
        }
        return null;
    }

    public Participant findByEmail(String email) throws Exception {
        String sql = "SELECT id,name,email,password,batch_id FROM participants WHERE email=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getObject(5) != null ? rs.getInt(5) : null);
            }
        }
        return null;
    }
}
