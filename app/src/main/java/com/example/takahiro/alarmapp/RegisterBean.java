package com.example.takahiro.alarmapp;

import java.io.Serializable;

/**
 * Created by Takahiro on 2016/02/28.
 */
public class RegisterBean implements Serializable {
    // 予約番号
    private int RegisterId = -1;
    // 予約日
    private String RegisterDate = "";
    // アラーム実行日
    private String ExecuteDate = "";
    // ListView用データ
    private String RegisterInfo = "";

    public int getRegisterId() {
        return RegisterId;
    }
    public void setRegisterId(int registerId) {
        RegisterId = registerId;
    }
    public String getRegisterDate() {
        return RegisterDate;
    }
    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }
    public String getExecuteDate() {
        return ExecuteDate;
    }
    public void setExecuteDate(String executeDate) {
        ExecuteDate = executeDate;
    }
    public String getRegisterInfo() {
        return RegisterInfo;
    }
    public void setRegisterInfo(String registerInfo) {
        RegisterInfo = registerInfo;
    }
}
