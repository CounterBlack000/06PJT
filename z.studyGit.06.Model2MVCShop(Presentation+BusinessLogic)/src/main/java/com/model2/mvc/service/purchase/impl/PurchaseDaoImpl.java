package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}		
	
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getPurchase2", ProdNo);
	}

	@Override
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("search", search);
		
		List<Purchase> list = sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
		
		System.out.println("dao에서 전달된 값들 : " + buyerId + search);
		System.out.println("dao에 매핑된 값 : " + map);
		System.out.println("dao에서 확보한 list : " + list);
		return list;
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
			sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
			sqlSession.update("PurchaseMapper.updateTrancode", purchase);		
	}

	@Override
	public int getTotalCount(String buyerId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
	}

}
