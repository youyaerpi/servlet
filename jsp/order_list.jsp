﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>

	<%@include file="/jsp/header.jsp"%>

	<div class="container">
		<div class="row">

			<div style="margin:0 auto; margin-top:10px;width:950px;">
				<h1>
					<strong>我的订单</strong>
				</h1>
				<table class="table table-bordered">
					<c:forEach items="${page.list }" var="order">
						<tbody>
							<tr class="success">
								<th colspan="5">
								订单编号:${order.oid }
								总金额：￥${order.total }元
								<c:if test="${order.state==1 }">
								<a href="${pageContext.request.contextPath}/OrderServlet?method=findOrderByOid&oid=${order.oid }">付款</a>
								</c:if>
								<c:if test="${order.state==2 }">未发货 </c:if>
								<c:if test="${order.state==3 }">
								<a href="">签收</a>
								</c:if>
								<c:if test="${order.state==4 }">结束</c:if>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
						<c:forEach items="${order.list  }" var="item">
							<tr class="active">
								<td style="width:40%"><input type="hidden" name="id"
									value="22"> <img
									src="${pageContext.request.contextPath}/${item.product.pimage }"
									width="70" height="60"></td>
								<td style="width:30%"><a target="_blank">${item.product.pname }</a></td>
								<td style="width:20%">￥${item.product.shop_price }</td>
								<td style="width:10%">${item.quantity }</td>
								<td style="width:15%"><span class="subtotal">￥${item.total }</span>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</c:forEach>

				</table>
				<%@include file="/jsp/pageFile.jsp"%>

			</div>

		</div>
	</div>
	<%@include file="/jsp/footer.jsp"%>
</body>

</html>