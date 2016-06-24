<?php
require 'conn.php';
require 'commonDataBase.php';

if (isset ( $_POST ["username"] ) && isset ( $_POST ["password"] )) {
	$username = $_POST ["username"];
	$password = $_POST ["password"];
	$sql = "SELECT * FROM user WHERE username = '" . $username . "'";
	$res = mysql_query ( $sql );
	$num = mysql_num_rows ( $res );
	if ($num == 0) {
		$sql = "insert into user set username = '" . $username . "' , password = '" . $password . "'";
		$res = mysql_query ( $sql );
		getResult($res);
		if ($res) {
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'register success' 
			) );
		} else {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'insert error' 
			) );
		}
	} else {
		echo json_encode ( array (
				'state' => 0,
				'msg' => 'the user already exist' 
		) );
	}
}
?>