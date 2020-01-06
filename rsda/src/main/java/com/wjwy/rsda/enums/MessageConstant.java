/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 18:03:09
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-04 10:37:25
 */
package com.wjwy.rsda.enums;

import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import com.wjwy.rsda.common.util.StringUtils;

public class MessageConstant {
	// Response返回错误信息
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_NOT_FOUND = "找不到记录,可能已被删除";
	public static final String MSG_INVALID_DATA = "数据不完整或格式错误";
	public static final String MSG_FAIL = "操作不成功,可能是网络故障,请稍后重试";
	public static final String MSG_ERROR = "网络故障,请稍后重试";
	public static final String MSG_DENY_MODIFY = "当前数据不可操作";
	public static final String MSG_DENY_DUPLICATE = "已经存在唯一数据";
	/**
	 * UTF-8 字符集
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * 通用成功标识
	 */
	public static final String SUCCESS = "0";

	/**
	 * 通用失败标识
	 */
	public static final String FAIL = "1";

	/**
	 * 登录成功
	 */
	public static final String LOGIN_SUCCESS = "Success";

	/**
	 * 注销
	 */
	public static final String LOGOUT = "Logout";

	/**
	 * 登录失败
	 */
	public static final String LOGIN_FAIL = "Error";

	/**
	 * 当前记录起始索引
	 */
	public static final String PAGE_NUM = "pageNum";

	/**
	 * 每页显示记录数
	 */
	public static final String PAGE_SIZE = "pageSize";

	/**
	 * 排序列
	 */
	public static final String ORDER_BY_COLUMN = "orderByColumn";

	/**
	 * 排序的方向 "desc" 或者 "asc".
	 */
	public static final String IS_ASC = "isAsc";

	/**
	 * 资源映射路径 前缀
	 */
	public static final String RESOURCE_PREFIX = "/profile";

	public static final String EMPTY_JSON = "{}";
	public static final char C_BACKSLASH = '\\';
	public static final char C_DELIM_START = '{';
	public static final char C_DELIM_END = '}';

