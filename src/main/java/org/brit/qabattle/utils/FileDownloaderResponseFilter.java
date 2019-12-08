package org.brit.qabattle.utils;

import com.codeborne.selenide.Configuration;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
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
    private File tempDir = new File(System.getProperty("user.dir") + "\\" + Configuration.reportsFolder + "\\" + RandomStringUtils.randomAlphabetic(6));
    private File tempFile = null;

    public FileDownloaderResponseFilter() {
        tempDir.mkdir();
        tempDir.deleteOnExit();
    }

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
            String uri = messageInfo.getOriginalRequest().getUri();
            String fileName = uri.substring(uri.lastIndexOf('/'));
            tempFile = new File(tempDir, fileName);
            tempFile.deleteOnExit();
            try {
                FileUtils.write(tempFile, contents.getTextContents(), contents.getCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
