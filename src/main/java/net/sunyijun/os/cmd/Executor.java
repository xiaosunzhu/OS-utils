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

import net.sunyijun.os.FileSystemUtils;
import net.sunyijun.os.OS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author SunYiJun
 * @since 0.0.1
 */
public abstract class Executor {

    public <P extends Parameters> ExeResult execute(Command<P> command, P params) throws IOException {
        OS osType = FileSystemUtils.checkOSType();

        Process process;
        switch (osType) {
            case WINDOWS:
                process = executeWindows(command, params);
                break;
            case LINUX:
                process = executeLinux(command, params);
                break;
            case OTHERS:
                process = executeOthers(command, params);
                break;
            default:
                throw new UnsupportedOperationException("Not support " + osType + " OS yet!");
        }

        BufferedReader in = null;
        BufferedReader err = null;
        try {
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            err = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = err.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException ignored) {
                }
            }
        }
        return ExeResult.success();
    }

    protected abstract <P extends Parameters> Process executeWindows(Command<P> command, P params) throws IOException;

    protected abstract <P extends Parameters> Process executeLinux(Command<P> command, P params) throws IOException;

    protected abstract <P extends Parameters> Process executeOthers(Command<P> command, P params) throws IOException;

}
