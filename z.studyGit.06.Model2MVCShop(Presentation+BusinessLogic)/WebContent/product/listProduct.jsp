<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%-- 
<%@page import="com.model2.mvc.service.purchase.impl.PurchaseServiceImpl"%>
<%@page import="com.model2.mvc.service.purchase.PurchaseService"%>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.util.*" %>
<%@ page import="com.model2.mvc.common.*" %>

<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		
	String menu = (String)request.getAttribute("menu");
	
	//여기서부턴 검색 유지 로직
	boolean nowSearchState = false;
	
	if(search.getSearchCondition() != null){
		nowSearchState = true;
	} else {
		nowSearchState = false;
	}
%>

--%>
<c:if test="${search.searchCondition != null }">
	<c:set var="nowSearchState" value="true" />
</c:if>
<c:if test="${search.searchCondition == null}">
	<c:set var="nowSearchState" value="false" />	
</c:if>
<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetProductList(currentPage){
		document.getElementById("currentPage").value = currentPage;
   		document.detailForm.submit();
	}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
							<%--<%=(menu.equals("manage"))?"상품관리":"상품조회"%>--%>
							${menu.equals("manage") ? "상품관리" : "상품조회"}
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	
	
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- 
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>
				<option value="2" <%= (searchCondition.equals("2") ? "selected" : "")%>>상품가격</option>		
			--%>
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>		
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>		
			</select>
			
			<select name="searchFilter" class="ct_input_g" style="width:80px">
				<option value="0" ${! empty search.searchFilter && search.searchFilter==0 ? "selected" : ""}>기본</option>
				<option value="1" ${! empty search.searchFilter && search.searchFilter==1 ? "selected" : ""}>가격 낮은순(전체)</option>
				<option value="2" ${! empty search.searchFilter && search.searchFilter==2 ? "selected" : ""}>가격 높은순(전체)</option>
				<option value="3" ${! empty search.searchFilter && search.searchFilter==3 ? "selected" : ""}>가격 낮은순(이 페이지에서)</option>
				<option value="4" ${! empty search.searchFilter && search.searchFilter==4 ? "selected" : ""}>가격 높은순(이 페이지에서)</option>
			</select>
			
			<input 	type="text" name="searchKeyword"  value="<%--<%=searchKeyword %>--%>${search.searchKeyword}" 
							class="ct_input_g" style="width:200px; height:19px" >
		</td>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체  <%--<%=resultPage.getTotalCount()%>--%>${resultPage.totalCount} 건수, 현재 <%--<%=resultPage.getCurrentPage()%> --%>${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%--<% 	
		//PurchaseService service = new PurchaseServiceImpl();
		User user = (User)session.getAttribute("user");
		//int no=list.size();
		for(int i=0; i<list.size(); i++) {
			Product product = list.get(i);
			//vo = (ProductVO)list.get(i);
			//Purchase purchase = service.getPurchase2(vo.getProdNo());
			//System.out.println("JSP 내부에서 get된 purchaseVO : " + purchase);
	%>--%>
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
	
		<tr class="ct_list_pop">
			<td align="center"><%--<%=i+1%> --%>${i}</td>
			<td></td>
				<%--<%if(menu.equals("search")){%> --%>
				<c:if test="${menu == 'search'}">
					<td align="left"><a href="/getProduct.do?prodNo=<%-- <%=product.getProdNo() %>--%>${product.prodNo}&menu=<%--<%=menu%>--%>${menu}"><%--<%=product.getProdName()%>--%>${product.prodName}</a></td>
				<%--<%} else if(menu.equals("manage")){%> --%>
				</c:if>
				<c:if test="${menu == 'manage'}">
					<td align="left"><a href="/updateProductView.do?prodNo=<%-- <%=product.getProdNo() %>--%>${product.prodNo}&menu=<%--<%=menu%>--%>${menu}"><%--<%=product.getProdName()%>--%>${product.prodName}</a></td>				
				<%--<%}%> --%>
				</c:if> 
			<td></td>
			<td align="left"><%--<%=product.getPrice()%> --%>${product.price}</td>
			<td></td>
			<td align="left"><%--<%=product.getRegDate()%> --%>${product.regDate}</td>
			<td></td>
			<td align="left">
			
			
				<%--<%if(user != null && user.getUserId().equals("admin")) {%>--%>
				<c:if test="${user != null && user.userId == 'admin'}"> <%--equals in EL is eq / not equals is ne --%>
					<%-- 
					<%if(product.getProTranCode() == null){ %>
						판매등록
					<%} else if(product.getProTranCode().equals("buy")) {%>
						<%if(nowSearchState){ %>
							<a href="/updateTranCode.do?prodNo=<%=product.getProdNo()%>&tranCode=<%=product.getProTranCode()%>&page=<%=resultPage.getBeginUnitPage()-resultPage.getPageUnit()%>&menu=<%=menu%>&searchCondition=<%=search.getSearchCondition()%>&searchKeyword=<%=search.getSearchKeyword()%>">상품구매(배송)</a>
						<%} else { %>
							<a href="/updateTranCode.do?prodNo=<%=product.getProdNo()%>&tranCode=<%=product.getProTranCode()%>&page=<%=resultPage.getBeginUnitPage()-resultPage.getPageUnit()%>&menu=<%=menu%>">상품구매(배송)</a>
						<%} %>
					<%} else if(product.getProTranCode().equals("tra")) {%>
						배송중
					<%} else if(product.getProTranCode().equals("com")) {%>
						배송완료
					<%} %>
					--%>
					<c:if test="${product.proTranCode == 'sel' || product.proTranCode == '0  ' || product.proTranCode == null}">
						판매등록
					</c:if>
					<c:if test="${product.proTranCode == 'buy' || product.proTranCode == '1  '}">
						<c:if test="${nowSearchState}">
							<a href="/updateTranCode.do?prodNo=${product.prodNo}&tranCode=${product.proTranCode}&page=${resultPage.beginUnitPage-resultPage.pageUnit}&menu=${menu}&searchCondition=${search.searchCondition}&searchKeyword=${search.searchKeyword}">상품구매(배송)</a>							
						</c:if>
						<c:if test="${!nowSearchState}">
							<a href="/updateTranCode.do?prodNo=${product.prodNo}&tranCode=${product.proTranCode}&page=${resultPage.beginUnitPage-resultPage.pageUnit}&menu=${menu}">상품구매(배송)</a>		
						</c:if>
					</c:if>
					<c:if test="${product.proTranCode == 'tra'|| product.proTranCode == '2  '}">
						배송중
					</c:if>
					<c:if test="${product.proTranCode == 'com'|| product.proTranCode == '3  '}">
						배송완료
					</c:if>
				</c:if>	
				<%-- 
				<%} else if(user != null && user.getUserId().contains("user")){%>
				
					 <%if(product.getProTranCode() == null){ %>
						판매등록
					<%} else if(product.getProTranCode().equals("buy")) {%>
					 	구매완료
					<%} else if(product.getProTranCode().equals("tra")) {%>
						<%if(nowSearchState){ %>
							<a href="/updateTranCode.do?prodNo=<%=product.getProdNo()%>&tranCode=<%=product.getProTranCode()%>&page=<%=resultPage.getBeginUnitPage()-resultPage.getPageUnit()%>&menu=<%=menu%>&searchCondition=<%=search.getSearchCondition()%>&searchKeyword=<%=search.getSearchKeyword()%>">배송중(수령확인)</a>
						<%} else { %>
							<a href="/updateTranCode.do?prodNo=<%=product.getProdNo()%>&tranCode=<%=product.getProTranCode()%>&page=<%=resultPage.getBeginUnitPage()-resultPage.getPageUnit()%>&menu=<%=menu%>">배송중(수령확인)</a>
						<%} %>
					<%} else if(product.getProTranCode().equals("com")) {%>
						배송완료
					<%} %>
				--%>
				<c:if test="${user.role eq 'user'}">
				<%-- <fn:contains test="${user.userId, 'user'}"> --%>
					<c:if test="${product.proTranCode == 'sel'|| product.proTranCode == '0  '|| product.proTranCode == null}">
						판매등록
					</c:if>
					<c:if test="${product.proTranCode == 'buy'|| product.proTranCode == '1  '}">
						구매완료
					</c:if>
					<c:if test="${product.proTranCode == 'tra'|| product.proTranCode == '2  '}">
						<c:if test="${nowSearchState}">
							<a href="/updateTranCode.do?prodNo=${product.prodNo}&tranCode=${product.proTranCode}&page=${resultPage.beginUnitPage-resultPage.pageUnit}&menu=${menu}&searchCondition=${search.searchCondition}&searchKeyword=${search.searchKeyword}">배송중(수령확인)</a>							
						</c:if>
						<c:if test="${!nowSearchState}">
							<a href="/updateTranCode.do?prodNo=${product.prodNo}&tranCode=${product.proTranCode}&page=${resultPage.beginUnitPage-resultPage.pageUnit}&menu=${menu}">배송중(수령확인)</a>		
						</c:if>
					</c:if>
					<c:if test="${product.proTranCode == 'com'|| product.proTranCode == '3  '}">
						배송완료
					</c:if>
				<%-- </fn:contains> --%>
				</c:if> 
				<%-- 
				<%} else if(user == null) {%>
				
					<%if(product.getProTranCode() == null){ %>
						판매등록
					<%} else if(product.getProTranCode().equals("buy")) {%>
					 	구매완료
					<%} else if(product.getProTranCode().equals("tra")) {%>
						배송중
					<%} else if(product.getProTranCode().equals("com")) {%>
						배송완료
					<%} %>
				
				<%} %>		
				--%>
				<c:if test="${user == null}">
					<c:if test="${product.proTranCode == 'sel'|| product.proTranCode == '0  '|| product.proTranCode == null}">
						판매등록
					</c:if>
					<c:if test="${product.proTranCode == 'buy'|| product.proTranCode == '1  '}">
						구매완료
					</c:if>
					<c:if test="${product.proTranCode == 'tra'|| product.proTranCode == '2  '}">
						배송중
					</c:if>
					<c:if test="${product.proTranCode == 'com'|| product.proTranCode == '3  '}">
						배송완료
					</c:if>
				</c:if>
		
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
	
	<%--<%}%> --%>
	
	<%-- 
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
	<tr class="ct_list_pop">
		<td align="center">2</td>
		<td></td>
				
				<td align="left"><a href="/getProduct.do?prodNo=10001&menu=search">자전거</a></td>
		
		<td></td>
		<td align="left">10000</td>
		<td></td>
		<td align="left">2012-11-14</td>
		<td></td>
		<td align="left">
		
			배송중
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
	<tr class="ct_list_pop">
		<td align="center">1</td>
		<td></td>
				
				<td align="left"><a href="/getProduct.do?prodNo=10002&menu=search">보르도</a></td>
		
		<td></td>
		<td align="left">1170000</td>
		<td></td>
		<td align="left">2012-10-14</td>
		<td></td>
		<td align="left">
		
			판매중
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	--%>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">

			<%-- 
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전	
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getBeginUnitPage()-1%>');">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>');">이후 ▶</a>
			<% } %>
			--%>
			<%-- <c:if test="${resultPage.currentPage <= resultPage.pageUnit}">
					◀ 이전
			</c:if>
			<c:if test="${resultPage.currentPage > resultPage.pageUnit}">
					<a href="javascript:fncGetProductList('${resultPage.endUnitPage}');">◀ 이전</a>
			</c:if>
			
			<c:forEach var="i" begin="${resultPage.beginUnitPage}" step="1" end="${resultPage.endUnitPage}">
					<a href="javascript:fncGetProductList('${i}');">${i}</a>
			</c:forEach>
			
			<c:if test="${resultPage.endUnitPage >= resultPage.maxPage}">
			
			</c:if>--%>
			<%-- <jsp:include page="../common/pageNavigator.jsp"/>	 --%>
			<input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>

			
			<jsp:include page="../common/pageNavigator.jsp">
				<jsp:param value="Product" name="type"/>
			</jsp:include>
			
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
