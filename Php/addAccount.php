<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramUsername = rawurldecode($_POST["username"]);
	$paramName = rawurldecode($_POST["name"]);
	$paramDescription = rawurldecode($_POST["description"]);
	
	$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	// check the same account is exist
	$sql = "SELECT id, name, description FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE parentuser='".$paramUsername."' AND name='".$paramName."'";
	$result = mysql_query($sql);
	if(mysql_num_rows($result)){
		echo "account is already exist.";
	} else {
		// add new account
		$sql = "INSERT INTO `felicekarl_godohosting_com`.`oma_bankaccount` (`id`, `name`, `parentuser`, `description`) 
					VALUES (NULL, '".$paramName."', '".$paramUsername."', '".$paramDescription."')";
		$result = mysql_query($sql);
		
		// return result
		$sql = "SELECT * FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE parentuser='".$paramUsername."' AND name='".$paramName."'";
		$result = mysql_query($sql);
		$json = array();
		
		if(mysql_num_rows($result)){
			while($row=mysql_fetch_assoc($result)){
				$json['oma_bankaccount'][]=$row;
			}
		}
		mysql_close($con);
		echo json_encode($json); 
	}
?>