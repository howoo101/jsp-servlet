<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import=" java.util.*"%>

<%
	/* 	 Map map = (Map)request.getAttribute("map");
	Object json = request.getAttribute("json");
			 if(map == null) response.sendRedirect("menuAction");
			String url = request.getParameter("url");
			
			List list = (List)request.getAttribute("list");
		 */
%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<style>
.col-sm img {
	width: 100%;
}

.col-sm p {
	text-align: center;
	font-weight: bold;
}

.col-md-3 img {
	width: 80%;
}

.logolist {
	margin-bottom: 100px;
	border-bottom: 10px solid black;
}

.logolist .row:nth-child(2) {
	margin-bottom: 80px;
}

.x {
	width: 10px;
}

#submit {
	margin-right: 30px;
	font-size: 50px;
	float: right;
}

#main {
	font-size: 40px;
}

/* footer {
	visibility: hidden;
} */
</style>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>


<script type="text/javascript">
	{

	}
</script>
</head>

<body>
	<a id="ab" href="login.jsp">관리자</a>
	<!-- header -->
	<h1>
		<button onclick="a(0)">전체메뉴</button>
	</h1>
	<header class="logolist">
		<div class="row">
			<div class="col-6 col-md-3 logo" onclick="a(1)">
				<img src="img/logo1.png" alt="">
			</div>
			<div class="col-6 col-md-3 logo" onclick="a(2)">
				<img src="img/logo2.png" alt="">
			</div>
			<div class="col-6 col-md-3 logo" onclick="a(3)">
				<img src="img/logo3.png" alt="">
			</div>
			<div class="col-6 col-md-3 logo" onclick="a(4)">
				<img src="img/logo4.png" alt="">
			</div>
		</div>
		<div class="row">
			<div class="col-6 col-md-3 logo" onclick="a(5)">
				<img src="img/logo5.png" alt="">
			</div>
			<div class="col-6 col-md-3 logo" onclick="a(6)">
				<img src="img/random.png" alt="">
			</div>
			<div class="col-6 col-md-3 logo" onclick="a(7)">
				<img src="img/logo7.png" alt="">
			</div>
			<div class="col-6 col-md-3 logo" onclick="a(8)">
				<img src="img/logo8.png" alt="">
			</div>
		</div>
	</header>

	<!--/header  -->
	<div class="container">
		<section>
			<div class="row" id="menuList"></div>
		</section>
	</div>
	<div id="menu"></div>
	<footer id="foot">
		<div id="p">
			<table class="table">

				<thead>
					<tr align="center">
						<td id="main" colspan="5" span style="color: black">주문현황</td>
					</tr>
					<tr>
						<th scope="col">가게명</th>
						<th scope="col">메뉴명</th>
						<th scope="col">가격</th>
						<th scope="col">수량</th>
						<th scope="col">취소</th>

					</tr>
				</thead>

				<tbody id="tbody">


				</tbody>
			</table>
		</div>
		<div id="total">
			<h1 id='t'>결제액:0</h1>
		</div>
		<form action="DbSendController" id="form" method="post">
			<div id="inputlist"></div>

			<div id="submit">
				<input type="submit" value="결제">
			</div>
		</form>
	</footer>
</body>
<script type="text/javascript" src="js/aa.js"></script>
<script>
	function a(url) {
		//var href="menuAction?url="+url;
		//alert('ajax 호출')
		$.ajax({
			type : "GET",
			url : "MenuAajaxController",
			data : {
				url : url
			},
			contentType : 'application/json; charset=utf-8',
			dataType : "json",
			success : function(data, status, xhr) {
				//alert('성공')
				console.log(data);
				var arry = data.values;

				// 자 여기서 43개를 섞은뒤 짜르자구
				// 자바스크립트에는 배열을 섞는 함수가 있다
				if (data.storeCode == 6) {
					let tmp = arry[Math.floor(Math.random() * arry.length)]; // 임의의 배열방 선택
					arry = []; // 배열객체를 없애고
					arry[0] = tmp // 새로운 배열객체를 만든뒤 0번방에 임시로 빼온 요소를 넣는다
				}

				var tags = '<div class="row">'

				for (let i = 0; i < arry.length; i++) {
					var checkUrl = ''

					if (arry[i].storeCode == 6) {
						checkUrl += 'javascript:a(6);'
					}

					var src = ''
					if (arry.length - i >= 3)
						src = '<div class="col-sm">'
					else
						src = '<div class="col-sm-4">'
					tags += src + '<img src="' + arry[i].uri
							+ '" alt="" onclick="Radd(' + arry[i].menuCode
							+ ',' + arry[i].storeCode + ',' + "'"
							+ arry[i].menuName + "'" + ',' + arry[i].menuPrice
							+ ')">' + '<p>' + arry[i].menuName + '</p>' + '<p>'
							+ arry[i].menuPrice + '</p>' + '</div>'
					if (i != 0 && i % 3 == 2) {
						tags += '</div>';

						if (i != arry.length - 1) {
							tags += '<div class="row">'
						}
					}
				}

				$('#menu').html(tags);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				//console.log(jqXHR.responseText); 
				alert('실패');
			}

		});

	}

	/* var c = '${json }'
	console.log(c);
	var json = $.parseJSON(c);
	console.log(json); */
</script>
</html>