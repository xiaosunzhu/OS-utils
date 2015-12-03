package net.sunyijun.os.cmd;

/**
 * @author sun.yijun
 */
public class ExeResult {

    public boolean success;

    public String consoleMsg;

    public String errorMsg;

    public static ExeResult success() {
        ExeResult result = new ExeResult();
        result.success = true;
        return result;
    }

    public static ExeResult error() {
        ExeResult result = new ExeResult();
        result.success = false;
        return result;
    }

    public static ExeResult errorWithMsg(String errorMsg) {
        ExeResult result = new ExeResult();
        result.success = false;
        result.errorMsg = errorMsg;
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getConsoleMsg() {
        return consoleMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
