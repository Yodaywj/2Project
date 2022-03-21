package domain;

import java.util.HashMap;

public class ResultEntity extends HashMap<String, Object> {

    public static ResultEntity success(String message){
        ResultEntity response = new ResultEntity();
        response.setResult(Boolean.TRUE);
        response.setMessage(message);
        return response;
    }

    public static ResultEntity failure(String message){
        ResultEntity response = new ResultEntity();
        response.setResult(Boolean.FALSE);
        response.setMessage(message);
        return response;
    }

    public void setResult(Boolean success) {
        if (success != null) put("result", success);
    }

    public void setMessage(String message) {
        if (message != null) put("message", message);
    }

    public ResultEntity setOther(String key, Object value) {
        if (key != null && value != null) put(key, value);
        return this;
    }
}
