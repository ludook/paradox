/*
 *     Copyright (C) 2017 Hein Smith
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.heinsmith.paradox.commands;

/**
 * Created by Hein Smith on 2017/03/19.
 */
public class InvalidCommandIndexException extends RuntimeException {

    public InvalidCommandIndexException() {
        super();
    }

    public InvalidCommandIndexException(String message) {
        super(message);
    }

    public InvalidCommandIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandIndexException(Throwable cause) {
        super(cause);
    }

    protected InvalidCommandIndexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
