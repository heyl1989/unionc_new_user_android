package cn.v1.unionc_user.model;

import java.io.Serializable;

/**
 * Created by qy on 2018/2/9.
 */

public class UserInfoData extends BaseData {


    /**
     * data : {"ClinicCount":0,"DoctorCount":0,"EvaluateCount":0,"Gender":"","HeadImage":"","IsCertification":0,"ServicePhone":"010-8888888","Telphone":"138****5545","UserName":"138****5545","WorkingHours":"工作时间：上午9:00-下午18:00"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData implements Serializable{
        /**
         * ClinicCount : 0
         * DoctorCount : 0
         * EvaluateCount : 0
         * Gender :
         * HeadImage :
         * IsCertification : 0
         * ServicePhone : 010-8888888
         * Telphone : 138****5545
         * UserName : 138****5545
         * WorkingHours : 工作时间：上午9:00-下午18:00
         */

        private int ClinicCount;
        private int DoctorCount;
        private int EvaluateCount;
        private String Gender;
        private String HeadImage;
        private int IsCertification;
        private String ServicePhone;
        private String Telphone;
        private String UserName;
        private String WorkingHours;

        public int getClinicCount() {
            return ClinicCount;
        }

        public void setClinicCount(int ClinicCount) {
            this.ClinicCount = ClinicCount;
        }

        public int getDoctorCount() {
            return DoctorCount;
        }

        public void setDoctorCount(int DoctorCount) {
            this.DoctorCount = DoctorCount;
        }

        public int getEvaluateCount() {
            return EvaluateCount;
        }

        public void setEvaluateCount(int EvaluateCount) {
            this.EvaluateCount = EvaluateCount;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getHeadImage() {
            return HeadImage;
        }

        public void setHeadImage(String HeadImage) {
            this.HeadImage = HeadImage;
        }

        public int getIsCertification() {
            return IsCertification;
        }

        public void setIsCertification(int IsCertification) {
            this.IsCertification = IsCertification;
        }

        public String getServicePhone() {
            return ServicePhone;
        }

        public void setServicePhone(String ServicePhone) {
            this.ServicePhone = ServicePhone;
        }

        public String getTelphone() {
            return Telphone;
        }

        public void setTelphone(String Telphone) {
            this.Telphone = Telphone;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getWorkingHours() {
            return WorkingHours;
        }

        public void setWorkingHours(String WorkingHours) {
            this.WorkingHours = WorkingHours;
        }
    }
}
