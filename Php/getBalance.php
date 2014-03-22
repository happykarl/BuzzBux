<?php
	$host="localhost"; //replace with database hostname 
	$username="felicekarl"; //replace with database username 
	$password="opdopd8123"; //replace with database password 
	$db_name="felicekarl_godohosting_com"; //replace with database name
	
	$paramAccountId = rawurldecode($_POST["index"]);
	
	$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
	mysql_select_db("$db_name")or die("cannot select DB");
	
	$sql = "SELECT * FROM `felicekarl_godohosting_com`.`oma_bankaccount` WHERE `oma_bankaccount`.`index`='".$paramAccountId."'";
	$result = mysql_query($sql);
	$balance = 0;
	if(mysql_num_rows($result)){
		while($row=mysql_fetch_assoc($result)){
			$balance = $row['balance'];
		}
		echo $balance;
	} else {
		echo "null";
	}
	
	
?>