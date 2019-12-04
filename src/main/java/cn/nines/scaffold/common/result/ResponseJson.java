package cn.nines.scaffold.common.result;

import cn.nines.scaffold.config.exception.ExceptionEnum;
import lombok.Data;

/**
 * @ClassName: ResponseJson
 * @Description: 封装Json返回信息
 * @author: Nines
 * @date: 2019年11月25日 23:12
 */
@Data
public class ResponseJson {

    /**
     * http 状态码
     */
    private int status;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public ResponseJson() {
    }

    private ResponseJson(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseJson error(String msg){
        return new ResponseJson(500, msg, null);
    }

    public static ResponseJson error(int code, String msg){
        return new ResponseJson(code, msg, null);
    }

    public static ResponseJson error(ExceptionEnum exce){
        return new ResponseJson(exce.getExceptionCode(), exce.getExceptionMsg(), null);
    }

    public static ResponseJson success(String msg){
        return new ResponseJson(200, msg, null);
    }

    public static ResponseJson success(Object data){
        return new ResponseJson(200, null, data);
    }

    public static ResponseJson success(String msg, Object data){
        return new ResponseJson(200, msg, data);
    }

}
