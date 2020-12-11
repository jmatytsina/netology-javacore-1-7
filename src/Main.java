import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String path = "E://Games/Save Games/zip.zip";
        String pathRes = "E://Games/Save Games/";
        openZip(path, pathRes);
        System.out.println(openProgress(pathRes + "save1.dat"));
    }

    private static void openZip(String pathSource, String pathResult) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathSource))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = pathResult + entry.getName().substring(7);
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
            return gameProgress;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
