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
package net.sunyijun.os.cmd.impl;

import net.sunyijun.os.cmd.IConsoleLineCallbackForDifferentOS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SunYiJun
 * @since 0.0.1
 */
public class PingCheckSuccessCallback implements IConsoleLineCallbackForDifferentOS {

    private static final Pattern WIN_PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern LINUX_PATTERN = Pattern.compile("(\\d+)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);

    private boolean pingSuccess = true;

    private int pingCount;
    private int successCount;

    public PingCheckSuccessCallback(int pingCount) {
        this.pingCount = pingCount;
    }

    public boolean isPingSuccess() {
        return pingSuccess && successCount == pingCount;
    }

    public void processNormalWindows(String newLine) {
        if (!pingSuccess) {
            return;
        }
        Matcher matcher = WIN_PATTERN.matcher(newLine);
        while (matcher.find()) {
            successCount++;
        }
    }

    public void processNormalLinux(String newLine) {
        if (!pingSuccess) {
            return;
        }
        Matcher matcher = LINUX_PATTERN.matcher(newLine);
        while (matcher.find()) {
            successCount++;
        }
    }

    public void processNormalOthers(String newLine) {
    }

    public void processErrorWindows(String newLine) {
        pingSuccess = false;
    }

    public void processErrorLinux(String newLine) {
        pingSuccess = false;
    }

    public void processErrorOthers(String newLine) {
        pingSuccess = false;
    }
}
