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
	//==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		
	
%>
--%>
<html>
<head>
<title>���� �����ȸ</title>

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
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">��ü  <%--<%=resultPage.getTotalCount()%>--%>${resultPage.totalCount} �Ǽ�, ���� <%--<%=resultPage.getCurrentPage()%>--%>${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
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
			<td align="left">����
					<%-- 
					<%if(purchase.getTranCode() == null){ %>
						�Ǹŵ��
					<%} else if(purchase.getTranCode().equals("buy")) {%>
					 	���ſϷ�
					<%} else if(purchase.getTranCode().equals("tra")) {%>
						�����
					<%} else if(purchase.getTranCode().equals("com")) {%>
						��ۿϷ�
					<%} %>
					--%>
					<c:if test="${purchase.tranCode == null}">
						�Ǹŵ��
					</c:if>
					<c:if test="${purchase.tranCode eq 'buy'}">
						���ſϷ�
					</c:if>
					<c:if test="${purchase.tranCode eq 'tra'}">
						�����
					</c:if>
					<c:if test="${purchase.tranCode eq 'com'}">
						��ۿϷ�
					</c:if>
					���� �Դϴ�.</td>
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
				����
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
				����
			<%if(isLastPageSet == false){ %>
				</a>
			<%} %>
			--%>
			<%-- 
			<input type="hidden" id="currentPage" name="currentPage" value="<%=resultPage.getCurrentPage()%>"/>
			
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getBeginUnitPage()-1%>');">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetPurchaseList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>');">���� ��</a>
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

<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>



