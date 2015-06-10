// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FastJsonHttpMessageConverter.java

package com.survey.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.*;
import java.nio.charset.Charset;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.*;

@SuppressWarnings("all")
public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter
{

	public static final Charset UTF8 = Charset.forName("UTF-8");
	private Charset charset;
	private SerializerFeature serializerFeature[];

	public FastJsonHttpMessageConverter()
	{
		charset = UTF8;
	}

	protected boolean supports(Class clazz)
	{
		return true;
	}

	protected Object readInternal(Class clazz, HttpInputMessage inputMessage)
		throws IOException, HttpMessageNotReadableException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = inputMessage.getBody();
		byte buf[] = new byte[1024];
		do
		{
			int len = in.read(buf);
			if (len == -1)
				break;
			if (len > 0)
				baos.write(buf, 0, len);
		} while (true);
		byte bytes[] = baos.toByteArray();
		if (charset == UTF8)
			return JSON.parseObject(bytes, clazz, new Feature[0]);
		else
			return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz, new Feature[0]);
	}

	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
		throws IOException, HttpMessageNotWritableException
	{
		OutputStream out = outputMessage.getBody();
		byte bytes[];
		if (charset == UTF8)
		{
			if (serializerFeature != null)
				bytes = JSON.toJSONBytes(obj, serializerFeature);
			else
				bytes = JSON.toJSONBytes(obj, new SerializerFeature[] {
					SerializerFeature.WriteDateUseDateFormat
				});
		} else
		{
			String text;
			if (serializerFeature != null)
				text = JSON.toJSONString(obj, serializerFeature);
			else
				text = JSON.toJSONString(obj, new SerializerFeature[] {
					SerializerFeature.WriteDateUseDateFormat
				});
			bytes = text.getBytes(charset);
		}
		out.write(bytes);
	}

}
