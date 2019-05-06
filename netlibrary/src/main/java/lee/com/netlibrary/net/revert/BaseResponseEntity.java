package lee.com.netlibrary.net.revert;



import java.io.Serializable;

import lee.com.netlibrary.utils.ApiConfig;

/**
 * @describe：网络请求返回值
 */
public class BaseResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public int code;

    public String msg;

    public boolean success() {
        return ApiConfig.getSucceedCode() == code;
    }

    public int getTokenInvalid() {
        return ApiConfig.getInvalidateToken();
    }

    public T data;

}
