import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@WebListener
public class MyContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        File dir = new File("/tmp");
        System.out.println(dir.setReadable(true, true));
        System.out.println(dir.setWritable(true, true));

        String dirName = "/tmp";
        //List all files (for troubleshooting)
        try {
            Files.list(new File(dirName).toPath())
                    .limit(10)
                    .forEach(path -> {
                        System.out.println(path);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Delete all files
        if(dir.exists()) {
            for (File file : dir.listFiles()){
                System.out.println(file);
                if (!file.isDirectory()){
                    file.delete();
                    System.out.println(file);}
            }}
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //I delete all files on server start to avoid errors caused due to
        // unexpected shut down.
        //Iterate over each txt file within the lott_nums dir and delete
        System.out.println(System.getProperty("user.dir"));
        File dir = new File("/tmp");
        if(dir.exists()) {
            for (File file : dir.listFiles()){
                System.out.println(file);
                if (!file.isDirectory()){
                    file.delete();
                    System.out.println(file);}
            }}
    }
    }

