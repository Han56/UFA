package com.han56.ufa.utils;

import com.futu.openapi.*;
import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotSub;
import com.futu.openapi.pb.QotUpdateBasicQot;

/**
 * @author han56
 * @description 功能描述:实时报价回调
 * @create 2022/2/11 上午9:50
 */
public class FutuQuetoTest implements FTSPI_Qot, FTSPI_Conn {

    FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();

    public FutuQuetoTest(){
        qot.setClientInfo("javaclient",1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
    }

    public void start(){
        qot.initConnect("127.0.0.1",(short)11111,false);
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        System.out.printf("Qot onInitConnect: ret=%b desc=%s connID=%d\n", errCode, desc, client.getConnectID());
        if (errCode != 0)
            return;

        QotCommon.Security sec = QotCommon.Security.newBuilder()
                .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                .setCode("00700")
                .build();
        QotSub.C2S c2s = QotSub.C2S.newBuilder()
                .addSecurityList(sec)
                .addSubTypeList(QotCommon.SubType.SubType_Basic_VALUE)
                .setIsSubOrUnSub(true)
                .setIsRegOrUnRegPush(true)
                .build();
        QotSub.Request req = QotSub.Request.newBuilder().setC2S(c2s).build();
        int seqNo = qot.sub(req);
        System.out.printf("Send QotSub: %d\n", seqNo);
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        System.out.printf("Qot onDisConnect: %d\n", errCode);
    }

    @Override
    public void onReply_Sub(FTAPI_Conn client, int nSerialNo, QotSub.Response rsp) {
        if (rsp.getRetType() != 0) {
            System.out.printf("QotSub failed: %s\n", rsp.getRetMsg());
        }
        else {
            System.out.println(rsp);
        }
    }

    @Override
    public void onPush_UpdateBasicQuote(FTAPI_Conn client, QotUpdateBasicQot.Response rsp) {
        if (rsp.getRetType() != 0) {
            System.out.printf("QotUpdateBasicQuote failed: %s\n", rsp.getRetMsg());
        }
        else {
            System.out.println(rsp);
        }
    }

    public static void main(String[] args) {
        FTAPI.init();
        FutuQuetoTest qot = new FutuQuetoTest();
        qot.start();

        while (true) {
            try {
                Thread.sleep(1000 * 600);
            } catch (InterruptedException exc) {

            }
        }
    }
}
