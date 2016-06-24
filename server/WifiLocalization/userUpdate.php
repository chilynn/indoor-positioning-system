<?php
require 'conn.php';
require 'commonDataBase.php';

if ($_FILES ["file"] ["error"] > 0) {
	echo "Return Code: " . $_FILES ["file"] ["error"] . "<br />";
} else {
// 	echo "Upload: " . $_FILES ["file"] ["name"] . "<br />";
// 	echo "Type: " . $_FILES ["file"] ["type"] . "<br />";
// 	echo "Size: " . ($_FILES ["file"] ["size"] / 1024) . " Kb<br />";
// 	echo "Temp file: " . $_FILES ["file"] ["tmp_name"] . "<br />";
	
	$username = $_POST ['username'];
	
	if (file_exists ( "upload/" . $_FILES ["file"] ["name"] )) {
		unlink ( "upload/" . $_FILES ["file"] ["name"] );
// 		echo $_FILES ["file"] ["name"] . " already exists. ";
	}
	
	if (! file_exists ( "upload" )) {
		mkdir ( "upload" );
	}
	move_uploaded_file ( $_FILES ["file"] ["tmp_name"], "upload/" . $_FILES ["file"] ["name"] );
	
	$sql = "UPDATE user SET avatar = '" . "upload/" . $_FILES ["file"] ["name"] . "' WHERE username ='" . $username . "'";
	$res = mysql_query ( $sql );
	getResult ( $res );
	echo json_encode ( array (
			'state' => 1,
			'msg' => 'update success',

	) );
}
?>