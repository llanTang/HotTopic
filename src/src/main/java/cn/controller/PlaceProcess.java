package cn.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.model.Place;
import cn.service.PlaceNameSearch;
import net.sf.json.JSONArray;

@Controller
public class PlaceProcess {
	private JSONArray array;

	@RequestMapping("place.do")
	public void home(@ModelAttribute Place place,HttpSession session,HttpServletResponse response)throws Exception{
		PlaceNameSearch placeNameSearch=new PlaceNameSearch();
		
	    array=placeNameSearch.hotTopic(place, session.getId());
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(array);
	}

}
