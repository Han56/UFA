package com.han56.ufa.utils;

import com.futu.openapi.*;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotSub;
import com.futu.openapi.pb.QotUpdateOrderBook;
import org.junit.Test;

/**
 * @author han56
 * @description 功能描述:测试订阅行情
 * @create 2022/2/9 下午7:51
 */
public class FutuWebSocket {

    @Test
    public void webScoketTest() throws InterruptedException {
        FTAPI.init();
        SampleQotCallback_ qot = new SampleQotCallback_();
        while (true){
            Thread.sleep(1000*6000);
        }
    }

    static class SampleQotCallback_ implements FTSPI_Qot, FTSPI_Conn{
        FTAPI_Conn_Qot client = new FTAPI_Conn_Qot();

        public SampleQotCallback_(){
            client.setConnSpi(this);
            client.setQotSpi(this);
            client.setClientInfo("FTAPI4J_Sample",1);
            client.initConnect("127.0.0.1",(short)11111,false);
        }

        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
            this.sub();
        }

        @Override
        public void onPush_UpdateOrderBook(FTAPI_Conn client, QotUpdateOrderBook.Response rsp) {
            System.out.println("onPush_UpdateOrderBook:"+rsp.toString());
        }


        void sub(){
            QotCommon.Security sec = QotCommon.Security.newBuilder().setCode("00700")
                    .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                    .build();
            QotSub.C2S c2S = QotSub.C2S.newBuilder().addSecurityList(sec)
                    .addSubTypeList(QotCommon.SubType.SubType_OrderBook_VALUE)
                    .setIsSubOrUnSub(true)
                    .setIsRegOrUnRegPush(true)
                    .setIsFirstPush(true)
                    .build();
            QotSub.Request request = QotSub.Request.newBuilder().setC2S(c2S).build();
            client.sub(request);
        }
    }

}
