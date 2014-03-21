<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramAccountId = rawurldecode($_POST["accountid"]);
	$paramType = rawurldecode($_POST["type"]);
	$paramAmount = rawurldecode($_POST["amount"]);
	$paramDescription = rawurldecode($_POST["description"]);

	
	
	$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	// add new account
	$sql = "INSERT INTO `felicekarl_godohosting_com`.`oma_transaction` (`index`, `bankaccountid`, `type`, `amount`, `description`) 
				VALUES (NULL, '".$paramAccountId."', '".$paramType."', '".$paramAmount."', '".$paramDescription."')";
	$result = mysql_query($sql);

	// balance
	$sql = "SELECT * FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE id='".$paramAccountId."'";
	$result = mysql_query($sql);
	$balance = 0;
	if(mysql_num_rows($result)){
		while($row=mysql_fetch_assoc($result)){
			$balance = $row['balance'];
		}
	}
	if ($paramType == "0") {
		$balance += $paramAmount;
	} else if ($paramType == "1") {
		$balance -= $paramAmount;
	}
	
	// update balance
	$sql = "UPDATE `felicekarl_godohosting_com`.`oma_bankaccount` SET `balance`='".$balance."' WHERE `oma_bankaccount`.`id` ='".$paramAccountId."'";
	$result = mysql_query($sql);
	
	if($result == 1) {
		// return result
		$sql = "SELECT * FROM `felicekarl_godohosting_com`.`oma_transaction` WHERE bankaccountid='".$paramAccountId."' AND type='".$paramType
				."' AND amount='".$paramAmount."' AND description='".$paramDescription."'";
		$result = mysql_query($sql);
		$json = array();
		
		if(mysql_num_rows($result)){
			while($row=mysql_fetch_assoc($result)){
				$json['oma_transaction'][]=$row;
			}
		}
		mysql_close($con);
		echo json_encode($json);
	} else {
		echo $result;
	}

	
?>