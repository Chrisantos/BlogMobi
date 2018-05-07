package com.example.chrisantuseze.blogmobi.Library;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by CHRISANTUS EZE on 3/24/2018.
 */

final class RSSResponseBodyConverter<T> implements Converter<ResponseBody, RSSFeed> {
    RSSResponseBodyConverter() {
    }

    @Override
    public RSSFeed convert(ResponseBody value) {
        RSSFeed rssFeed = new RSSFeed();
        try {
            XMLParser parser = new XMLParser();
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(parser);
            InputSource inputSource = new InputSource(value.charStream());
            xmlReader.parse(inputSource);
            ArrayList<RSSItem> items = parser.getItems();
            rssFeed.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rssFeed;
    }

}
