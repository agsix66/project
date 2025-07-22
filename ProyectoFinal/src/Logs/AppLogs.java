package Logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLogs {
    private Logger logger;

    public AppLogs(Class<?> nombreClase) {
        this.logger = LogManager.getLogger(nombreClase);
    }

    public void infoLogs(String mensaje){
        logger.info(mensaje);
    }

    public void errorLogs(Exception e){
        logger.error(e);
    }
}
