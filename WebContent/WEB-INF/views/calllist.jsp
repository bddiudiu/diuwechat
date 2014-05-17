<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>回复信息</title>
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
								<i class="icon-user"></i>
								回复信息
							</small>
						</h1>
					</div> 
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							<form class="form-inline" method="get" action="<%=request.getContextPath() %>/manager/students">
								<input type="hidden" name="pagenum" value="${pagenum}">
								<button  type="button" onclick="location.href='<%=request.getContextPath() %>/manager/toadd'" class="btn btn-purple btn-small">
									添加回复
									<i class="icon-plus-sign icon-on-right bigger-110"></i>
								</button>
								
								<c:if test="${param.notice != null}">
									<div class="alert alert-info">
										<button type="button" class="close" data-dismiss="alert">
											<i class="icon-remove"></i>
										</button>
										<i class="icon-ok"></i>
										<c:choose>
											<c:when test="${param.notice eq 'update'}"><strong>更新成功</strong></c:when>
											<c:when test="${param.notice eq 'del'}"><strong>删除成功</strong></c:when>
											<c:when test="${param.notice eq 'add'}"><strong>添加成功</strong></c:when>
										</c:choose>
									</div>
								</c:if>
								
							</form>
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th width="60%">回复内容</th>
										<th width="10%">回复类型</th>
										<th width="10%">是否启用</th>
										<th width="20%" >操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${callList}"  var="call"  >
									<tr>
										<td>${call.content}</td>
										<td>${call.type}</td>
										<td>
										<c:choose>
											<c:when test="${call.state eq 1 }">启用</c:when>
											<c:otherwise>停用</c:otherwise>
										</c:choose>
										</td>
										<td >
											<button class="btn btn-mini btn-primary" onclick="location.href='<%=request.getContextPath() %>/manager/calldetail?id=${call.id}'"><i class="icon-file"></i>编辑</button>
											<button class="btn btn-mini btn-primary" onclick="location.href='<%=request.getContextPath() %>/manager/delcall?id=${call.id}'"><i class="icon-file"></i>删除</button>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							
					 		<div class="dataTables_paginate paging_bootstrap pagination">
							  <button class="btn btn-success btn-mini" onclick="location.href='<%=request.getContextPath() %>/manager/students?pagenum=${pagenum-1}'" <c:if test="${pagenum <= 1}">disabled="disabled"</c:if>    >&laquo;</button>
							  <button class="btn btn-success btn-mini" disabled="disabled">第 ${pagenum} 页</button>
							  <button class="btn btn-success btn-mini" onclick="location.href='<%=request.getContextPath() %>/manager/students?pagenum=${pagenum+1}'" <c:if test="${length < 8}">disabled="disabled"</c:if> >&raquo;</button>
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