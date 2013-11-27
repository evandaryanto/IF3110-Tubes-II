var xmlhttp;
if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
	xmlhttp=new XMLHttpRequest();
} else {// code for IE6, IE5
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
function login() {	
	var parameters = "Login[username]=" + encodeURI( document.getElementById("login_username").value ) + "&Login[password]=" + encodeURI( document.getElementById("login_password").value );
	xmlhttp.onreadystatechange=function() {
		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			var data = JSON.parse(xmlhttp.responseText);
			if (data.success) {
				if (data.admin) {
					document.getElementById('loading').innerHTML = "Anda berhasil login sebagai admin";
					window.location = "http://localhost/phpmyadmin";
				}
				document.getElementById('loading').innerHTML = "Anda berhasil login";
				window.location = server+"/home";
			} else {
				document.getElementById('loading').innerHTML = "Username atau password salah";
				// alert('Username atau password salah');
			}
			document.getElementById('loading').classList.remove('loading');
		}
	}
	document.getElementById('loading').innerHTML = "";
	document.getElementById('loading').classList.add('loading');
	xmlhttp.open("POST",server+"/api/login",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	// xmlhttp.setRequestHeader("Content-length", parameters.length);
	// xmlhttp.setRequestHeader("Connection", "close");
	xmlhttp.send(parameters);
}