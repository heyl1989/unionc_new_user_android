package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/2/27.
 */

public class DoctorInfoData extends BaseData {


    /**
     * data : {"doctors":{"AUniress":"北京市,市辖区,东城区,锐创国际","ClinicName":"苏涛的测试医院 ","CollectionCount":"0","DepartName":"口腔科","Distance":"0.88","DoctId":"45","DoctorName":"苏涛1111","EvaluateCount":"0","FirstClinicName":"小郊亭社区卫生服务站","ImagePath":"http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/12/0/952f4d8d-7832-4a7c-b7ee-97c19c27a598_u=3247990642,490396215&fm=27&gp=0.jpg","IsAttention":"0","IsRecom":"0","IsShare":"0","Latitude":"40.015698","Longitude":"116.491371","Major":"擅长","ProfessLevel":"知名专家","RecommendCount":"0","Remarks":"备注","Telphone":"0108888888"},"familyDoctFlag":"1","questions":[{"PageView":"15","Question":"提问","QuestionId":"1"}]}
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
         * doctors : {"AUniress":"北京市,市辖区,东城区,锐创国际","ClinicName":"苏涛的测试医院 ","CollectionCount":"0","DepartName":"口腔科","Distance":"0.88","DoctId":"45","DoctorName":"苏涛1111","EvaluateCount":"0","FirstClinicName":"小郊亭社区卫生服务站","ImagePath":"http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/12/0/952f4d8d-7832-4a7c-b7ee-97c19c27a598_u=3247990642,490396215&fm=27&gp=0.jpg","IsAttention":"0","IsRecom":"0","IsShare":"0","Latitude":"40.015698","Longitude":"116.491371","Major":"擅长","ProfessLevel":"知名专家","RecommendCount":"0","Remarks":"备注","Telphone":"0108888888"}
         * familyDoctFlag : 1
         * questions : [{"PageView":"15","Question":"提问","QuestionId":"1"}]
         */

        private DoctorsData doctors;
        private String familyDoctFlag;
        private List<QuestionsData> questions;

        public DoctorsData getDoctors() {
            return doctors;
        }

        public void setDoctors(DoctorsData doctors) {
            this.doctors = doctors;
        }

        public String getFamilyDoctFlag() {
            return familyDoctFlag;
        }

        public void setFamilyDoctFlag(String familyDoctFlag) {
            this.familyDoctFlag = familyDoctFlag;
        }

        public List<QuestionsData> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsData> questions) {
            this.questions = questions;
        }

        public static class DoctorsData {
            /**
             * AUniress : 北京市,市辖区,东城区,锐创国际
             * ClinicName : 苏涛的测试医院
             * CollectionCount : 0
             * DepartName : 口腔科
             * Distance : 0.88
             * DoctId : 45
             * DoctorName : 苏涛1111
             * EvaluateCount : 0
             * FirstClinicName : 小郊亭社区卫生服务站
             * ImagePath : http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/12/0/952f4d8d-7832-4a7c-b7ee-97c19c27a598_u=3247990642,490396215&fm=27&gp=0.jpg
             * IsAttention : 0
             * IsRecom : 0
             * IsShare : 0
             * Latitude : 40.015698
             * Longitude : 116.491371
             * Major : 擅长
             * ProfessLevel : 知名专家
             * RecommendCount : 0
             * Remarks : 备注
             * Telphone : 0108888888
             */

            private String AUniress;
            private String ClinicName;
            private String CollectionCount;
            private String DepartName;
            private String Distance;
            private String DoctId;
            private String DoctorName;
            private String EvaluateCount;
            private String FirstClinicName;
            private String ImagePath;
            private String IsAttention;
            private String IsRecom;
            private String IsShare;
            private String Latitude;
            private String Longitude;
            private String Major;
            private String ProfessLevel;
            private String RecommendCount;
            private String Remarks;
            private String Telphone;
            private String Identifier;

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String identifier) {
                Identifier = identifier;
            }

            public String getAUniress() {
                return AUniress;
            }

            public void setAUniress(String AUniress) {
                this.AUniress = AUniress;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String ClinicName) {
                this.ClinicName = ClinicName;
            }

            public String getCollectionCount() {
                return CollectionCount;
            }

            public void setCollectionCount(String CollectionCount) {
                this.CollectionCount = CollectionCount;
            }

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String DepartName) {
                this.DepartName = DepartName;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String Distance) {
                this.Distance = Distance;
            }

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String DoctId) {
                this.DoctId = DoctId;
            }

            public String getDoctorName() {
                return DoctorName;
            }

            public void setDoctorName(String DoctorName) {
                this.DoctorName = DoctorName;
            }

            public String getEvaluateCount() {
                return EvaluateCount;
            }

            public void setEvaluateCount(String EvaluateCount) {
                this.EvaluateCount = EvaluateCount;
            }

            public String getFirstClinicName() {
                return FirstClinicName;
            }

            public void setFirstClinicName(String FirstClinicName) {
                this.FirstClinicName = FirstClinicName;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String ImagePath) {
                this.ImagePath = ImagePath;
            }

            public String getIsAttention() {
                return IsAttention;
            }

            public void setIsAttention(String IsAttention) {
                this.IsAttention = IsAttention;
            }

            public String getIsRecom() {
                return IsRecom;
            }

            public void setIsRecom(String IsRecom) {
                this.IsRecom = IsRecom;
            }

            public String getIsShare() {
                return IsShare;
            }

            public void setIsShare(String IsShare) {
                this.IsShare = IsShare;
            }

            public String getLatitude() {
                return Latitude;
            }

            public void setLatitude(String Latitude) {
                this.Latitude = Latitude;
            }

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public String getMajor() {
                return Major;
            }

            public void setMajor(String Major) {
                this.Major = Major;
            }

            public String getProfessLevel() {
                return ProfessLevel;
            }

            public void setProfessLevel(String ProfessLevel) {
                this.ProfessLevel = ProfessLevel;
            }

            public String getRecommendCount() {
                return RecommendCount;
            }

            public void setRecommendCount(String RecommendCount) {
                this.RecommendCount = RecommendCount;
            }

            public String getRemarks() {
                return Remarks;
            }

            public void setRemarks(String Remarks) {
                this.Remarks = Remarks;
            }

            public String getTelphone() {
                return Telphone;
            }

            public void setTelphone(String Telphone) {
                this.Telphone = Telphone;
            }
        }

        public static class QuestionsData {
            /**
             * PageView : 15
             * Question : 提问
             * QuestionId : 1
             */

            private String PageView;
            private String Question;
            private String QuestionId;

            public String getPageView() {
                return PageView;
            }

            public void setPageView(String PageView) {
                this.PageView = PageView;
            }

            public String getQuestion() {
                return Question;
            }

            public void setQuestion(String Question) {
                this.Question = Question;
            }

            public String getQuestionId() {
                return QuestionId;
            }

            public void setQuestionId(String QuestionId) {
                this.QuestionId = QuestionId;
            }
        }
    }
}
