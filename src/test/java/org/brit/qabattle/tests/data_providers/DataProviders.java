package org.brit.qabattle.tests.data_providers;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author sbrit
 */
public class DataProviders {
    @DataProvider(name = "dataProviderAdvertisers")
    public static Object[] dataProviderAdvertisers() {
        return readFile("Advertiser.txt").toArray(new String[0]);
    }

    @DataProvider(name = "dataProviderPublishers")
    public static Object[] dataProviderPublishers() {
        return readFile("Publishers.txt").toArray(new String[0]);
    }

    @DataProvider(name = "dataProviderForTopLevelClients")
    public static Object[] dataProviderForTopLevelClients() {
        return readFile("TopLevelClients.txt").toArray(new String[0]);
    }

    private static List<String> readFile(String fileName) {
        try {
            return FileUtils.readLines(new File(DataProviders.class.getClassLoader().getResource(fileName).getFile()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
