<?php
require 'conn.php';
require 'database.php';
require 'commonDataBase.php';

if (isset ( $_POST ["name"] ) && isset ( $_POST ["description"] ) && isset ( $_POST ["position_id"] )) {
	$name = $_POST ["name"];
	$description = $_POST ["description"];
	$positionId = $_POST ["position_id"];
	
	$sqlQuery = "SELECT * FROM node WHERE position_id = '" . $positionId . "'";
	$res = mysql_query ( $sqlQuery );
	getResult ( $res );
	$isExist = false;
	$existPositionId;
	while ( $row = mysql_fetch_array ( $res ) ) {
		if ($row != null) {
			$existPositionId = $row ['id'];
			$isExist = true;
		} else {
		}
	}
	if (! $isExist) {
		if (isset ( $_POST ["category_id"] )) {
			$categoryId = $_POST ["category_id"];
			$sql = "insert into node set name = '" . $name . "' , description = '" . $description . "' , position_id ='" . $positionId . "' , category_id ='" . $categoryId . "'";
		} else {
			$sql = "insert into node set name = '" . $name . "' , description = '" . $description . "' , position_id ='" . $positionId . "'";
		}
		$res = mysql_query ( $sql );
		getResult ( $res );
		$nodeId = get_last_id ();
		if ($res) {
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'add node success',
					'id' => $nodeId 
			) );
		} else {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'add node error' 
			) );
		}
	} else {
		echo json_encode ( array (
				'state' => 0,
				'msg' => 'node is edited' 
		) );
	}
}

?>