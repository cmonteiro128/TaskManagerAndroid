package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/24/2015.
 */
public interface MangoUser {
    public String[] getUsername();
    public void setUsername(String name);
    public void setPassword(String password);
    public String getEmail();
    public void setEmail(String email);
    public String getUserID();
    public String[] getDevices();
    public int addDevice(String deviceID);
    public int removeDevice(String deviceID);
    public String[] getProjectIDs();
    public int addProject(String projectID);
    public int removeProject(String projectID);
}
