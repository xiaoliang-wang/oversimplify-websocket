package org.oversimplify.http.test;

import org.oversimplify.httpclient.HttpRequester;
import org.oversimplify.threadpool.TaskPools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {




    public static void main(String[] args) {

        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Map<String,Object> params = new HashMap<>();
        params.put("transaction_id","4200000208201809116712161484");
        params.put("nonce_str","gmK8eLkOTX1a8VU1cKHk5v8MoopkaN5k");
        params.put("bank_type","CFT");
        params.put("openid","obMvw1NRn7EUKFAGpy7IMtIx-NMU");
        params.put("sign","9EB12031D8C7FF33B75840860FAC162696A775303D245F029553A7CF96CBF663");
        params.put("fee_type","CNY");

        params.put("mch_id","1502590621");
        params.put("cash_fee","50");
        params.put("out_trade_no","test1536664719481");
        params.put("appid","wx2162033865fd0626");
        params.put("total_fee","50");
        params.put("trade_type","NATIVE");
        params.put("result_code","SUCCESS");
        params.put("time_end","20180911192134");
        params.put("is_subscribe","N");
        params.put("return_code","SUCCESS");
        try {
            String post = HttpRequester.post(url, params);
            System.out.println(post);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        for (int i = 0; i<12;i++){
//
//            TaskPools.getTaskPoolsImpl().execute(new Runnable() {
//                @Override
//                public void run() {
////                    for (;;){
//                        Map<String,Object> params = new HashMap<>(1);
//                        String phone = 17001+""+((int)((Math.random()*9+1)*100000))+"";
//                        params.put("phone",phone);
//
////                        String ccbuluo = HttpRequester.get("http://test.zht.com/zlxt/api/getVerificationCode?_="
//
//                        String ccbuluo = null;
//                        try {
//                            ccbuluo = HttpRequester.post("http://www.91vmai.com/auth/sendMessage.shtml",params);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println(phone+":"+ccbuluo);
////                    }
//                }
//            });
//        }
//        for(;;){
//            TaskPools.getTaskPoolsImpl().execute(new Runnable() {
//                @Override
//                public void run() {
//                    String ccbuluo = HttpRequester.get("http://wildestlife.ccbuluo.com/api/member/register/getverificationcode?_="
////                    String ccbuluo = HttpRequester.get("http://test.zht.com/zlxt/api/getVerificationCode?_="
//                            + System.currentTimeMillis()
//                    );
//                    System.out.println(ccbuluo);
////                    String baidu = HttpRequester.get("https://www.baidu.com");
////                    System.out.println(baidu);
//                }
//            });
////            try {
////                Thread.sleep(1000L);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////
////            TaskPools.getTaskPoolsImpl().execute(new Runnable() {
//
////                @Override
////                public void run() {
////                    String baidu = HttpRequester.get("http://192.168.0.48:8102/bill/financebill/querypendingmarketpaymentbills");
////                    System.out.println(baidu);
////                }
////            });
////            try {
////                Thread.sleep(1000L);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
    }


}