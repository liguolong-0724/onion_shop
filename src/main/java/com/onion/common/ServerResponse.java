package com.onion.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.io.Serializable;

/**
 * 自定义通用的服务响应对象
 */

// 保证序成化成json的时侯，如果是null的对象,key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    // 构造器全部私有化
    private ServerResponse(){}

    private ServerResponse(int status){
        this.status = status;
    }

    // 当T为String类型时会调用下面两个构造器的哪一个呢？
    private ServerResponse(int status,String msg){
        this(status);
        this.msg = msg;
    }

    private ServerResponse(int status,T data){
        this(status);
        this.data = data;
    }

    private ServerResponse(String msg,T data){
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status,String msg,T data){
        this(status,msg);
        this.data = data;
    }

    // 响应是否正确
    @JsonIgnore // 使这个方法不在json序列化的结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return this.status;
    }

    public String getMsg(){
        return this.msg;
    }

    public T getData(){
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    // 通过createBySuccessMessage和createBySuccess解决上面当T为String类型时调用构造混乱的问题
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMsg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMsg){
        return new ServerResponse<T>(errorCode,errorMsg);
    }

}
