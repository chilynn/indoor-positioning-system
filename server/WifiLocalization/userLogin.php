<?php
require 'conn.php';
require 'commonDataBase.php';

$extras = array ();
$params = array (
		"receiver_type" => 4,
		"receiver_value" => "",
		"sendno" => 1,
		"send_description" => "",
		"override_msg_id" => "" 
);
if (isset ( $_POST ["username"] ) && isset ( $_POST ["password"] )) {
	$username = $_POST ["username"];
	$password = $_POST ["password"];
	$sql = "SELECT * FROM user WHERE username = '" . $username . "' and password = '" . $password . "'";
	$res = mysql_query ( $sql );
	$row = mysql_fetch_assoc ( $res );
	$num = mysql_num_rows ( $res );
	if ($num == 0) {
		echo json_encode ( array (
				'state' => 0,
				'msg' => 'username or password error' 
		) );
	} else {
		echo json_encode ( array (
				'state' => 1,
				'msg' => 'login sucess',
				'data' => $row 
		) );
	}
}

?>
