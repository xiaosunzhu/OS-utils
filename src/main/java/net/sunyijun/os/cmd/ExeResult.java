/*
 * Copyright 2015 SunYiJun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sunyijun.os.cmd;

/**
 * @author SunYiJun
 * @since 0.0.1
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
