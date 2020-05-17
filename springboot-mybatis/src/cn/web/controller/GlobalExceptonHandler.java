package cn.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptonHandler {

//	@ExceptionHandler(value=Exception.class)
//	@ResponseBody
//	public Object handle(HttpServletRequest req, Exception e) throws Exception{
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("message", e.getMessage());
//		map.put("url", req.getRequestURL().toString());
//		map.put("t", "1");
//		return map;
//	}
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView handle(HttpServletRequest req, Exception e) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", e.getMessage());
		map.put("url", req.getRequestURL().toString());
		
		e.printStackTrace();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("map", map);
		return mv;
	}
}
