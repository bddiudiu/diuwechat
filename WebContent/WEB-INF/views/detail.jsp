<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="GBK"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>查看消息</title>
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
								<i class="icon-list-alt"></i>
								查看消息
							</small>
						</h1>
					</div> 
					<div class="row-fluid">
						
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							<form action="addcall" id="addclasses" class="form-horizontal" method="post">
								<div class="control-group">
									<label class="control-label" for="id">发送人</label>
									<div class="controls">
										${message.fromUserName }
									</div>
								</div>
								<div class="control-group">
									<label class="control-label"  for="name">消息类型</label>
									<div class="controls">
										<c:choose>
											<c:when test="${message.msgType eq 'TEXT' }">文字</c:when>
											<c:otherwise>图片</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label"  for="headteacher">发送时间</label>
									<div class="controls">
										${message.createTime }
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label"  for="headteacher">消息内容</label>
									<div class="controls">
										<c:choose>
											<c:when test="${message.msgType eq 'TEXT' }">${message.content }</c:when>
											<c:otherwise><img src='${message.picUrl }'/></c:otherwise>
										</c:choose>
										
									</div>
								</div>
								
								
								
								<div class="form-actions">
									<button class="btn btn-info" type="button" onclick="location.href='<%=request.getContextPath() %>/manager/messages'" >
										<i class="icon-arrow-left"></i>
										返回
									</button>
								</div>
							</form>
							<!--PAGE CONTENT ENDS-->
					</div><!--/.span-->
				</div><!--/.row-fluid-->
			</div><!--/.page-content-->
		</div><!--/.main-content-->
	</div><!--/.main-container-->
	<%@include file="/WEB-INF/views/common/js.jsp" %>
	<script type="text/javascript">
		$(function() {
			$('#add').on('click', function() {

					$("#addclasses").submit();
			});
			
		});
		</script>
	</body>
</html>