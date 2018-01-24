package com.basicfu.system.util

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author fuliang
 * @date 12/5/2016
 */
object SmsUtil {

    fun send(recNum: String) {
        //        String smscode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));//随机6位验证码
        val SIGN_METHOD = "hmac"
        val params = HashMap<String, String>()
        params.put("method", "alibaba.aliqin.fc.sms.num.send")
        params.put("app_key", "23542706")
        params.put("sign_method", SIGN_METHOD)
        params.put("timestamp", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
        params.put("format", "json")
        params.put("v", "2.0")
        //business params
        params.put("extend", "123")
        params.put("sms_type", "normal")
        params.put("sms_free_sign_name", "简游平台")
        params.put("sms_param", "{'code':123456}")
        params.put("rec_num", "18611331161")
        params.put("sms_template_code", "SMS_71580057")
        try {
            val sign = TaobaoUtil.signTopRequest(params, "21e1602bf6d23acbc68e99a7b2ad59ef", SIGN_METHOD)
            params.put("sign", sign)
            val result = HttpUtil.post("http://gw.api.taobao.com/router/rest", params)
            println(result)
        } catch (e: IOException) {
        }

    }
}


//
//    JSONObject jsonObject = JSON.parseObject(rsp.getBody());
//    JSONObject json = jsonObject.getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
//        if (json != null && json.getJSONObject("result").getString("success").equals("true")) {
//                msg="获取验证码成功，请注意查收";
//                resultMap.put("code",0);
//                resultMap.put("smscode",smscode);
//                } else {
//                resultMap.put("success",false);
//                JSONObject jsonError = jsonObject.getJSONObject("error_response");
//                String errorCode = jsonError.getString("sub_code");
////            {"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.BUSINESS_LIMIT_CONTROL","sub_msg":"触发业务流控","request_id":"rxngl2o5jrh6"}}
//                switch (errorCode) {
//                case "isv.MOBILE_NUMBER_ILLEGAL":
//                msg = "手机号码格式错误";
//                break;
//                case "isv.BUSINESS_LIMIT_CONTROL":
//                msg = "获取频繁，请稍后重试";//	短信验证码，使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时，10条/天。一个手机号码通过阿里大于平台只能收到40条/天。 短信通知，使用同一签名、同一模板，对同一手机号发送短信通知，允许每天50条（自然日）。
//                break;
//                case "isv.AMOUNT_NOT_ENOUGH":
//                msg = "获取手机号码错误，请联系管理员";//实际错误为账号余额不足
//                break;
//default:
//        msg = "获取手机号码错误，请联系管理员";
//        break;
//        }
//        resultMap.put("code",1);
//        }
//        resultMap.put("msg",msg);