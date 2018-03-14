package cn.v1.unionc_user.network_frame;

import java.io.File;
import java.util.Map;

import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.DoctorAnswerDetailData;
import cn.v1.unionc_user.model.DoctorEvaluateData;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.model.DoctorInfoIdentifierData;
import cn.v1.unionc_user.model.DoctorScheduleData;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.IsDoctorSignData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.TIMSigData;
import cn.v1.unionc_user.model.UpdateFileData;
import cn.v1.unionc_user.model.UserInfoData;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by qy on 2018/1/31.
 */

public interface UnionAPI {
    /**
     * 验证码发送
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("authcode-send")
    Observable<BaseData> getAuthCode(@FieldMap Map<String, Object> params);

    /**
     * 登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginData> login(@FieldMap Map<String, Object> params);

    /**
     * 获取TIM sig
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("instant-msg")
    Observable<TIMSigData> getTIMSig(@FieldMap Map<String, Object> params);


    /**
     * 获取用户信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-info")
    Observable<UserInfoData> getUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 获取首页信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/home-page2")
    Observable<HomeListData> getHomeList(@FieldMap Map<String, Object> params);


    /**
     * 获取医生详细信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/doctor-info")
    Observable<DoctorInfoData> getDoctorInfo(@FieldMap Map<String, Object> params);

    /**
     * 获取医生详细信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/doctor-info-by-param")
    Observable<DoctorInfoIdentifierData> doctorInfoByParam(@FieldMap Map<String, Object> params);


    /**
     * 上传照片
     *
     * @param data
     * @param encryption
     * @param imgs
     * @return
     */
    @Multipart
    @POST("upload/upload-image")
    Observable<UpdateFileData> uploadImge(@Query("data") String data,
                                          @Query("encryption") boolean encryption,
                                          @Part MultipartBody.Part imgs);

    /**
     * 修改用户信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/update-user-info")
    Observable<BaseData> updateUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 实名认证
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/certification")
    Observable<BaseData> certification(@FieldMap Map<String, Object> params);

    /**
     * 实名认证
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/is-certification")
    Observable<BaseData> isCertification(@FieldMap Map<String, Object> params);

    /**
     * 查询医生排班
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/doctor-schedule")
    Observable<DoctorScheduleData> doctorSchedule(@FieldMap Map<String, Object> params);

    /**
     * 查询医生回答
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("doctor/question-info")
    Observable<DoctorAnswerDetailData> getDoctorAnswer(@FieldMap Map<String, Object> params);

    /**
     * 查询医生是否签约
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/is-signed")
    Observable<IsDoctorSignData> isDoctorSign(@FieldMap Map<String, Object> params);

    /**
     * 签约医生
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/sign-family-doctor")
    Observable<BaseData> signDoctor(@FieldMap Map<String, Object> params);

    /**
     * 推荐和不推荐医生
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/save-doctor-evaluate-star")
    Observable<BaseData> evaluateDoctor(@FieldMap Map<String, Object> params);

    /**
     * 关注取消接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/attention")
    Observable<BaseData> attention(@FieldMap Map<String, Object> params);

    /**
     * 医生和医院评价
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/evaluates")
    Observable<DoctorEvaluateData> evaluates(@FieldMap Map<String, Object> params);

    /**
     * 保存医生评价
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("clinic/save-doctor-evaluate")
    Observable<BaseData> saveDoctorEvaluate(@FieldMap Map<String, Object> params);
}
