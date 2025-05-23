/*
 *    Copyright 2006-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.exception;

import java.io.Serial;

/**
 * This class is used by the ShellCallback methods to denote unrecoverable errors.
 *
 * @author Jeff Butler
 */
public class ShellException extends Exception {

    @Serial
    private static final long serialVersionUID = -2026841561754434544L;

    public ShellException() {
        super();
    }

    public ShellException(String arg0) {
        super(arg0);
    }

    public ShellException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ShellException(Throwable arg0) {
        super(arg0);
    }
}
