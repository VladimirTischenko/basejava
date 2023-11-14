package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
//        http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
        if (e instanceof PSQLException && "23505".equals(e.getSQLState())) {
            throw new ExistStorageException(null);
        }
        return new StorageException(e);
    }
}
