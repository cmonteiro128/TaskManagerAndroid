package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/24/2015.
 */

import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class ParseMangoUser extends ParseUser implements MangoUser {
    public String[] getDevices(){
        List<String> deviceList = getList("devices");
        String[] deviceArray = new String[deviceList.size()];
        for (int i = 0; i < deviceList.size(); i++){
            deviceArray[i] = deviceList.get(i);
        }
        return deviceArray;
    }

    public String[] getProjectIDs(){
        List<String> projectList = getList("projects");
        String[] projectArray = new String[projectList.size()];
        for (int i = 0; i < projectList.size(); i++){
            projectArray[i] = projectList.get(i);
        }
        return projectArray;
    }

    public String getUserID(){
        return super.getObjectId();
    }

    public int addDevice(String deviceID){
        add("devices", deviceID);
        return 0;
    }

    public int removeProject(String projectID){
        List<String> projectList = getList("projects");
        boolean successful = projectList.remove(projectID);
        put("projects", projectList);
        if (successful){
            return 0;
        }else{
            return -1;
        }
    }

    public int removeDevice(String deviceID){
        List<String> deviceList = getList("devices");
        boolean successful = deviceList.remove(deviceID);
        put("devices", deviceList);
        if (successful){
            return 0;
        }else{
            return -1;
        }
    }

    public int addProject(String projectID){
        add("project", projectID);
        return 0;
    }
}
