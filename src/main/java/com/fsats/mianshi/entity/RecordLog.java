package com.fsats.mianshi.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RecordLog {

    private Integer id;
    private Integer userId;
    private String type;
    private String methodName;
    private Date recordDate;
    private String IPAddress;
    private String errorCode;
    private String errorMessage;

    public RecordLog() {
    }

    public RecordLog(Integer userId,String type, String methodName, Date recordDate, String IPAddress) {
        this.userId=userId;
        this.type = type;
        this.methodName = methodName;
        this.recordDate = recordDate;
        this.IPAddress = IPAddress;
    }

    public RecordLog(Integer userId,String type, String methodName, Date recordDate, String IPAddress, String errorCode, String errorMessage) {
        this.userId=userId;
        this.type = type;
        this.methodName = methodName;
        this.recordDate = recordDate;
        this.IPAddress = IPAddress;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "RecordLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", methodName='" + methodName + '\'' +
                ", recordDate=" + recordDate +
                ", IPAddress='" + IPAddress + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
