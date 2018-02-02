package cn.v1.unionc_user.network_frame;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by qy on 2018/1/31.
 */

public class UnionAPIPackage {



    /**
     * 下载头像
     * @return
     */
    public static Observable<ResponseBody> getcontributorsAvator() {
        return ConnectHttp.getUnionAPI().getcontributorsAvator();
    }
}
