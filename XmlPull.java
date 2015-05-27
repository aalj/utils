package com.aalj22.aa21.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.aalj22.aa21.NEWS.News;

public class XmlPull {

	public static List<News> getXmlPull(InputStream stream) throws Exception {
		List<News> newsInfoList = null;
		News news = null;
		XmlPullParser parser = Xml.newPullParser();

		parser.setInput(stream, "utf-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if ("news".equals(name)) {
					newsInfoList = new ArrayList<News>();

				} else if ("new".equals(name)) {
					news = new News();
				} else if ("title".equals(name)) {
					news.setTitle(parser.nextText());
				} else if ("detail".equals(name)) {
					news.setDetail(parser.nextText());

				} else if ("comment".equals(name)) {
					news.setComment(parser.nextText());
				} else if ("image".equals(name)) {
					news.setImage(parser.nextText());

				}

				break;
			case XmlPullParser.END_TAG:
				if ("new".equals(name)) {
					newsInfoList.add(news);

				}
				break;

			default:
				break;
			}
			eventType = parser.next();
		}
		return newsInfoList;

	}

}
