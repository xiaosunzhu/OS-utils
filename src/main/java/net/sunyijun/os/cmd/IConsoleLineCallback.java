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
 * Callback for {@link Executor#execute(Command, Parameters, IConsoleLineCallback)}.<br/>
 * When console print one new normal info line, will call {@link #processNormalMsg(String)}.
 * if console print one new error info line, will call {@link #processErrorMsg(String)}.
 *
 * @author SunYiJun
 * @since 0.0.1
 */
public interface IConsoleLineCallback {

    void processNormalMsg(String newLine);

    void processErrorMsg(String newLine);

}