	/**
	 * 格式化字符串<br>
	 * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
	 * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 * 
	 * @param strPattern 字符串模板
	 * @param argArray   参数列表
	 * @return 结果
	 */
	public static String format(final String strPattern, final Object... argArray) {
		if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray)) {
			return strPattern;
		}
		final int strPatternLength = strPattern.length();

		// 初始化定义好的长度以获得更好的性能
		StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

		int handledPosition = 0;
		int delimIndex;// 占位符所在位置
		for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
			delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
			if (delimIndex == -1) {
				if (handledPosition == 0) {
					return strPattern;
				} else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
					sbuf.append(strPattern, handledPosition, strPatternLength);
					return sbuf.toString();
				}
			} else {
				if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {
					if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH) {
						// 转义符之前还有一个转义符，占位符依旧有效
						sbuf.append(strPattern, handledPosition, delimIndex - 1);
						sbuf.append(Convert.utf8Str(argArray[argIndex]));
						handledPosition = delimIndex + 2;
					} else {
						// 占位符被转义
						argIndex--;
						sbuf.append(strPattern, handledPosition, delimIndex - 1);
						sbuf.append(C_DELIM_START);
						handledPosition = delimIndex + 1;
					}
				} else {
					// 正常占位符
					sbuf.append(strPattern, handledPosition, delimIndex);
					sbuf.append(Convert.utf8Str(argArray[argIndex]));
					handledPosition = delimIndex + 2;
				}
			}
		}
		// 加入最后一个占位符后所有的字符
		sbuf.append(strPattern, handledPosition, strPattern.length());

		return sbuf.toString();
	}

	/** ISO-8859-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";
	/** UTF-8 */
	public static final String UTF_8 = "UTF-8";
	/** GBK */
	public static final String GBK = "GBK";

	/** ISO-8859-1 */
	public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
	/** UTF-8 */
	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
	/** GBK */
	public static final Charset CHARSET_GBK = Charset.forName(GBK);

	/**
	 * 转换为Charset对象
	 * 
	 * @param charset 字符集，为空则返回默认字符集
	 * @return Charset
	 */
	public static Charset charset(String charset) {
		return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
	}

	/**
	 * 转换字符串的字符集编码
	 * 
	 * @param source      字符串
	 * @param srcCharset  源字符集，默认ISO-8859-1
	 * @param destCharset 目标字符集，默认UTF-8
	 * @return 转换后的字符集
	 */
	public static String convert(String source, String srcCharset, String destCharset) {
		return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
	}

	/**
	 * 转换字符串的字符集编码
	 * 
	 * @param source      字符串
	 * @param srcCharset  源字符集，默认ISO-8859-1
	 * @param destCharset 目标字符集，默认UTF-8
	 * @return 转换后的字符集
	 */
	public static String convert(String source, Charset srcCharset, Charset destCharset) {
		if (null == srcCharset) {
			srcCharset = StandardCharsets.ISO_8859_1;
		}

		if (null == destCharset) {
			srcCharset = StandardCharsets.UTF_8;
		}

		if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset)) {
			return source;
		}
		return new String(source.getBytes(srcCharset), destCharset);
	}

	/**
	 * @return 系统字符集编码
	 */
	public static String systemCharset() {
		return Charset.defaultCharset().name();
	}

	/**
	 * 平台内系统用户的唯一标志
	 */
	public static final String SYS_USER = "SYS_USER";

	/** 正常状态 */
	public static final String NORMAL = "0";

	/** 异常状态 */
	public static final String EXCEPTION = "1";

	/** 用户封禁状态 */
	public static final String USER_BLOCKED = "1";

	/** 角色封禁状态 */
	public static final String ROLE_BLOCKED = "1";

	/** 部门正常状态 */
	public static final String DEPT_NORMAL = "0";

	/** 字典正常状态 */
	public static final String DICT_NORMAL = "0";

	/** 是否为系统默认（是） */
	public static final String YES = "Y";

	/**
	 * 用户名长度限制
	 */
	public static final int USERNAME_MIN_LENGTH = 2;
	public static final int USERNAME_MAX_LENGTH = 20;

	/** 登录名称是否唯一的返回结果码 */
	public final static String USER_NAME_UNIQUE = "0";
	public final static String USER_NAME_NOT_UNIQUE = "1";

	/** 手机号码是否唯一的返回结果 */
	public final static String USER_PHONE_UNIQUE = "0";
	public final static String USER_PHONE_NOT_UNIQUE = "1";

	/** e-mail 是否唯一的返回结果 */
	public final static String USER_EMAIL_UNIQUE = "0";
	public final static String USER_EMAIL_NOT_UNIQUE = "1";

	/** 部门名称是否唯一的返回结果码 */
	public final static String DEPT_NAME_UNIQUE = "0";
	public final static String DEPT_NAME_NOT_UNIQUE = "1";

	/** 角色名称是否唯一的返回结果码 */
	public final static String ROLE_NAME_UNIQUE = "0";
	public final static String ROLE_NAME_NOT_UNIQUE = "1";

	/** 岗位名称是否唯一的返回结果码 */
	public final static String POST_NAME_UNIQUE = "0";
	public final static String POST_NAME_NOT_UNIQUE = "1";

	/** 角色权限是否唯一的返回结果码 */
	public final static String ROLE_KEY_UNIQUE = "0";
	public final static String ROLE_KEY_NOT_UNIQUE = "1";

	/** 岗位编码是否唯一的返回结果码 */
	public final static String POST_CODE_UNIQUE = "0";
	public final static String POST_CODE_NOT_UNIQUE = "1";

	/** 菜单名称是否唯一的返回结果码 */
	public final static String MENU_NAME_UNIQUE = "0";
	public final static String MENU_NAME_NOT_UNIQUE = "1";

	/** 字典类型是否唯一的返回结果码 */
	public final static String DICT_TYPE_UNIQUE = "0";
	public final static String DICT_TYPE_NOT_UNIQUE = "1";

	/** 参数键名是否唯一的返回结果码 */
	public final static String CONFIG_KEY_UNIQUE = "0";
	public final static String CONFIG_KEY_NOT_UNIQUE = "1";

	/**
	 * 密码长度限制
	 */
	public static final int PASSWORD_MIN_LENGTH = 5;
	public static final int PASSWORD_MAX_LENGTH = 36;

	/**
	 * 手机号码格式限制
	 */
	public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";

	/**
	 * 邮箱格式限制
	 */
	public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
	public final static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

	public final static String KAPTCHA_SESSION_DATE = "KAPTCHA_SESSION_DATE";

	public final static String KAPTCHA_SESSION_CONFIG_KEY = "kaptcha.session.key";

	public final static String KAPTCHA_SESSION_CONFIG_DATE = "kaptcha.session.date";

	public final static String KAPTCHA_BORDER = "kaptcha.border";

	public final static String KAPTCHA_BORDER_COLOR = "kaptcha.border.color";

	public final static String KAPTCHA_BORDER_THICKNESS = "kaptcha.border.thickness";

	public final static String KAPTCHA_NOISE_COLOR = "kaptcha.noise.color";

	public final static String KAPTCHA_NOISE_IMPL = "kaptcha.noise.impl";

	public final static String KAPTCHA_OBSCURIFICATOR_IMPL = "kaptcha.obscurificator.impl";

	public final static String KAPTCHA_PRODUCER_IMPL = "kaptcha.producer.impl";

	public final static String KAPTCHA_TEXTPRODUCER_IMPL = "kaptcha.textproducer.impl";

	public final static String KAPTCHA_TEXTPRODUCER_CHAR_STRING = "kaptcha.textproducer.char.string";

	public final static String KAPTCHA_TEXTPRODUCER_CHAR_LENGTH = "kaptcha.textproducer.char.length";

	public final static String KAPTCHA_TEXTPRODUCER_FONT_NAMES = "kaptcha.textproducer.font.names";

	public final static String KAPTCHA_TEXTPRODUCER_FONT_COLOR = "kaptcha.textproducer.font.color";

	public final static String KAPTCHA_TEXTPRODUCER_FONT_SIZE = "kaptcha.textproducer.font.size";

	public final static String KAPTCHA_TEXTPRODUCER_CHAR_SPACE = "kaptcha.textproducer.char.space";

	public final static String KAPTCHA_WORDRENDERER_IMPL = "kaptcha.word.impl";

	public final static String KAPTCHA_BACKGROUND_IMPL = "kaptcha.background.impl";

	public static final String KAPTCHA_BACKGROUND_CLR_FROM = "kaptcha.background.clear.from";

	public static final String KAPTCHA_BACKGROUND_CLR_TO = "kaptcha.background.clear.to";

	public static final String KAPTCHA_IMAGE_WIDTH = "kaptcha.image.width";

	public static final String KAPTCHA_IMAGE_HEIGHT = "kaptcha.image.height";
}
