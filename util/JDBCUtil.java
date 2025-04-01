package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil{
    private static String DBIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static volatile JDBCUtil instance = new JDBCUtil();
    private JDBCUtil(){
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(".env", Charset.forName("UTF-8")));
            String str;
            while ((str=br.readLine())!=null) {
                String[] line =str.split("=");
                switch (line[0]) {
                    case "DBIVER":
                    DBIVER=line[1];
                        break;
                    case "URL":
                    URL=line[1];
                        break;
                    case "USER":
                    USER=line[1];
                        break;
                    case "PASSWORD":
                    PASSWORD=line[1];
                        break;
                    default:
                        break;
                }
            }
            Class.forName(DBIVER);
            System.out.println("Driver loading OK");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
               
        }
    }

    public static JDBCUtil getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

    public void close(AutoCloseable... closeables) {
		try {
            for (AutoCloseable c : closeables) {
                if (c != null) {
                    c.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}