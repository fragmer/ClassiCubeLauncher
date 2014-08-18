package net.classicube.launcher;

import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import net.classicube.launcher.gui.ErrorScreen;
import net.classicube.shared.SharedUpdaterCode;
import org.apache.commons.lang3.StringUtils;

public class DiagnosticInfoUploader {

    public static final String GIST_API_URL = "https://api.github.com/gists";

    public static String uploadToGist() {
        // gather files for uploading
        final String sysData = getSystemProperties();
        final String dirData = gatherDirStructure();
        final String clientLogData = readLogFile(PathUtil.getClientDir(), PathUtil.CLIENT_LOG_FILE_NAME);
        final String clientOldLogData = readLogFile(PathUtil.getClientDir(), PathUtil.CLIENT_LOG_OLD_FILE_NAME);
        final String selfUpdaterLogData = readLogFile(PathUtil.getClientDir(), PathUtil.SELF_UPDATER_LOG_FILE_NAME);
        final String optionsData = readLogFile(PathUtil.getClientDir(), PathUtil.OPTIONS_FILE_NAME);
        String launcherLogData = null,
                launcherOldLogData = null,
                crashLogData = null;
        try {
            launcherLogData = readLogFile(SharedUpdaterCode.getLauncherDir(), PathUtil.LOG_FILE_NAME);
            launcherOldLogData = readLogFile(SharedUpdaterCode.getLauncherDir(), PathUtil.LOG_OLD_FILE_NAME);
        } catch (final IOException ex) {
            // Theoretically this should never happen.
            LogUtil.getLogger().log(Level.SEVERE, "Could not find launcher directory!", ex);
        }

        final String crashLogName = findLastCrashLogFile(PathUtil.getClientDir());
        if (crashLogName != null) {
            crashLogData = readLogFile(PathUtil.getClientDir(), crashLogName);
        }

        // construct a Gist API request (JSON)
        JsonStringWriter writer = JsonWriter.string()
                .object()
                .value("description", "ClassiCube debug information")
                .value("public", false)
                .object("files");

        // append system information
        if (sysData != null && !sysData.isEmpty()) {
            writer = writer.object("_system")
                    .value("content", sysData)
                    .end();
        }

        // append directory information
        if (dirData != null && !dirData.isEmpty()) {
            writer = writer.object("_dir")
                    .value("content", dirData)
                    .end();
        }

        // append log files
        if (clientLogData != null && !clientLogData.isEmpty()) {
            writer = writer.object(PathUtil.CLIENT_LOG_FILE_NAME)
                    .value("content", clientLogData)
                    .end();
        }
        if (clientOldLogData != null && !clientOldLogData.isEmpty()) {
            writer = writer.object(PathUtil.CLIENT_LOG_OLD_FILE_NAME)
                    .value("content", clientOldLogData)
                    .end();
        }
        if (launcherLogData != null && !launcherLogData.isEmpty()) {
            writer = writer.object(PathUtil.LOG_FILE_NAME)
                    .value("content", launcherLogData)
                    .end();
        }
        if (launcherOldLogData != null && !launcherOldLogData.isEmpty()) {
            writer = writer.object(PathUtil.LOG_OLD_FILE_NAME)
                    .value("content", launcherOldLogData)
                    .end();
        }
        if (selfUpdaterLogData != null && !selfUpdaterLogData.isEmpty()) {
            writer = writer.object(PathUtil.SELF_UPDATER_LOG_FILE_NAME)
                    .value("content", selfUpdaterLogData)
                    .end();
        }
        if (optionsData != null && !optionsData.isEmpty()) {
            writer = writer.object(PathUtil.OPTIONS_FILE_NAME)
                    .value("content", optionsData)
                    .end();
        }
        if (crashLogData != null && !crashLogData.isEmpty()) {
            writer = writer.object(crashLogName)
                    .value("content", crashLogData)
                    .end();
        }

        // finalize JSON
        final String json = writer.end().end().done();
        
        // DEBUG: Log request string
        LogUtil.getLogger().log(Level.INFO, json);

        // post data to Gist
        final String gistResponse = HttpUtil.uploadString(GIST_API_URL, json, HttpUtil.JSON);

        // get URL of newly-created Gist
        try {
            return JsonParser.object().from(gistResponse).getString("html_url");
        } catch (final JsonParserException ex) {
            ErrorScreen.show("Error uploading debug information",
                    "Debug information was gathered, but could not be uploaded.",
                    ex);
            LogUtil.getLogger().log(Level.SEVERE, "Error parsing Gist response", ex);
            return null;
        }
    }

    // Prints all system properties to string, one per line
    // Based on HashTable.toString(), but different formatting.
    private static String getSystemProperties() {
        final Properties props = System.getProperties();
        final int max = props.size();
        final StringBuilder sb = new StringBuilder();
        final Iterator<Map.Entry<Object, Object>> it = props.entrySet().iterator();

        for (int i = 0; i < max; i++) {
            final Map.Entry<Object, Object> e = it.next();
            final Object key = e.getKey();
            final Object value = e.getValue();
            sb.append(key == props ? "(this)" : key.toString());
            sb.append('=');
            sb.append(value == props ? "(this)" : value.toString());
            sb.append('\n');
        }

        return sb.toString();
    }

    // List files in client's and launcher's directories
    private static String gatherDirStructure() {
        try {
            final StringBuilder sb = new StringBuilder();
            final String absClientDir = PathUtil.getClientDir().getAbsolutePath();
            sb.append("Client directory structure:\n");
            walkDir(Paths.get(absClientDir), sb);

            final String absLauncherDir = SharedUpdaterCode.getLauncherDir().getAbsolutePath();
            sb.append("\nLauncher directory structure:\n");
            walkDir(Paths.get(absLauncherDir), sb);

            return sb.toString();
        } catch (final IOException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "Error gathering directory structure.", ex);
            return null;
        }
    }

    // List all files in client's directory, except screenshots and server logs
    private static void walkDir(final Path basePath, final StringBuilder sb) throws IOException {
        Files.walkFileTree(basePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                final String relativePathName = basePath.relativize(file).toString();
                if (!relativePathName.startsWith("Screenshots")
                        && !relativePathName.startsWith("logs")) {
                    sb.append(String.format("%1$7s  %2$s\n",
                            file.toFile().length(),
                            relativePathName));
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    // Reads contents of given file into a string, if the file exists. Returns null otherwise.
    private static String readLogFile(final File dir, final String fileName) {
        final Path path = Paths.get(dir.getAbsolutePath(), fileName);
        if (path.toFile().exists()) {
            try {
                final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                return StringUtils.join(lines, '\n');
            } catch (final IOException ex) {
                LogUtil.getLogger().log(Level.SEVERE, "Could not read " + fileName, ex);
            }
        }
        return null;
    }

    private static String findLastCrashLogFile(final File dir) {
        // your directory
        File[] matchingFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("hs_err_pid");
            }
        });
        if (matchingFiles.length == 0) {
            // No JVM crash logs found
            return null;
        } else if (matchingFiles.length > 1) {
            // Multiple JVM crash logs found -- find the most recent one
            Arrays.sort(matchingFiles, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    // Sort by date-modified in descending order
                    return Long.compare(f2.lastModified(), f1.lastModified());
                }
            });
        }
        return matchingFiles[0].getName();
    }

    private DiagnosticInfoUploader() {
    }
}
