package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/14.
 */

public class DoctorInfoIdentifierData extends BaseData {


    /**
     * data : {"doctInfo":{"doctId":"90","doctName":"徐生生","gender":"","identifier":"22853568982b408597faa111c322e82c","telPhone":"15010888310"}}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * doctInfo : {"doctId":"90","doctName":"徐生生","gender":"","identifier":"22853568982b408597faa111c322e82c","telPhone":"15010888310"}
         */

        private DoctInfoData doctInfo;

        public DoctInfoData getDoctInfo() {
            return doctInfo;
        }

        public void setDoctInfo(DoctInfoData doctInfo) {
            this.doctInfo = doctInfo;
        }

        public static class DoctInfoData {
            /**
             * doctId : 90
             * doctName : 徐生生
             * gender :
             * identifier : 22853568982b408597faa111c322e82c
             * telPhone : 15010888310
             */

            private String doctId;
            private String doctName;
            private String gender;
            private String identifier;
            private String telPhone;

            public String getDoctId() {
                return doctId;
            }

            public void setDoctId(String doctId) {
                this.doctId = doctId;
            }

            public String getDoctName() {
                return doctName;
            }

            public void setDoctName(String doctName) {
                this.doctName = doctName;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getTelPhone() {
                return telPhone;
            }

            public void setTelPhone(String telPhone) {
                this.telPhone = telPhone;
            }
        }
    }
}
