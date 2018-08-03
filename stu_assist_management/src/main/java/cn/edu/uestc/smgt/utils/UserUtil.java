package cn.edu.uestc.smgt.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Encoder;
import cn.edu.uestc.smgt.pojo.User;

public class UserUtil {
	private static final String COOKIE_USER_SID = "COOKIE_USER_SID";
	private static final String COOKIE_USER_PASSWORD = "COOKIE_USER_PASSWORD";
	private static final String SESSION_USER = "USER";
	private static final String SESSION_USER_LOGOUT = "USER_LOGOUT";// 用户登出

	public static void removeCurrentUser(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_USER);
		session.setAttribute(SESSION_USER_LOGOUT, true);
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			if (name.equals(COOKIE_USER_SID)
					|| name.equals(COOKIE_USER_PASSWORD)) {
				cookie.setValue(null);
				cookie.setMaxAge(0);// 立刻删除Cookie
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
	}

	public static User getCurrentUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SESSION_USER);
		return user;
	}

	public static void setCurrentUser(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_USER, user);
		session.setAttribute(SESSION_USER_LOGOUT, false);
	}

	public static String EncoderByMd5(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		// 加密后的字符串
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}

	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		BASE64Encoder base64en = new BASE64Encoder();
		return base64en.encode(hexValue.toString().getBytes());

	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		System.out.println(EncoderByMd5("111111"));
		System.out.println(string2MD5("111111"));
		System.out.println(string2MD5("12345"));
	}
}
