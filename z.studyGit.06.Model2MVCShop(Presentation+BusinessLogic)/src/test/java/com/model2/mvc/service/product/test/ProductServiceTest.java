package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml" })
public class ProductServiceTest {
	
	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		product.setProdName("오졌다리");
		product.setProdDetail("지렸다리");
		product.setPrice(10000);
		product.setManuDate("2018-10-11");
		product.setFileName("뭐하지");
		
		productService.addProduct(product);		
	}
	
	//@Test
	public void testGetProduct() throws Exception{
		Product product = new Product();
		product = productService.getProduct(10122);
		
		Assert.assertEquals(10122, product.getProdNo());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("오졌다리", product.getProdName());
		Assert.assertEquals("지렸다리", product.getProdDetail());
		Assert.assertEquals("18/10/11", product.getManuDate());
		Assert.assertEquals("뭐하지", product.getFileName());		
	}
	
	//@Test
	public void testUpdateProduct() throws Exception{
		Product product = new Product();
		product = productService.getProduct(10122);

		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("오졌다리", product.getProdName());
		Assert.assertEquals("지렸다리", product.getProdDetail());
		Assert.assertEquals("18/10/11", product.getManuDate());
		Assert.assertEquals("뭐하지", product.getFileName());
		
		product.setProdNo(10122);
		product.setPrice(15000);
		product.setProdName("오져브러");
		product.setProdDetail("피자먹고싶다.");
		product.setManuDate("12/10/18");
		product.setFileName("코딩하는데따분하다.");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10122);
		Assert.assertNotNull(product);
		
		Assert.assertEquals(15000, product.getPrice());
		Assert.assertEquals("오져브러", product.getProdName());
		Assert.assertEquals("피자먹고싶다.", product.getProdDetail());
		Assert.assertEquals("12/10/18", product.getManuDate());
		Assert.assertEquals("코딩하는데따분하다.", product.getFileName());
	}
	
	//@Test
	public void testGetProductListAll() throws Exception{
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	@Test
	public void testGetProductListByProdNo() throws Exception{
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("100%");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println(list); 	
		
	}
	
	//@Test
	 public void testGetProductListByProductName() throws Exception{
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("123%");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println(list); 	
	}
}
