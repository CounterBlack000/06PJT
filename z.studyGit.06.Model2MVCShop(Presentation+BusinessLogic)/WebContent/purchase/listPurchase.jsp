<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.*" %>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>


<%

	List<Purchase> list= (List<Purchase>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		
	
%>
--%>
<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetPurchaseList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체  <%--<%=resultPage.getTotalCount()%>--%>${resultPage.totalCount} 건수, 현재 <%--<%=resultPage.getCurrentPage()%>--%>${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<%--<% 	
		int no=list.size();
		for(int i=0; i<list.size(); i++) {
			Purchase purchase = list.get(i);
			//vo = (ProductVO)list.get(i);
	%>--%>
	
	<c:set var="i" value="0" />
	<c:forEach var="purchase" items="${list}">
		<c:set var="i" value="${ i+1 }" />
	
		<tr class="ct_list_pop">
			<td align="center">
			
				<a href="/getPurchase.do?tranNo=<%--<%=purchase.getTranNo()%>--%>${purchase.tranNo}"><%--<%=i+1%> --%>${i}</a>
			
			</td>
			<td></td>
			<td align="left">
				<a href="/getUser.do?userId=<%--<%=purchase.getBuyer().getUserId()%>--%>${purchase.buyer.userId}">${purchase.buyer.userId}</a>
			</td>
			<td></td>
			<td align="left"><%--<%=purchase.getReceiverName()%>--%>${purchase.receiverName}</td>
			<td></td>
			<td align="left"><%--<%=purchase.getReceiverPhone()%>--%>${purchase.receiverPhone}</td>
			<td></td>
			<td align="left">현재
					<%-- 
					<%if(purchase.getTranCode() == null){ %>
						판매등록
					<%} else if(purchase.getTranCode().equals("buy")) {%>
					 	구매완료
					<%} else if(purchase.getTranCode().equals("tra")) {%>
						배송중
					<%} else if(purchase.getTranCode().equals("com")) {%>
						배송완료
					<%} %>
					--%>
					<c:if test="${purchase.tranCode == null}">
						판매등록
					</c:if>
					<c:if test="${purchase.tranCode eq 'buy'}">
						구매완료
					</c:if>
					<c:if test="${purchase.tranCode eq 'tra'}">
						배송중
					</c:if>
					<c:if test="${purchase.tranCode eq 'com'}">
						배송완료
					</c:if>
					상태 입니다.</td>
			<td></td>
			<td align="left"><% %>
				
			</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
		<%--<%}%> --%>
	</c:forEach>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 	<%-- 
			<a href="/listPurchase.do?page=1">1</a> 
			--%>
			
			<%-- 
			 
			<%if(isFisrtPageset == false){ %>
				<a href="/listPurchase.do?page=<%=nowStartPage-pageCounter%>&menu=<%=menu%>">
			<%} %>
				이전
			<%if(isFisrtPageset == false){ %>
				</a>
			<%} %>
			
			<%
				for(int i = nowStartPage; i <= nowEndPage; i++){
			%>
				<a href="/listPurchase.do?page=<%=i%>&menu=<%=menu%>"><%=i %></a>			
			<%
				}
			%>		
			
			<%if(isLastPageSet == false){ %>
				<a href="/listPurchase.do?page=<%=nowStartPage+pageCounter%>&menu=<%=menu%>">
			<%} %>
				다음
			<%if(isLastPageSet == false){ %>
				</a>
			<%} %>
			--%>
			<%-- 
			<input type="hidden" id="currentPage" name="currentPage" value="<%=resultPage.getCurrentPage()%>"/>
			
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getBeginUnitPage()-1%>');">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetPurchaseList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>');">이후 ▶</a>
			<% } %>
			--%>
			<%-- <jsp:include page="../common/pageNavigator.jsp"/>	--%>
			<input type="hidden" id="currentPage" name="currentPage" value="<%-- <%=resultPage.getCurrentPage()%> --%>${resultPage.currentPage}"/>
			
			<jsp:include page="../common/pageNavigator.jsp">
				<jsp:param value="Purchase" name="type"/>
			</jsp:include>
			
			
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>



