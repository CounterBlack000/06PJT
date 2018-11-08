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
	
	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
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
		Assert.assertEquals("�����ٸ�", purchase.getPurchaseProd().getProdName());
	}
	
	//@Test
	public void testAddPurchase() throws Exception{
		Purchase purchase = new Purchase();
		purchase.setDivyAddr("�̰��� �ּ�");
		purchase.setDivyDate("18/11/01");
		purchase.setDivyRequest("�̰��� �䱸����");
		purchase.setPaymentOption("001");
		purchase.setReceiverName("���̸�");
		purchase.setReceiverPhone("������");
		purchase.setTranCode("sel");
		
		/*addPurchase�� �Ѵٴ� �� ��ü�� ��ǰ�� �������� �����ߴٴ� ��. web���� �����ϴ� ��� �� ���� ������ JSP�κ��� �ڿ����� �����������, 
		 * ����� �����׽�Ʈ�̹Ƿ� �׷� �� �� ����. ���� �� ������Ʈ �������� prodNo�� userId�� ������ �� ������ ��ü�� ����� ����ϰڴ�.*/
		
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
		purchase.setDivyAddr("������ �ּ�");
		purchase.setDivyDate("18/11/01");
		purchase.setDivyRequest("������ �䱸����");
		purchase.setPaymentOption("001");
		purchase.setReceiverName("���̸�");
		purchase.setReceiverPhone("������");

		
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
