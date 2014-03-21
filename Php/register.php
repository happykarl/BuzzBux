<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramUsername = rawurldecode($_POST["username"]);
	$paramPassword = rawurldecode($_POST["password"]);
	$paramFirstname = rawurldecode($_POST["firstname"]);
	$paramLastname = rawurldecode($_POST["lastname"]);
	
	$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	$sql = "SELECT username FROM oma_account WHERE username='".$paramUsername."'";
	$result = mysql_query($sql);
	if(mysql_num_rows($result)){
		echo "username is already exist.";
	} else {
		$sql = "INSERT INTO `felicekarl_godohosting_com`.`oma_account` (`index`, `username`, `password`, `firstname`, `lastname`) 
				VALUES (NULL, '".$paramUsername."', '".$paramPassword."', '".$paramFirstname."', '".$paramLastname."')";
		$result = mysql_query($sql);
		
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
		
	}
	
	
	
?>