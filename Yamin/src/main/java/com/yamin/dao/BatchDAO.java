package com.yamin.dao;

import com.yamin.model.Batch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchDAO {

    public void create(Batch b) throws Exception {
        String sql = "INSERT INTO batches (name,timing) VALUES (?,?)";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getName());
            ps.setString(2, b.getTiming());
            ps.executeUpdate();
        }
    }

    public void update(Batch b) throws Exception {
        // fixed SQL: removed extra comma before WHERE
        String sql = "UPDATE batches SET name=?, timing=? WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getName());
            ps.setString(2, b.getTiming());
            ps.setInt(3, b.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM batches WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Batch findById(int id) throws Exception {
        String sql = "SELECT id,name,timing FROM batches WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Batch(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            }
        }
        return null;
    }

    public List<Batch> findAll() throws Exception {
        List<Batch> list = new ArrayList<>();
        String sql = "SELECT id,name,timing FROM batches";
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Batch(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        }
        return list;
    }
}
