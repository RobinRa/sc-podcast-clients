package com.soundcloud.podcast.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for PodcastClient
 */
public class PodcastClientTest extends TestCase {

    public void testParse() {

        List<Item> items = read();
        for (Item item : items) {
          PodcastClient client = PodcastClient.parse(item.getClientAgent());
          String name = (client == null) ? null : client.getName();
          assertEquals("For agent " + item.getClientAgent(),
                       item.getExpectedPodcast(),
                       name);
        }

        assertTrue(true);
    }

    private static List<Item> read() {
        try {
            InputStream in = PodcastClientTest.class.getResourceAsStream("/examples.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                                                   .withCommentMarker('#')
                                                   .parse(reader);

            List<Item> items = new LinkedList<Item>();

            for (CSVRecord record : records) {
                String clientAgent = record.get(0);
                String expectedPodcast = (record.get(1).equals("null")) ? null : record.get(1);
                items.add(new Item(clientAgent, expectedPodcast));
            }

            return Collections.unmodifiableList(items);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read test items", e);
        }
    }

    private static class Item {

        private final String clientAgent;
        private final String expectedPodcast;

        public Item(String clientAgent,
                    String expectedPodcast) {
            this.clientAgent = clientAgent;
            this.expectedPodcast = expectedPodcast;
        }

        public String getClientAgent() {
            return clientAgent;
        }

        public String getExpectedPodcast() {
            return expectedPodcast;
        }
    }
}
