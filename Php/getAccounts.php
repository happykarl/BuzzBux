<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramUsername = rawurldecode($_POST["username"]);
	
	$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	$sql = "SELECT * FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE parentuser='".$paramUsername."' ORDER BY `oma_bankaccount`.`name` ASC";
	$result = mysql_query($sql);
	$json = array();
	 
	if(mysql_num_rows($result)){
		while($row=mysql_fetch_assoc($result)){
			$json['oma_account'][]=$row;
		}
		mysql_close($con);
		echo json_encode($json); 
	} else {
		echo "null";
	}
	
	
	
	
?>