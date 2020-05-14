package com.hunter.tracelog.upload.http;

import android.content.Context;
import com.hunter.tracelog.upload.BaseUploadTrace;
import com.hunter.tracelog.upload.ITraceLogUpload;

import java.io.File;

/**
 * Description:
 * author: mayuhai
 * created on: 2019-06-20 17:25
 */
public class RetrofitUploadTrace extends BaseUploadTrace {

    private Context context;
    public RetrofitUploadTrace(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void sendReport(String collectId, String title, String body, File file, ITraceLogUpload.OnUploadFinishedListener onUploadFinishedListener) {
//        Map param = WebFrontUtil.getBaseParam();
//        param.put("siteId",param.get("visitSiteId"));
//        param.put("proVersion", param.get("appVersion"));
//        param.put("osVersion", param.get("opAdvice"));
//        param.put("errorMsg", UserControl.getInstance().mapNullableUser(User::getEmail)+"-"+UserControl.getInstance().mapNullableUser(User::getMobile));
//        param.put("channel", ApplicationUtil.getChannelName(context));
//        param.put("errorCode","error0");
//
//        try {
//            if (file.exists()) {
//                RequestBody fileBody = MultipartBody.create(MediaType.parse("application/octet-stream"), file);
//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("file", TraceLog.LOG_DRI + System.currentTimeMillis()+".zip", fileBody)
//                        .addFormDataPart("param", RetrofitUtil.toJSONStr(param))
//                        .addFormDataPart("collectId", collectId)
//                        .build();
//                RetrofitClient.getApiService(BasicApiService.class)
//                        .uploadErrorFile(requestBody, BaseUrlTypes.BASE_MICRO_SERVICE_URL)
//                        .subscribeOn(Schedulers.io()).subscribe(new AbstractFileUploadObserver<BaseResponse>() {
//                    @Override
//                    public void onUploadFailure(Throwable throwable) {
//                        Log.e("uploadLog onUploadFailure", throwable.toString());
//                        onUploadFinishedListener.onError(throwable.toString());
//                    }
//
//                    @Override
//                    public void onUploadSuccess(BaseResponse baseResponse) {
//                        Log.e("uploadLog onUploadSuccess", baseResponse.toString());
//                        onUploadFinishedListener.onSucceed();
//                    }
//
//                    @Override
//                    public void onProgress(int i) {
//                        Log.e("uploadLog onProgress", i + "");
//                    }
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
