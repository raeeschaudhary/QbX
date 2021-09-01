//var versionen = "1.4";
//var host_name = "http://"+document.domain+"/qbx/"+versionen+"/";
//var email="";
//var password="";
//var gender=null;
//var year=null;
//var month=null;
//var day=null;
//var computer_brand="";
//var computer_model="";
//
//function login() {
//	try{
//		email 	 = document.getElementById("email").value;
//		password = document.getElementById("password").value;
//
//		if(email=="" || password=="")
//			alert("Fill in login information");
//		else
//			window.location.href=host_name+"patient_info.html?email="+email+"&password="+password;
//	}
//	catch(err){
//		alert(err.message);
//	}
//}
//
//function patientInfoSubmit() {
//	try{
//		var length;
//		var radio =document.getElementsByName("gender");
//		year   =document.getElementById("year").value;
//		month  =document.getElementById("month").value;
//		day    =document.getElementById("day").value;
//		computer_brand = document.getElementById("computer_brand").value;
//		computer_model = document.getElementById("computer_model").value;
//
//		for (var i = 0, length = radio.length; i < length; i++) {
//			if (radio[i].checked) {
//				gender = radio[i].value;
//			}
//		}
//
//		if(gender==null || year==null || month==null || day==null || year=="0" || month=="0" || day=="0")
//			alert("Please! Fill in gender and birth date information");
//		else
//			window.location.href=host_name+"patient_instructions.html?"+
//				document.getElementById("hidden_parameters").value+
//				"&gender="+gender+"&year="+year+"&month="+month+"&day="+day+
//				"&computer_brand="+
//				computer_brand+"&computer_model="+computer_model;
//	}
//	catch(err){
//		alert(err.message);
//	}
//}
//
//function patientInstrSubmit() {
//	try{
//		window.location.href=host_name+"before_test.html?"+
//			document.getElementById("hidden_parameters").value;
//	}
//	catch(err){
//		throw Error(err.message);
//	}
//}
//
//function reportPage(sessionid, test_time) {
//    window.location.href=host_name+"php/report_page.php?sessionid="+sessionid+
//        "&test_time="+test_time;
//}
//
//function getReport() {
//    window.location.href=host_name+"index.html";
//}
