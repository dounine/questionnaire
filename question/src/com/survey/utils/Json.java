package com.survey.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json格式工具
 */
public final class Json {
	/**
	 * 把Java对象转换为Json格式
	 */
	public static String toString(Object obj) {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		String jsonString = EMPTY_JSON_STRING;
		try {
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(byteOutput, encoding);
			objectMapper.writeValue(jsonGenerator, obj);
			jsonString = byteOutput.toString(encoding.getJavaName());
		} catch (IOException e) {
			LOG.error("Json格式转换出错", e);
		} finally {
			IOUtils.closeQuietly(byteOutput);
		}
		return jsonString;
	}

	private static final String EMPTY_JSON_STRING = "{}";
	private static final JsonEncoding encoding = JsonEncoding.UTF8;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger LOG = LoggerFactory.getLogger(Json.class);
}