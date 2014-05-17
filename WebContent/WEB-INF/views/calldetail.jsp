<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=70ba0acb-1775-4b25-bb4b-9f93db7a2b26&amp;pophcol=3&amp;lang=zh">
</script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/addons/bshareDrag.js?bp=sinaminiblog,bsharesync,qzone,renren,kaixin001&amp;text=%E5%88%86%E4%BA%AB%E5%88%B0"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function(){
	function setBshare(){
		var BSh_pic = '';
		$('.img_wrapper').length>0 && (BSh_pic = $('.img_wrapper > img').attr('src'));
		$('.thumb_contentDiv').length>0 && (BSh_pic = $('.thumb_contentDiv li.currentThumb img').attr('src').replace(/_h60/g,''));
		return BSh_pic;
		};

	bShare.addEntry({
		title:" ",
		summary: $('title').html(),
		pic:setBshare()
	});
});

</script>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>查看回复</title>
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
								查看回复
							</small>
						</h1>
					</div> 
					<div class="row-fluid">
						
						<div class="span12">
							<!--PAGE CONTENT BEGINS-->
							<form action="updatecall" id="addclasses" class="form-horizontal" method="post">
							<input type="hidden" id="id" name="id" value="${call.id }">
								<div class="control-group">
									<label class="control-label" for="id">回复内容</label>
									<div class="controls">
										<textarea id="content" name="content" style="width:90%;height:60px">${call.content }</textarea>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label"  for="name">选择类型</label>
									<div class="controls">
										<select id="type" name="type">
											<option value="welcome">欢迎</option>
											<option value="help">帮助</option>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label"  for="headteacher">是否启用</label>
									<div class="controls">
										<select id="state" name="state">
											<option value="1">启用</option>
											<option value="0">不启用</option>
										</select>*本条启用其他将自动更新为停用状态
									</div>
								</div>
									<div class="form-actions">
									<button class="btn btn-info" id="add" type="button">
										<i class="icon-ok bigger-110"></i>
										保存回复
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn btn-info" type="button" onclick="location.href='<%=request.getContextPath() %>/manager/calls'" >
										<i class="icon-arrow-left"></i>
										返回
									</button>
								</div>
							</form>
							
							<a href="javascript:;" title="QQ空间" onclick="javascript:bShare.share(event,'qzone');return false;" style="background:url(http://static.bshare.cn/frame/images//slogos_sprite8.png) no-repeat 0 -1566px;">QQ空间</a>
							
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
			
			$("#type option[value='${call.type}']").attr("selected", true);
			$("#state option[value='${call.state}']").attr("selected", true);
			
		});
		</script>
		
		
		
		
		
	</body>
</html>