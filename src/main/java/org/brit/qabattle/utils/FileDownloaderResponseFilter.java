package org.brit.qabattle.utils;

import com.codeborne.selenide.Configuration;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The idea was given from here https://github.com/selenide/selenide/issues/196
 *
 * @author sbrit
 */
public class FileDownloaderResponseFilter implements ResponseFilter {

    private Set<String> contentTypes = new HashSet<String>();
    private File tempDir = new File(System.getProperty("user.dir") + "\\" + Configuration.reportsFolder);
    private File tempFile = null;

    public static FileDownloaderResponseFilter withContent(String contentType) {
        return new FileDownloaderResponseFilter().addContentType(contentType);
    }

    public static FileDownloaderResponseFilter withContents(String... contentType) {
        FileDownloaderResponseFilter downloader = new FileDownloaderResponseFilter();
        for (int i = 0; i < contentType.length; i++) {
            downloader.addContentType(contentType[i]);
        }
        return downloader;
    }

    public FileDownloaderResponseFilter addContentType(String contentType) {
        contentTypes.add(contentType);
        return this;
    }

    public File getTempFile() {
        return tempFile;
    }

    @Override
    public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        String contentType = response.headers().get("Content-Type");
        if (contentTypes.contains(contentType)) {
            try {
                String postfix = contentType.substring(contentType.indexOf('/') + 1);
                tempFile = File.createTempFile("downloaded", "." + postfix, tempDir);
                tempFile.deleteOnExit();
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                outputStream.write(contents.getBinaryContents());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
