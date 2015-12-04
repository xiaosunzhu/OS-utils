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

    public class Params extends Parameters {
        public String ip;
        public int sendCount;
        public int timeout;
    }

    public String getWindowsCommand(Params params) {
        return "ping -n " + params.sendCount + " -w " + params.timeout + " " + params.ip;
    }

    public String getLinuxCommand(Params params) {
        return "ping -c " + params.sendCount + " -w " + params.timeout + " " + params.ip;
    }

    public String getOthersCommand(Params params) {
        return "ping";
    }

}
