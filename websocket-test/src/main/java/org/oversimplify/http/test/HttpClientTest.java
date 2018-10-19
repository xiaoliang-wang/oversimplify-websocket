package org.oversimplify.http.test;

import org.oversimplify.httpclient.HttpRequester;
import org.oversimplify.threadpool.TaskPools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {

    public static void main(String[] args) {


        for (int i = 0; i<12;i++){

            TaskPools.getTaskPoolsImpl().execute(new Runnable() {
                @Override
                public void run() {
//                    for (;;){
                        Map<String,Object> params = new HashMap<>(1);
                        String phone = 17001+""+((int)((Math.random()*9+1)*100000))+"";
                        params.put("phone",phone);

//                        String ccbuluo = HttpRequester.get("http://test.zht.com/zlxt/api/getVerificationCode?_="

                        String ccbuluo = null;
                        try {
                            ccbuluo = HttpRequester.post("http://www.91vmai.com/auth/sendMessage.shtml",params);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(phone+":"+ccbuluo);
//                    }
                }
            });
        }
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