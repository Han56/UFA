package com.han56.ufa.utils;

import com.futu.openapi.*;
import com.futu.openapi.pb.*;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/2/9 下午4:25
 */
public class FutuStart {

    public static void main(String[] args) throws InterruptedException {
        //初始化环境
        FTAPI.init();
        //创建行情对象
        FTAPI_Conn_Qot client = new FTAPI_Conn_Qot();
        client.setConnSpi(new SampleQotCallback());
        client.setQotSpi(new SampleQotCallback());
        client.setClientInfo("FTAPI4J_Sample", 1); //建立标识
        client.initConnect("127.0.0.1", (short)11111, false); //开始连接
        Thread.sleep(1000 * 600);
    }

    static class SampleQotCallback implements FTSPI_Qot, FTSPI_Conn
    {
        @Override
        public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
            System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
            //简单演示一下获取用户行情基本信息
            FTAPI_Conn_Qot qot = (FTAPI_Conn_Qot)client;
            {
                GetGlobalState.Request req = GetGlobalState.Request.newBuilder().setC2S(
                        GetGlobalState.C2S.newBuilder().setUserID(15891586)
                ).build();
                int seqNo = qot.getGlobalState(req);
                System.out.printf("Send GetGlobalState: %d\n", seqNo);
            }
        }
        @Override
        public void onDisconnect(FTAPI_Conn client, long errCode) {
            System.out.printf("Qot onDisConnect: %d\n", errCode);
        }

        @Override
        public void onReply_GetGlobalState(FTAPI_Conn client, int nSerialNo, GetGlobalState.Response rsp) {
            System.out.printf("Reply: GetGlobalState: %d  %s\n", nSerialNo, rsp.toString());
        }
    }

}
