<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramUsername = rawurldecode($_POST["username"]);
	$paramPassword = rawurldecode($_POST["password"]);
	
	$con=mysql_connect("$host", "$username", "$password")or die("cannot connect"); 
	mysql_select_db("$db_name")or die("cannot select DB");
	$sql = "SELECT username, firstname, lastname FROM oma_account WHERE username='".$paramUsername."' AND password='".$paramPassword."'";
	$result = mysql_query($sql);
	$json = array();
	 
	if (mysql_num_rows($result)) {
		while($row=mysql_fetch_assoc($result)){
			$json['oma_user'][]=$row;
		}
		mysql_close($con);
		echo json_encode($json);
	} else {
		echo "null";
	}
	 
?>