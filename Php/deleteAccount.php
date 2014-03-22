<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramAccountId = rawurldecode($_POST["index"]);
	
	$con = mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	// delete account
	$sql = "DELETE FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE `oma_bankaccount`.`index` ='".$paramAccountId."'";
	$result = mysql_query($sql);
	
	// delete transaction
	$sql = "DELETE FROM `felicekarl_godohosting_com`.`oma_transaction` WHERE `oma_transaction`.`parentaccount` ='".$paramAccountId."'";
	$result = mysql_query($sql);
	
	if($result == 1) {
		echo "succeed";
	} else {
		echo "failed";
	}
?>