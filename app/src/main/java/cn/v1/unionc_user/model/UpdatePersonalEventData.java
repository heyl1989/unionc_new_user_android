package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/8.
 */

public class UpdatePersonalEventData {
    private boolean update;

    public UpdatePersonalEventData(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
