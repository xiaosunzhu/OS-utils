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
public class PingSimple implements Command<PingSimple.Params> {

    public static class Params implements Parameters {
        public Params(String ip) {
            this.ip = ip;
        }

        public Params(String ip, Integer sendCount) {
            this.ip = ip;
            this.sendCount = sendCount;
        }

        public Params(String ip, Integer sendCount, Integer timeout) {
            this.ip = ip;
            this.sendCount = sendCount;
            this.timeout = timeout;
        }

        public String ip;
        public Integer sendCount;
        public Integer timeout;
    }

    public String getWindowsCommand(Params params) {
        StringBuilder builder = new StringBuilder("ping");
        if (params.sendCount != null) {
            builder.append(" -n ").append(params.sendCount);
        }
        if (params.timeout != null) {
            builder.append(" -w ").append(params.timeout);
        }
        builder.append(" ").append(params.ip);
        return builder.toString();
    }

    public String getLinuxCommand(Params params) {
        StringBuilder builder = new StringBuilder("ping");
        if (params.sendCount != null) {
            builder.append(" -c ").append(params.sendCount);
        }
        if (params.timeout != null) {
            builder.append(" -w ").append(params.timeout);
        }
        builder.append(" ").append(params.ip);
        return builder.toString();
    }

    public String getOthersCommand(Params params) {
        return "ping";
    }

}
