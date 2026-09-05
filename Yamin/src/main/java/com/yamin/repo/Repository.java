package com.yamin.repo;

import com.yamin.dao.BatchDAO;
import com.yamin.dao.ParticipantDAO;
import com.yamin.dao.AdminDAO;

public class Repository {
    private static final BatchDAO batchDAO = new BatchDAO();
    private static final ParticipantDAO participantDAO = new ParticipantDAO();
    private static final AdminDAO adminDAO = new AdminDAO();

    public static BatchDAO batches() { return batchDAO; }
    public static ParticipantDAO participants() { return participantDAO; }
    public static AdminDAO admins() { return adminDAO; }
}
