<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramTransactionId = rawurldecode($_POST["index"]);
	$paramAccountId = rawurldecode($_POST["sign"]);
	$paramDiff = rawurldecode($_POST["diff"]);
	
	$con = mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	// delete transaction
	$sql = "DELETE FROM `felicekarl_godohosting_com`.`oma_transaction` WHERE `oma_transaction`.`index` ='".$paramTransactionId."'";
	$result = mysql_query($sql);
	
	// get balance
	$sql = "SELECT * FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE `oma_bankaccount`.`index`='".$paramAccountId."'";
	$result = mysql_query($sql);
	
	$balance = 0;
	if(mysql_num_rows($result)){
		while($row=mysql_fetch_assoc($result)){
			$balance = $row['balance'];
		}
	}
	$balance += $paramDiff;
	
	// update balance
	$sql = "UPDATE `felicekarl_godohosting_com`.`oma_bankaccount` SET `balance`='".$balance."' WHERE `oma_bankaccount`.`index` ='".$paramAccountId."'";
	$result = mysql_query($sql);
	
	if($result == 1) {
		echo $balance;
	} else {
		echo "null";
	}
	
	
?>