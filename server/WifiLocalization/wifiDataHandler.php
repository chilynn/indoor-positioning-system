<?php
require 'conn.php';
require 'database.php';
require 'commonDataBase.php';
require 'commonWifiDataHandler.php';

if (isset ( $_POST ["access_point"] )) {
	$array = json_decode ( $_POST ["access_point"], true );
	
	if (isset ( $_POST ["map_x"] ) && isset ( $_POST ["map_y"] )) {
		$x = $_POST ["map_x"];
		$y = $_POST ["map_y"];
		
		if (isset ( $_POST ["ahead_degree"] )) {
			$aheadDegree = $_POST ["ahead_degree"];
		} else {
			$aheadDegree = 265;
		}
		
		$sqlQuery = "SELECT * FROM position WHERE map_x = '" . $x . "' and map_y = '" . $y . "' and ahead_degree = '" . $aheadDegree . "'";
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
			$sql = "INSERT INTO position SET map_x = '" . $x . "' , map_y = '" . $y . "'";
			$res = mysql_query ( $sql );
			getResult ( $res );
			
			$positionId = get_last_id ();
			
			foreach ( $array as $k => $v ) {
				$sql_array = array ();
				foreach ( $v as $kk => &$vv ) {
					if ($kk == 'position_id') {
						$vv = $positionId;
					} else {
					}
					$sql_array [] = $kk . "='" . $vv . "'";
				}
				$sql = "INSERT INTO access_point SET ";
				$sql .= implode ( ', ', $sql_array );
				$res = mysql_query ( $sql );
				getResult ( $res );
			}
			
			//insertEdge($array,$positionId);
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'upload success',
					'id' => $positionId 
			) );
		} else {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'the position exsit',
					'id' => $existPositionId 
			) );
		}
	}
} else {
}
function insertEdge($array, $positionId) {
	$ssidList = array ();
	foreach ( $array as $key => $value ) {
		$ssidList [] = "'" . $value ['bssid'] . "'";
	}
	$sqlAllPosition = "SELECT * FROM position";
	$resAllPosition = mysql_query ( $sqlAllPosition );
	$positionNum = mysql_num_rows ( $resAllPosition );
	if ($positionNum != 0) {
		while ( $positionRow = mysql_fetch_array ( $resAllPosition ) ) {
			$otherPositionId = $positionRow ['id'];
			if ($positionId != $otherPositionId) {
				$matchQuantityCount = 0;
				$distance = caculateDistance ( $positionRow, $ssidList, $array, $matchQuantityCount );
				$tempWeight = $distance - $matchQuantityCount;
				$sql = "INSERT INTO position_neighbour SET position_id = '" . $positionId . "' , neighbour_id = '" . $otherPositionId . "' , distance ='".$tempWeight."'";
				$res = mysql_query ( $sql );
				getResult ( $res );
			}
		}
	}
}
?>
