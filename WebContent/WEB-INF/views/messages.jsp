<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="GBK"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>微信接收信息</title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="shortcut icon" href="<%=request.getContextPath() %>/assets/images/favicon.ico" />
		<%@include file="/WEB-INF/views/common/css.jsp" %>
	</head>

	<body>
		<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
		<div class="main-container container-fluid">
			<a class="menu-toggler" id="menu-toggler" href="#">
				<span class="menu-text"></span>
			</a>
			<%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
			<div class="main-content">
				 
				<div class="page-content">
					<div class="page-header position-relative">
						<h1>
							<small>
								<i class="icon-comment"></i>
								微信接收信息
							</small>
						</h1>
					</div> 
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							 
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr >
								      <th  width="5%">#</th>
								      <th width="15%">MsgId</th>
								      <th width="10%">MsgType</th>
								      <th width="15%">FromUserName</th>
								      <th width="25%">CreateTime</th>
								      <th width="30%">control</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${messageList}"  var="message"   varStatus="st" >
								  		<tr>
								  		<td>${st.index+1}</td>
								  		<td>${message.msgId}</td>
								  		<td>
								  			<c:choose>
								  				<c:when test="${message.msgType eq 'TEXT'}">文字</c:when>
								  				<c:otherwise>图片</c:otherwise>
								  			</c:choose>
								  		</td>
								  		<td>${message.fromUserName}</td>
								  		<td>${message.createTime}</td>
								  		<td><button class="btn btn-mini btn-primary" onclick="location.href='<%=request.getContextPath() %>/manager/detail?id=${message.id}'"><i class="icon-file"></i>查看</button></td>
								  		</tr>
								  	</c:forEach>
								  </tbody>
							</table>
							
					 		<div class="dataTables_paginate paging_bootstrap pagination">
							  <button class="btn btn-success btn-mini" onclick="location.href='<%=request.getContextPath() %>/manager/messages?pagenum=${pagenum-1}'" <c:if test="${pagenum <= 1}">disabled="disabled"</c:if>    >&laquo;</button>
							  <button class="btn btn-success btn-mini" disabled="disabled">第 ${pagenum} 页</button>
							  <button class="btn btn-success btn-mini" onclick="location.href='<%=request.getContextPath() %>/manager/messages?pagenum=${pagenum+1}'" <c:if test="${length < 10}">disabled="disabled"</c:if> >&raquo;</button>
					 		</div>
							 
							<!--PAGE CONTENT ENDS-->
					</div><!--/.span-->
				</div><!--/.row-fluid-->
			</div><!--/.page-content-->
		</div><!--/.main-content-->
	</div><!--/.main-container-->

		<%@include file="/WEB-INF/views/common/js.jsp" %>
		
	</body>
</html>