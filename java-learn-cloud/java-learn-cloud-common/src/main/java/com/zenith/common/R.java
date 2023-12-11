package com.zenith.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class R {
    private Integer code;
    private Object data;
    private String msg;

    public R(Integer code,Object data,String msg){
        this.code=code;
        this.data=data;
        this.msg=msg;
    }

    public static R ok(){
        return new R(200,null,"success");
    }

    public static R ok(Object data){
        return new R(200,data,"success");
    }

    public static R ok(Object data,String msg){
        return new R(200,data,msg);
    }

    public static R error(){
        return new R(500,null,"system error");
    }

    public static R error(String msg){
        return new R(500,null,msg);
    }
}
