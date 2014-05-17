<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="GBK"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>�����ظ�</title>
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
								������Ϣ
							</small>
						</h1>
					</div> 
					<div class="row-fluid">
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							 
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr >
								      <th width="30%">��������</th>
								      <th width="10%">��ϵ��</th>
								      <th width="15%">��ϵ�绰</th>
								      <th width="15%">����ʱ��</th>
								      <th width="15%">Ԥ��ʱ��</th>
								    </tr>
								  </thead>
								  <tbody>
								  		<tr>
								  		<td>${bill.goods}</td>
								  		<td>${bill.name}</td>
								  		<td>${bill.tel}</td>
								  		<td>${bill.date}</td>
								  		<td>${bill.ordertime}</td>
								  		</tr>
								  </tbody>
							</table>
							<form action="addmessage" id="addmessage" method="post">
								<label for="form-field-8">�����ظ�����</label>
								<input type="hidden" value="${bill.fromusername }" name="fromusername" id="formusername" />
								<textarea class="span12" name="content" rows="4" placeholder="�ظ�����"></textarea>
								<br>
								<button class="btn btn-info" id="leavebill" type="button"><i class="icon-inbox"></i>����</button>
								<button class="btn btn-info" onclick="location.href='<%=request.getContextPath() %>/manager/getbills'" type="button"><i class="icon-inbox"></i>����</button>
							</form>
							 
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr >
								      <th width="30%">�ظ�����</th>
								      <th width="15%">����ʱ��</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${list }" var="list">
								  		<tr>
								  		<td>${list.content}</td>
								  		<td>${list.createTime}</td>
								  		</tr>
								  	</c:forEach>
								  </tbody>
							</table>
							<!--PAGE CONTENT ENDS-->
					</div><!--/.span-->
				</div><!--/.row-fluid-->
			</div><!--/.page-content-->
		</div><!--/.main-content-->
	</div><!--/.main-container-->

		<%@include file="/WEB-INF/views/common/js.jsp" %>
		<script type="text/javascript">
		$(function(){
			$('#leavebill').on('click',function(){
				if($.trim($('#content').val())==''){
					alert('������ظ�����');
					return;
				}else{
					$("#addmessage").submit();
				}
			});
		});
		</script>
	</body>
</html>