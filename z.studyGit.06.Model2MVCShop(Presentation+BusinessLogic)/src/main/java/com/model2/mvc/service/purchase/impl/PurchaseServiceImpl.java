package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDAO;
	
	public void setProductDao(PurchaseDao purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}		
	
	public PurchaseServiceImpl() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.addPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Impl에서의 tranNo : " + tranNo);
		return purchaseDAO.getPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		System.out.println("Impl에서의 prodNo : " + prodNo);
		return purchaseDAO.getPurchase2(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		List<Purchase> list= purchaseDAO.getPurchaseList(search, buyerId);
		System.out.println("ServiceImpl 에서의 list : " + list);
		int totalCount = purchaseDAO.getTotalCount(buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		
		map.put("totalCount", new Integer(totalCount));
		return map;
	}

	@Override
	public HashMap<String, Object> getSaleList(Search searchVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDAO.updatePurcahse(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		purchaseDAO.updateTranCode(purchaseVO);
		
	}

}
