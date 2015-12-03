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
package net.sunyijun.os;

import net.sunyijun.os.exception.FileSystemException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide some file system operations. All functions are statics.
 *
 * @author SunYiJun
 * @since 0.0.1
 */
public class FileSystemUtils {

    /**
     * Check current using witch os type.
     *
     * @return {@linkplain OS OS type}
     */
    public static OS checkOSType() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("win")) {
            return OS.WINDOWS;
        } else if (osName.toLowerCase().startsWith("lin")) {
            return OS.LINUX;
        } else {
            return OS.OTHERS;
        }
    }

    /**
     * Delete one directory include sub-dirs and sub-files.
     *
     * @return If all things delete success.
     */
    public static boolean deleteDirectory(File directory) {
        if (!directory.exists())
            return true;
        if (directory.isFile()) {
            return directory.delete();
        }
        File[] files = directory.listFiles();
        boolean finalSuccess = true;
        if (files != null) {
            for (File file : files) {
                boolean success = deleteDirectory(file);
                if (!success) {
                    finalSuccess = false;
                }
            }
        }
        boolean success = directory.delete();
        if (!success) {
            finalSuccess = false;
        }
        return finalSuccess;
    }

    /**
     * Make one directory if it not exists.
     * And check if it can write(create sub-dir or sub-file).
     * If not, make it writable.
     *
     * @param directory The directory need to create and check.
     * @throws FileSystemException If create dir or make it writable error.
     */
    public static void makeWritableDir(File directory) throws FileSystemException {
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                throw new FileSystemException(directory, "Create directory failed.");
            }
        }
        OS osType = checkOSType();
        if (osType == OS.LINUX) {
            boolean success = directory.setWritable(true, false);
            if (!success) {
                throw new FileSystemException(directory, "Set directory writable failed.");
            }
        }
    }

    /**
     * Split one file into several parts, and each part size not larger than max size set with param.<br/>
     * Part file name is add a number to origin file name. Number start with 0.<br/>
     * eg. "test.tmp" split to 3 parts, names are : test.tmp.0 , test.tmp.1 , test.tmp.2
     *
     * @param file        The file to split.
     * @param partMaxSize A part max size(byte).
     * @return Split to how many parts.
     * @throws FileSystemException Read or write file error.
     */
    public static int splitFile(File file, int partMaxSize) throws FileSystemException {
        return splitFileToFiles(file, partMaxSize).size();
    }

    /**
     * Split one file into several parts, and each part size not larger than max size set with param.<br/>
     * Part file name is add a number to origin file name. Number start with 0.<br/>
     * eg. "test.tmp" split to 3 parts, names are : test.tmp.0 , test.tmp.1 , test.tmp.2
     *
     * @param file        The file to split.
     * @param partMaxSize A part max size(byte).
     * @return Split file parts list.
     * @throws FileSystemException Read or write file error.
     */
    public static List<File> splitFileToFiles(File file, int partMaxSize) throws FileSystemException {
        List<File> fileParts = new ArrayList<File>();

        String directoryPath = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
        String partFilePrefix = directoryPath + File.separator + fileName;
        int num = 0;
        InputStream inputStream = null;
        byte[] buffer = new byte[partMaxSize];
        try {
            inputStream = new FileInputStream(file);
            int realLen;

            while ((realLen = inputStream.read(buffer, 0, partMaxSize)) != -1) {
                OutputStream outputStream = null;
                try {
                    File filePart = new File(partFilePrefix + "." + num);
                    outputStream = new FileOutputStream(filePart);
                    outputStream.write(buffer, 0, realLen);
                    outputStream.flush();
                    fileParts.add(filePart);
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
                num++;
            }

            return fileParts;
        } catch (IOException e) {
            throw new FileSystemException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static void combineFile(File targetCombineFile, int count) throws FileSystemException {
        String directoryPath = targetCombineFile.getParentFile().getAbsolutePath();
        String fileName = targetCombineFile.getName();
        String partFilePrefix = directoryPath + File.separator + fileName;

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetCombineFile);

            for (int i = 0; i < count; i++) {
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(new File(partFilePrefix + "." + i));
                    byte[] buffer = new byte[inputStream.available()];
                    int length = inputStream.read(buffer);
                    if (length >= 0) {
                        outputStream.write(buffer, 0, length);
                    }
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
                if ((i + 1) % 5 == 0) {
                    outputStream.flush();
                }
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new FileSystemException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
