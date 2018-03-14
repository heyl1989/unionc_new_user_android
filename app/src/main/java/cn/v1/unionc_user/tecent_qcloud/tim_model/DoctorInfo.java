package cn.v1.unionc_user.tecent_qcloud.tim_model;

import java.io.Serializable;

/**
 * Created by qy on 2018/3/14.
 */

public class DoctorInfo implements Serializable{

    private String identifier;
    private String imagePath;
    private String doctorName;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
