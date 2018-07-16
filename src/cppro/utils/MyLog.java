package cppro.utils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLog {
    public static final Logger logger = Logger.getLogger(MyLog.class.getName());
    private FileHandler fileHandler;

    public MyLog() {
        addFileHandler(logger);
    }
    
    private void addFileHandler(Logger logger) {
        try {
            // save file
            fileHandler = new FileHandler(MyLog.class.getName() + ".log");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        logger.addHandler(fileHandler);
    }
    public static void info(String s){
        BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("MyLog.log",true));
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        out.write( sdf.format(cal.getTime())+":::" );

	        out.write(s); out.newLine();
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
