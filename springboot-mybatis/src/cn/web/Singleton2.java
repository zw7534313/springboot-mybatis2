package cn.web;

public class Singleton2 {
	private static Singleton2 s;
	private Singleton2() {}
	
	static {
		s = new Singleton2();
	}
	
	public static Singleton2 getInstance() {
		return s;
	}
}
