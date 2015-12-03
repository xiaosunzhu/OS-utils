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
package net.sunyijun.os.exception;

import java.io.File;

/**
 * @author SunYiJun
 * @since 0.0.1
 */
public class FileSystemException extends Exception {

    public FileSystemException(String message) {
        super(message);
    }

    public FileSystemException(File errorFile, String message) {
        super(message + " (" + errorFile.getAbsolutePath() + ")");
    }

    public FileSystemException(String errorFile, String message) {
        super(message + " (" + errorFile + ")");
    }

    public FileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemException(Throwable cause) {
        super(cause);
    }

}
