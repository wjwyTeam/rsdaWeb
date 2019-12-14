/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 16:42:56
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-04 16:44:13
 */
package com.wjwy.rsda.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

	public static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static String serialize(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj.getClass() == String.class) {
			return (String) obj;
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("json序列化出错：" + obj, e);
			return null;
		}
	}

	public static <T> T parse(String json, Class<T> tClass) {
		if (json == null) {
			return null;
		}
		try {
			return mapper.readValue(json, tClass);
		} catch (IOException e) {
			logger.error("json解析出错：" + json, e);
			return null;
		}
	}

	public static <E> List<E> parseList(String json, Class<E> eClass) {
		try {
			return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
		} catch (IOException e) {
			logger.error("json解析出错：" + json, e);
			return null;
		}
	}

	public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
		try {
			return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
		} catch (IOException e) {
			logger.error("json解析出错：" + json, e);
			return null;
		}
	}

	public static <T> T nativeRead(String json, TypeReference<T> type) {
		try {
			return mapper.readValue(json, type);
		} catch (IOException e) {
			logger.error("json解析出错：" + json, e);
			return null;
		}
	}
}
