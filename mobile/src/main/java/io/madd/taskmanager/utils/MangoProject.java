package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/24/2015.
 */
public interface MangoProject {
    public String getProjectID();
    public String getAdmin();
    public String[] getMembers();
    public int addMember(String memberID);
    public int removeMember(String memberID);
    public String getName();
    public void setName(String newName);
    public String getDescription();
    public void setDescription(String newName);
    public Object getPhotoFile();
    public void setPhotoFile(Object icon);
}
