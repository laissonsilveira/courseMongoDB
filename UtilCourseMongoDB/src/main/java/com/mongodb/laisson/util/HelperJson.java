package com.mongodb.laisson.util;

import java.io.StringWriter;

import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

public class HelperJson {

    public static void printJson(Document document, boolean indent) {
	JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, indent));
	new DocumentCodec().encode(jsonWriter, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
	System.out.println(jsonWriter.getWriter());
	System.out.flush();
    }

}
