package services;

import javax.inject.*;
import com.google.inject.ImplementedBy;

@ImplementedBy(AppLogger.class)
public interface BaseLogger {
    void log(String message);
}