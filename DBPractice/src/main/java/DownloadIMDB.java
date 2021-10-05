import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.zip.GZIPInputStream;

public class DownloadIMDB {


    public static void read(String file) throws IOException {

        try {
            String url = "https://datasets.imdbws.com/"+file;
            String file_name = "src/main/resources/"+file;

            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            Files.copy(in, Paths.get(file_name), StandardCopyOption.REPLACE_EXISTING);
            Path source = Paths.get(file_name);
            Path target = Paths.get(file_name.replace(".gz", ""));
            GZIPInputStream gis = new GZIPInputStream(new FileInputStream(source.toFile()));
            Files.copy(gis, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




