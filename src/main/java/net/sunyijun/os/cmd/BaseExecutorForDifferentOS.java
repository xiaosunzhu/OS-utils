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

import java.io.IOException;

/**
 * @author SunYiJun
 * @since 0.0.1
 */
public class BaseExecutorForDifferentOS extends Executor {

    @Override
    protected <P extends Parameters> Process executeWindows(Command<P> command, P params) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        return runtime.exec(command.getWindowsCommand(params));
    }

    @Override
    protected <P extends Parameters> Process executeLinux(Command<P> command, P params) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        return runtime.exec(new String[]{"sh", "-c", command.getLinuxCommand(params)});
    }

    @Override
    protected <P extends Parameters> Process executeOthers(Command<P> command, P params) {
        throw new UnsupportedOperationException(
                "Command : " + command.getOthersCommand(params) + " , not support other OS yet!");
    }

}
