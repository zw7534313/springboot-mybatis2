package cn.web;

public class Singleton3 {
	private static class InSingle{
		private static final Singleton3 s = new Singleton3();
	}
	
	private Singleton3() {}
	public static final Singleton3 getInstance() {
		return InSingle.s;
	}
}
