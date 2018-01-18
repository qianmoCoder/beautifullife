package com.ddu.util.xml;

import android.util.Xml;

import com.ddu.db.entity.StudyContent;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/5/16.
 */

public class PullParserUtils {

    public static List<StudyContent> getStudyContent(InputStream inputStream) throws Exception {
        List<StudyContent> studyContents = new ArrayList<>();
        StudyContent studyContent = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inputStream, "UTF-8");
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    studyContent = new StudyContent();
                    if ("Tag".equalsIgnoreCase(pullParser.getName())) {
                        String title = pullParser.getAttributeValue(0);
                        String isOld = pullParser.getAttributeValue(1);
                        String description = pullParser.nextText();
                        studyContent.setTitle(title);
                        studyContent.setOld(isOld.equalsIgnoreCase("0"));
                        studyContent.setDescription(description);
                        studyContents.add(studyContent);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            event = pullParser.next();
        }
        return studyContents;
    }
}
