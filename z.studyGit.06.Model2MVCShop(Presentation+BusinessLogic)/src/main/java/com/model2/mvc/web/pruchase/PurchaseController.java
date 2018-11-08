package com.model2.mvc.web.pruchase;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userservice;
	
	public PurchaseController() {
		System.out.println("Purchase 기본 생성자 호출 : " + this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prod_no") String prodno, HttpSession session) throws Exception {
		System.out.println("addPurchaseView 수행 시작...");
		
		User user = (User)session.getAttribute("user");
		
		int prodNo = Integer.parseInt(prodno);
		Product product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("purchase/addPurchaseView.jsp");
		modelAndView.addObject("user", user);
		modelAndView.addObject("product", product);
		
		System.out.println("addPurchaseView 수행 종료");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/addPurchase.do", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
									@RequestParam("prodNo") String prodno,
									@RequestParam("buyerId") String buyerId) throws Exception {
		System.out.println("addPurchase 수행 시작...");
		
		int prodNo = Integer.parseInt(prodno);
		Product product = new Product();
		User user = new User();
		
		user.setUserId(buyerId);
		product.setProdNo(prodNo);
				
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("0");
		System.out.println("얻어온 purchase : " + purchase);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
/*	
	@RequestMapping(value="/getPurchase.do", method=RequestMethod.POST)
	public ModelAndView getPurchase(@) throws Exception {
		
	}
	
*/
}
