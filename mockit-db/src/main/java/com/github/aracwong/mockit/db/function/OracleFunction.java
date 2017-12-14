/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.aracwong.mockit.db.function;

import com.github.aracwong.mockit.db.annotation.Function;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author : zpwang
 * @version : 1.0.0
 * @date : 2017/12/13
 */
public class OracleFunction {

    /**
     * Oracle `to_date` function
     *
     * @param source
     * @param format
     * @return
     * @throws ParseException
     */
    @Function(name = "to_date")
    public static java.sql.Date toDate(String source, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        java.util.Date date = sdf.parse(source);
        return new java.sql.Date(date.getTime());
    }


}
