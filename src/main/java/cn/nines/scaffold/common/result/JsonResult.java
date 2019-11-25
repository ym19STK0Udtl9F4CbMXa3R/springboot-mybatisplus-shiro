package cn.nines.scaffold.common.result;

import lombok.Data;

/**
 * @ClassName: JsonResult
 * @Description: 封装Json返回信息
 * @author: Nines
 * @date: 2019年11月25日 23:12
 */
@Data
public class JsonResult {

    private boolean success;
    private String status;
    private String msg;
    private Object obj;

    public boolean isSuccess() {
        return success;
    }

    /**
     * 失败
     * @return result
     */
    public static JsonResult error(){
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        result.setStatus("500");
        return result;
    }

    /**
     * 失败 (带消息）
     * @param msg 需要返回的消息
     * @return result
     */
    public static JsonResult error(String msg){
        JsonResult result = error();
        result.setMsg(msg);
        return result;
    }

    /**
     * 成功
     * @return result
     */
    public static JsonResult success(){
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        result.setStatus("200");
        return result;
    }

    /**
     * 成功
     * @param msg 需要返回的消息
     * @return result
     */
    public static JsonResult success(String msg){
        JsonResult result = success();
        result.setMsg(msg);
        return result;
    }

    /**
     * 成功
     * @param obj 需要返回的数据
     * @return result
     */
    public static JsonResult success(Object obj){
        JsonResult result = success();
        result.setObj(obj);
        return result;
    }

}
