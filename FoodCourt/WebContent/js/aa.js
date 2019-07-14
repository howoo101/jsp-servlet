
window.onload = function() {

}

function loadJQuery() {
	var oScript = document.createElement("script");
	oScript.type = "text/javascript";
	oScript.charset = "utf-8";
	oScript.src = "http://code.jquery.com/jquery-1.6.2.min.js";
	document.getElementsByTagName("head")[0].appendChild(oScript);
}
//결제액을 구하는 함수
function result() {
	   var footer = document.getElementById("foot");
	   var tmp ="";
	   var inputlist = document.getElementById('inputlist');
	   var total = 0;
	   var menucheck = document.getElementsByClassName('menucheck');
	   var code = document.getElementsByClassName('code');
	   var totalbody = document.getElementById('total');

	   if (menucheck.length < 1) {      
	      footer.style.visibility = "hidden";
	   }
	   else{
	      footer.style.visibility = "visible";   
	   }
	   for (var i = 0; i < menucheck.length; i++) {
	      total += parseFloat(menucheck[i].children[2].innerText);
	      tmp += "<input type='hidden' name='data' value='"+'\{"itemCode\":'+code[i].value+','+'\"qnt\":'+menucheck[i].children[3].innerText+"}'>";

	   }
	   totalbody.innerHTML = "<h1 id='t'>결제액:" + total + "</h1>";
	   inputlist.innerHTML = tmp;
	   
	   
	}


//주문현황에 새로 추가하는 함수~
function Radd(menuCode,storeCode, menuName, menuPrice) {

	
	var menucheck = document.getElementsByClassName('menucheck');
	var store;
	var menu = menuName;
	var price = menuPrice;
	var Code = menuCode;
	//중복제거를위해 주문현황을 추가하는 동작을 함수로 만들었다.
	function newMenu(){
		var tbody = document.getElementById('tbody');	
		tbody.innerHTML += "<tr class='menucheck'>" + "<th scope='row'>"
		+ store +"<input type='hidden' class='code' value='"+Code+"'>" +"</th>" + "<td >" + menu + "</td>" + "<td>" + price
		+ "</td>" + "<td>1</td>"
		+ "<td><img class='x' src='img/x.png'></td>" + "</tr>";
	}
	//가게코드 받아서 스위치문을이용하여 가게명으로 바꿔준다~
	switch (storeCode) {
	case 1:
		store = "맘스터치";
		break;
	case 2:
		store = "미미네 떡볶이";
		break;
	case 3:
		store = "미소야";
		break;
	case 4:
		store = "북촌손만두";
		break;
	case 5:
		store = "연안식당";
		break;
	case 7:
		store = "포베이";
		break;
	case 8:
		store = "홍콩반점";
		break;
	}

	// 중복이 있을시 메뉴를 추가한다
	if (menucheck.length == 0) {
		newMenu();
		result();
		return;
	}

	for (var i = 0; i < menucheck.length; i++) {
		if (menucheck[i].children[1].innerText == menu) {
			menucheck[i].children[3].innerText++;
			menucheck[i].children[2].innerText = price
					* menucheck[i].children[3].innerText;
			result();
			return;
		}
	}
	// 중복이 없으면 새로 만들어준다
	newMenu();
	result();

	//취소이미지에 기능부여
	$('#tbody').on('click', 'td img', function(e) {
		e.preventDefault();

		$((this.parentElement).parentElement).remove();
		result();
	});

}
