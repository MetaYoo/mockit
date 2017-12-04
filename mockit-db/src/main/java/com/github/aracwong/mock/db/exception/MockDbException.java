package com.github.aracwong.mock.db.exception;

/**
 * @author: aracwong
 * @email: aracwong@163.com
 * @datetime: 2017/10/29 0029 下午 7:37
 * @version: 1.0.0
 */
public class MockDbException extends Exception {
    public MockDbException(String message) {
        super(message);
    }

    public MockDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MockDbException(Throwable cause) {
        super(cause);
    }

    public MockDbException() {
        super();
    }
}
