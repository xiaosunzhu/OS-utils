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

    private class ConsoleMsgCollector implements IConsoleLineCallback {

        private StringBuilder normalStrBuilder = new StringBuilder();
        private StringBuilder errStrBuilder = new StringBuilder();

        public void processNormalMsg(String newLine) {
            normalStrBuilder.append(newLine).append(OS.LINE_SEPARATOR);
        }

        public void processErrorMsg(String newLine) {
            errStrBuilder.append(newLine).append(OS.LINE_SEPARATOR);
        }

        StringBuilder getNormalStrBuilder() {
            if (normalStrBuilder.length() > OS.LINE_SEPARATOR.length()) {
                normalStrBuilder.delete(normalStrBuilder.lastIndexOf(OS.LINE_SEPARATOR), normalStrBuilder.length());
            }
            return normalStrBuilder;
        }

        StringBuilder getErrStrBuilder() {
            if (errStrBuilder.length() > OS.LINE_SEPARATOR.length()) {
                errStrBuilder.delete(errStrBuilder.lastIndexOf(OS.LINE_SEPARATOR), errStrBuilder.length());
            }
            return errStrBuilder;
        }

        boolean haveErrStr() {
            return errStrBuilder.length() > 0;
        }

    }

    public <P extends Parameters> ExeResult execute(Command<P> command, P params) throws IOException {
        ConsoleMsgCollector consoleMsgCollector = new ConsoleMsgCollector();
        execute(command, params, consoleMsgCollector);

        if (consoleMsgCollector.haveErrStr()) {
            return ExeResult
                    .errorWithMsg(consoleMsgCollector.getNormalStrBuilder(), consoleMsgCollector.getErrStrBuilder());
        } else {
            return ExeResult.success(consoleMsgCollector.getNormalStrBuilder());
        }
    }

    public <P extends Parameters> void execute(Command<P> command, P params, IConsoleLineCallback callback)
            throws IOException {
        OS osType = FileSystemUtils.checkOSType();
        Process process = execute(command, params, osType);

        BufferedReader in = null;
        BufferedReader err = null;
        try {
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            err = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                callback.processNormalMsg(line);
            }
            String errLine;
            while ((errLine = err.readLine()) != null) {
                callback.processErrorMsg(errLine);
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
    }

    public <P extends Parameters> void execute(Command<P> command, P params,
                                               IConsoleLineCallbackForDifferentOS callback) throws IOException {
        OS osType = FileSystemUtils.checkOSType();
        Process process = execute(command, params, osType);

        BufferedReader in = null;
        BufferedReader err = null;
        try {
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            err = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                switch (osType) {
                    case WINDOWS:
                        callback.processNormalWindows(line);
                        break;
                    case LINUX:
                        callback.processNormalLinux(line);
                        break;
                    case OTHERS:
                        callback.processNormalOthers(line);
                        break;
                }
            }
            String errLine;
            while ((errLine = err.readLine()) != null) {
                switch (osType) {
                    case WINDOWS:
                        callback.processErrorWindows(errLine);
                        break;
                    case LINUX:
                        callback.processErrorLinux(errLine);
                        break;
                    case OTHERS:
                        callback.processErrorOthers(errLine);
                        break;
                }
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
    }

    private <P extends Parameters> Process execute(Command<P> command, P params, OS osType) throws IOException {
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
        return process;
    }

    protected abstract <P extends Parameters> Process executeWindows(Command<P> command, P params) throws IOException;

    protected abstract <P extends Parameters> Process executeLinux(Command<P> command, P params) throws IOException;

    protected abstract <P extends Parameters> Process executeOthers(Command<P> command, P params) throws IOException;

}
