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
		System.out.println("Purchase �⺻ ������ ȣ�� : " + this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prod_no") String prodno, HttpSession session) throws Exception {
		System.out.println("addPurchaseView ���� ����...");
		
		User user = (User)session.getAttribute("user");
		
		int prodNo = Integer.parseInt(prodno);
		Product product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("purchase/addPurchaseView.jsp");
		modelAndView.addObject("user", user);
		modelAndView.addObject("product", product);
		
		System.out.println("addPurchaseView ���� ����");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/addPurchase.do", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
									@RequestParam("prodNo") String prodno,
									@RequestParam("buyerId") String buyerId) throws Exception {
		System.out.println("addPurchase ���� ����...");
		
		int prodNo = Integer.parseInt(prodno);
		Product product = new Product();
		User user = new User();
		
		user.setUserId(buyerId);
		product.setProTranCode("buy");
		product.setProdNo(prodNo);
				
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("buy");
		System.out.println("���� purchase : " + purchase);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("addPurchase ���� ����...");
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") String tranNo
									) throws Exception {
		
		System.out.println("getPurchase ���� ����.....");
		int tranNoCast = Integer.parseInt(tranNo);
		
		Purchase purchase = purchaseService.getPurchase(tranNoCast);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/getPurchase.jsp"); //����Ʈ�� forward
		modelAndView.addObject("purchase", purchase);
		System.out.println("getPurchase ���� ��.....");

		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView getPurchaseList(@ModelAttribute("search") Search search,
										HttpSession session) throws Exception {
		System.out.println("getPurchaseList ���� ����...");
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		
		Map<String,Object> map = purchaseService.getPurchaseList(search, buyerId);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		System.out.println("purchaseList������ purchase list : " + map.get("list"));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		System.out.println("getPurchaseList ���� ��...");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") String tranNo) throws Exception {
		System.out.println("updatePurchaseView ���� ����...");
		
		Purchase purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		
		System.out.println("updatePurchaseView ������ purchase : " + purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase", purchase);
		System.out.println("updatePurchaseView ���� ��...");

		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception{
		System.out.println("updatePurchase ���� ����...");
		
		System.out.println("updatePurchase ���� ������Ʈ�� purchase" + purchase);
		purchaseService.updatePurcahse(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/getPurchase.do");
		modelAndView.addObject("tranNo", purchase.getTranNo());
		System.out.println("updatePurchase ���� ��...");
		
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
