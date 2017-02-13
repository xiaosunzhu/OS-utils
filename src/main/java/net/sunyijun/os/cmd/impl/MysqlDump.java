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

import net.sunyijun.os.cmd.Command;
import net.sunyijun.os.cmd.Parameters;

import java.util.List;

/**
 * @author SunYiJun
 * @since 0.0.1
 */
public class MysqlDump implements Command<MysqlDump.Params> {

    public static class Params implements Parameters {
        public Params(String ip, String port, String user, String password) {
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.password = password;
        }

        public Params(String ip, String port, String user, String password, List<String> databases) {
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.password = password;
            this.databases = databases;
        }

        public Params(String ip, String port, String user, String password, List<String> databases,
                      List<String> tables) {
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.password = password;
            this.databases = databases;
            this.tables = tables;
        }

        public String ip;
        public String port;
        public String user;
        public String password;
        public List<String> databases;
        public List<String> tables;
        public String charset;
        public boolean dumpData;
        public boolean dumpTrigger;
        public boolean dumpEvent;
    }

    public String getWindowsCommand(Params params) {
        return getCommand(params);
    }

    public String getLinuxCommand(Params params) {
        return getCommand(params);
    }

    private String getCommand(Params params){
        StringBuilder builder = new StringBuilder("mysqldump");
        return builder.toString();
    }

    public String getOthersCommand(Params params) {
        return "ping";
    }

}
