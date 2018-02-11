package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/9.
 */

public class HomeListData extends BaseData {


    /**
     * data : {"attendingDoctors":[{"clinicId":"50708","clinicName":"小郊亭社区卫生服务站","departName":"骨科","distance":"2.0301","doctorName":"45测试","major":"100","professLevel":"知名专家"}],"recommendDoctors":[{"clinicId":"50708","clinicName":"小郊亭社区卫生服务站","departName":"骨科","distance":"2.0301","doctorName":"45测试","major":"100","professLevel":"知名专家"}],"signedDoctros":[{"clinicId":"50708","clinicName":"小郊亭社区卫生服务站","departName":"骨科","distance":"2.0301","doctorName":"45测试","major":"100","professLevel":"知名专家"}]}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<HomeData> attendingDoctors;
        private List<HomeData> recommendDoctors;
        private List<HomeData> signedDoctros;

        public List<HomeData> getAttendingDoctors() {
            return attendingDoctors;
        }

        public void setAttendingDoctors(List<HomeData> attendingDoctors) {
            this.attendingDoctors = attendingDoctors;
        }

        public List<HomeData> getRecommendDoctors() {
            return recommendDoctors;
        }

        public void setRecommendDoctors(List<HomeData> recommendDoctors) {
            this.recommendDoctors = recommendDoctors;
        }

        public List<HomeData> getSignedDoctros() {
            return signedDoctros;
        }

        public void setSignedDoctros(List<HomeData> signedDoctros) {
            this.signedDoctros = signedDoctros;
        }

        public static class HomeData {
            /**
             *
             * type :
             * clinicId : 50708
             * clinicName : 小郊亭社区卫生服务站
             * departName : 骨科
             * distance : 2.0301
             * doctorName : 45测试
             * major : 100
             * professLevel : 知名专家
             */

            private String type;
            private String clinicId;
            private String clinicName;
            private String departName;
            private String distance;
            private String doctorName;
            private String major;
            private String professLevel;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getClinicId() {
                return clinicId;
            }

            public void setClinicId(String clinicId) {
                this.clinicId = clinicId;
            }

            public String getClinicName() {
                return clinicName;
            }

            public void setClinicName(String clinicName) {
                this.clinicName = clinicName;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getMajor() {
                return major;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public String getProfessLevel() {
                return professLevel;
            }

            public void setProfessLevel(String professLevel) {
                this.professLevel = professLevel;
            }
        }

    }
}
