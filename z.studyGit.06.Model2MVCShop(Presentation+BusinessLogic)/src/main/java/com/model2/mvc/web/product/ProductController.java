package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	public ProductController() {
		System.out.println("product controller �⺻ ������ ȣ�� : " + this.getClass());
	}

	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception {
		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product ,  Model model ) throws Exception {
		//@ModelAttribute("product") Product product�� request�κ��� �����͸� getParameter�� �޾� ������ ��ü�� �� ���ְ�, Model�� ���� ���� �־����� �ʾƵ� Model�� addAttribute�Ͽ�
		//���� jsp�� �������ֱ���� �Ѵ�.
		System.out.println("/addProduct.do");
		//Business Logic
		productService.addProduct(product);
		//model.addAttribute("product", product);
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, Model model, HttpSession session, HttpServletResponse response) throws Exception {
		System.out.println("/getProduct.do");
		
		Product product = productService.getProduct(prodNo);		
		//================================= �ֱ� �� ��ǰ ��� ���� =========================================
		
		String history = "";
		
		if(session.getAttribute("seeProd") == null) {
			session.setAttribute("seeProd", history);
		}
		
		history = (String)session.getAttribute("seeProd"); 
		history += ("," + Integer.toString(prodNo));
		session.setAttribute("seeProd", history);
		
		Cookie cookie = new Cookie("history", (String)session.getAttribute("seeProd"));
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		//========================================================================================
		
		if(!menu.equals("manage")) {
			menu = "search";
		}
		//String menu = "search";
		
		model.addAttribute("product", product);
		model.addAttribute("menu" , menu);
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		System.out.println("/updateProductView.do");
		
		Product product = productService.getProduct(prodNo);		
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";		
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product, Model model , HttpSession session) throws Exception{
		System.out.println("/updateProduct.do");
		
		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?prodNo="+product.getProdNo()+"&menu=manage";
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", request.getParameter("menu"));
		
		return "forward:/product/listProduct.jsp";
	}
	
	
}