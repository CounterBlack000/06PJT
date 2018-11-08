package com.model2.mvc.web.pruchase;

import java.util.Map;

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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
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
		product.setProTranCode("buy");
		product.setProdNo(prodNo);
				
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("buy");
		System.out.println("얻어온 purchase : " + purchase);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("addPurchase 수행 종료...");
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") String tranNo
									) throws Exception {
		
		System.out.println("getPurchase 수행 시작.....");
		int tranNoCast = Integer.parseInt(tranNo);
		
		Purchase purchase = purchaseService.getPurchase(tranNoCast);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/getPurchase.jsp"); //디폴트가 forward
		modelAndView.addObject("purchase", purchase);
		System.out.println("getPurchase 수행 끝.....");

		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView getPurchaseList(@ModelAttribute("search") Search search,
										HttpSession session) throws Exception {
		System.out.println("getPurchaseList 수행 시작...");
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		
		Map<String,Object> map = purchaseService.getPurchaseList(search, buyerId);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		System.out.println("purchaseList에서의 purchase list : " + map.get("list"));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		System.out.println("getPurchaseList 수행 끝...");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") String tranNo) throws Exception {
		System.out.println("updatePurchaseView 수행 시작...");
		
		Purchase purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		
		System.out.println("updatePurchaseView 에서의 purchase : " + purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase", purchase);
		System.out.println("updatePurchaseView 수행 끝...");

		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception{
		System.out.println("updatePurchase 수행 시작...");
		
		System.out.println("updatePurchase 에서 업데이트할 purchase" + purchase);
		purchaseService.updatePurcahse(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/getPurchase.do");
		modelAndView.addObject("tranNo", purchase.getTranNo());
		System.out.println("updatePurchase 수행 끝...");
		
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode( @RequestParam("prodNo") String prodNo, @RequestParam("tranCode") String tranCode, 
										@RequestParam("page") String inPage, @RequestParam("menu") String menu,
										@RequestParam("searchCondition") String searchCondition, @RequestParam("searchKeyword") String searchKeyword) 
										throws Exception {
		
		Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(prodNo));
		
		if(tranCode.equals("buy")) {
			tranCode = "tra";
		} else if(tranCode.equals("tra")) {
			tranCode = "com";
		}
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(searchCondition != null && searchKeyword != null) {
			modelAndView.setViewName("redirect:/listProduct.do?page="+inPage+"&menu="+menu+"&searchCondition="+searchCondition+"&searchKeyword="+searchKeyword);
		}
		modelAndView.setViewName("redirect:/listProduct.do?page="+inPage+"&menu="+menu);

		return modelAndView;
		
	}
	
	
}
