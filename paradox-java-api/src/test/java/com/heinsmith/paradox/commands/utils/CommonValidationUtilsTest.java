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

package com.heinsmith.paradox.commands.utils;

import com.heinsmith.paradox.CommonValidationUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by Hein Smith on 2017/10/13.
 */
public class CommonValidationUtilsTest {

    @Test
    void testAreaRanges() {

        boolean result;
        for (int i = 0; i > Integer.MIN_VALUE; i--) {
            result = CommonValidationUtils.invalidAreaNumber(i);
            assertTrue(result);
        }

        for (int i = 1; i < 8; i++) {
            result = CommonValidationUtils.invalidAreaNumber(i);
            assertFalse(result);
        }

        for (int i = 9; i < Integer.MAX_VALUE; i++) {
            result = CommonValidationUtils.invalidAreaNumber(i);
            assertTrue(result);
        }
    }

    @Test
    void testInvalidPanelCode() {

        boolean result;

        result = CommonValidationUtils.invalidPanelCode(null);
        assertTrue(result);

        result = CommonValidationUtils.invalidPanelCode("12A4".toCharArray());
        assertTrue(result);

        for (int i = 3; i > 0; i--) {
            char[] code = new char[i];
            Arrays.fill(code, '3');
            result = CommonValidationUtils.invalidPanelCode(code);
            assertTrue(result);
        }

        for (int i = 4; i < 6; i++) {
            char[] code = new char[i];
            Arrays.fill(code, '1');
            result = CommonValidationUtils.invalidPanelCode(code);
            assertFalse(result);
        }

        for (int i = 7; i < 100; i++) {
            char[] code = new char[i];
            Arrays.fill(code, '2');
            result = CommonValidationUtils.invalidPanelCode(code);
            assertTrue(result);
        }
    }

    @Test
    void testInvalidInputNumber() {
        boolean result;
        for (int i = 0; i > Integer.MIN_VALUE; i--) {
            result = CommonValidationUtils.invalidInputNumber(i);
            assertTrue(result);
        }

        for (int i = 1; i < 16; i++) {
            result = CommonValidationUtils.invalidInputNumber(i);
            assertFalse(result);
        }

        for (int i = 17; i < Integer.MAX_VALUE; i++) {
            result = CommonValidationUtils.invalidInputNumber(i);
            assertTrue(result);
        }
    }
}
