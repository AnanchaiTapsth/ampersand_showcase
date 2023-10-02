package com.example.ampersand02.common;

public class Constants {

    public static final String JSON_UTF_8 = "application/json;charset=utf-8";

    public static final String defaultPrefixCode = "99";

    public static class RequestHeader {
        public static final String AUTHORIZATION = "Authorization";
        public static final String REQUEST_DATE = "RequestDate";
        public static final String RESPONSE_DATE = "ResponseDate";
    }

    public enum MESSAGE {
        INFO_AUTH_0000("ระบบดำเนินการสำเร็จ"),
        WRN_AUTH_0001("บัญชีของคุณถูก login จากเครื่องอื่น"),
        ERR_AUTH_9999("ระบบไม่สามารถดำเนินการได้"),
        ERR_AUTH_0000("มีข้อมูล Username นี้อยู่ในระบบแล้ว"),
        ERR_AUTH_1111("มีข้อมูล Role นี้อยู่ในระบบแล้ว"),
        ERR_AUTH_2222("มีข้อมูล Permission นี้อยู่ในระบบแล้ว"),

        ERR_AUTH_0001("ไม่มี User Id นี้อยู่ในระบบ"),
        ERR_AUTH_0002("ไม่มีหน้าที่ (Role) นี้อยู่ในระบบนี้"),
        ERR_AUTH_0003("Action ไม่ถูกต้อง"),
        ERR_AUTH_0004("ไม่มี Permission นี้อยู่ในระบบนี้"),

        // Master Service
        INFO_MTS_0000("ระบบดำเนินการสำเร็จ"),
        WRN_MTS_0001("บัญชีของคุณถูก login จากเครื่องอื่น"),
        ERR_MTS_9999("ระบบไม่สามารถดำเนินการได้"),
        ERR_MTS_11111("คุณไม่มีสิทธิ์ทำรายการนี้"),
        ERR_MTS_0001("มีข้อมูลนี้แล้วในฐานข้อมูล"),
        ERR_MTS_0002("กรุณาระบุ id"),
        ERR_MTS_0003("ไม่พบ id ในระบบ");
        ;

        private final String msg;

        MESSAGE(String message) {
            this.msg = message;
        }

        public String getMsg() {
            return msg;
        }


    }

    public static class RedisKey {
        // %s = username
        public static final String CURRENT = "user_current_token:%s";
        // %s = username
        public static final String LOCKED_ACCOUNT = "locked_account:%s";
        // %s1 = action_name, %s2 = username
        public static final String COUNT_SMS_OTP = "count_sms_otp:%s:%s";
        // %s1 = action_name, %s2 = username
        public static final String COUNT_EMAIL_OTP = "count_email_otp:%s:%s";
        // %s1 = action_name, %s2 = username
        public static final String LOCKED_OTP = "locked_otp:%s:%s";
        // %s1 = action_name, %s2 = username, %s3 = otp
        public static final String OTP_VERIFY = "otp_verify:%s:%s:%s";
    }

    public static class Sort {
        public static final String ASC = "A";
        public static final String DESC = "D";
    }

     public enum AreaType {
         MAIN,
         SUB
     }

}
