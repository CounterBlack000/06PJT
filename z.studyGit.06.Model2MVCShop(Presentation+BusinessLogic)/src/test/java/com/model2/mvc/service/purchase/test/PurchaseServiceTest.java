package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {
	
	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	public void testGetPurchase() throws Exception{
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchase(10040);
		
		Assert.assertNotNull(purchase);
		System.out.println(purchase);
		Assert.assertEquals("user12", purchase.getBuyer().getUserId());
		Assert.assertEquals(10122, purchase.getPurchaseProd().getProdNo());
		Assert.assertNotNull(purchase.getPurchaseProd().getProdName());
		Assert.assertEquals("오졌다리", purchase.getPurchaseProd().getProdName());
	}
	
	//@Test
	public void testAddPurchase() throws Exception{
		Purchase purchase = new Purchase();
		purchase.setDivyAddr("이것은 주소");
		purchase.setDivyDate("18/11/01");
		purchase.setDivyRequest("이것은 요구사항");
		purchase.setPaymentOption("001");
		purchase.setReceiverName("이이름");
		purchase.setReceiverPhone("포오온");
		purchase.setTranCode("sel");
		
		/*addPurchase를 한다는 것 자체가 상품을 누군가가 구매했다는 뜻. web으로 구현하는 경우 두 가지 정보는 JSP로부터 자연스레 따라오겠지만, 
		 * 여기는 단위테스트이므로 그럴 수 가 없다. 따라서 이 프로젝트 한정으로 prodNo와 userId만 셋팅한 두 도메인 객체를 만들어 사용하겠다.*/
		
		User user = new User();
		Product product = new Product();
		
		user.setUserId("user12");
		product.setProdNo(10122);
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		
		purchaseService.addPurchase(purchase);
		
		purchase = purchaseService.getPurchase(10040);
		Assert.assertNotNull(purchase);
		System.out.println(purchase);
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception{
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchase(10040);
		
		purchase.setTranNo(10040);
		purchase.setDivyAddr("수정된 주소");
		purchase.setDivyDate("18/11/01");
		purchase.setDivyRequest("수정된 요구사항");
		purchase.setPaymentOption("001");
		purchase.setReceiverName("이이름");
		purchase.setReceiverPhone("포오온");

		
		purchaseService.updatePurcahse(purchase);
		purchase = purchaseService.getPurchase(10040);
		Assert.assertNotNull(purchase);
		System.out.println(purchase);
	}
	
	@Test
	public void testGetPurchaseListByBuyerId() throws Exception{
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	//String buyerId = "user12";
	 	Map<String,Object> map = purchaseService.getPurchaseList(search, "user12");
	 	List<Object> list = (List<Object>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println("the list of total count is : "+totalCount);
	 	System.out.println("the list is : "+list);
	}
	
	@Test
	public void testUpdatePurchaseTranCode() throws Exception{
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchase2(10001);
		purchase.setTranCode("1");
		purchaseService.updateTranCode(purchase);
		purchase = purchaseService.getPurchase2(10001);
		
		Assert.assertEquals("1  ", purchase.getTranCode());
	}
}
