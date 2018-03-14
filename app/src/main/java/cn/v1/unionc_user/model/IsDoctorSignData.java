package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/9.
 */

public class IsDoctorSignData extends BaseData {


    /**
     * data : {"isSigned":"0"}
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
         * isSigned : 0
         */

        private String isSigned;

        public String getIsSigned() {
            return isSigned;
        }

        public void setIsSigned(String isSigned) {
            this.isSigned = isSigned;
        }
    }
}
