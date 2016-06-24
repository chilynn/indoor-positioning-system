<?php
require 'conn.php';
require 'commonDataBase.php';
require 'database.php';
require 'commonWifiDataHandler.php';
require 'node.php';

include_once 'jpush/JPushClient.php';
$master_secret = '7ff6f9d119d9eade68be0485';
$app_key = '4a2df7d2105a4bd84d93e436';
$platform = '';
$apnsProduction = false;

$client = new JPushClient ( $app_key, $master_secret );

$isLogin = false;

if (isset ( $_POST ["id"] )) {
	$id = $_POST ['id'];
	$isLogin = true;
	$params = array (
			"receiver_type" => 3,
			"receiver_value" => "PUSH_LOCATION_OF_" . $id,
			"sendno" => 1,
			"send_description" => "",
			"override_msg_id" => "" 
	);
	
	$params2 = array (
			"receiver_type" => 3,
			"receiver_value" => "USER_ID_" . $id,
			"sendno" => 1,
			"send_description" => "",
			"override_msg_id" => "" 
	);
}

if (isset ( $_POST ["access_point"] )) {
	$referenceInfoArray = array ();
	$minDivation = 9999;
	$minWeight = 9999;
	$minWeightPositionId = 'none';
	$positionIdCursor = 'none';
	$array = json_decode ( $_POST ["access_point"], true );
	
	$ssidList = array ();
	foreach ( $array as $key => $value ) {
		$ssidList [] = "'" . $value ['bssid'] . "'";
	}
	
	$sqlAllPosition = "SELECT * FROM position";
	$resAllPosition = mysql_query ( $sqlAllPosition );
	$positionNum = mysql_num_rows ( $resAllPosition );
	if ($positionNum == 0) {
		echo json_encode ( array (
				'state' => 0,
				'msg' => 'no record' 
		) );
	} else {
		while ( $positionRow = mysql_fetch_array ( $resAllPosition ) ) {
			
			$matchQuantityCount = 0;
			$distance = caculateDistance ( $positionRow, $ssidList, $array, $matchQuantityCount );
			
			// $range = - 10;
			// $threshold = - 60;
			
			// $sql = "select * from access_point where bssid in (";
			// $sql .= implode ( ', ', $ssidList );
			// $sql = $sql . ")" . " and position_id = '" . $positionRow ['id'] . "'";
			// $res = mysql_query ( $sql );
			// $tempPowTotal = 0;
			// $counter = 0;
			// while ( $apRow = mysql_fetch_array ( $res ) ) {
			// foreach ( $array as $key => $value ) {
			// if ($value ['bssid'] == $apRow ['bssid']) {
			// $counter = $counter + 1;
			// if ($value ['rssi'] >= ($apRow ['rssi'] + $range) && $apRow ['rssi'] >= $threshold) {
			// $matchQuantityCount = $matchQuantityCount + 1;
			// }
			// $tempPowTotal = $tempPowTotal + pow ( abs ( $value ['rssi'] - $apRow ['rssi'] ), 2 );
			// }
			// }
			// }
			// $distance = (sqrt ( $tempPowTotal )) / $counter;
			// $tempWeight = $distance;
			$tempWeight = $distance - $matchQuantityCount;
			$referenceInfoArray [] = array (
					"id" => $positionRow ['id'],
					"map_x" => $positionRow ['map_x'],
					"map_y" => $positionRow ['map_y'],
					"matchCount" => $matchQuantityCount,
					"distance" => $distance,
					"weight" => $distance - $matchQuantityCount 
			);
			
			if ($tempWeight < $minWeight) {
				$minWeight = $tempWeight;
				$minWeightPositionId = $positionRow ['id'];
			}
			
			if ($distance < $minDivation) {
				$minDivation = $distance;
				$positionIdCursor = $positionRow ['id'];
			}
		}
		
		$sql = "SELECT * FROM position WHERE id = '" . $minWeightPositionId . "'";
		$res = mysql_query ( $sql );
		getResult ( $res );
		
		$isLocationChange = false;
		
		if (isset ( $_POST ["username"] ) && $_POST ["password"]) {
			$username = $_POST ["username"];
			$password = $_POST ["password"];
			
			$sql = "SELECT * FROM user WHERE username = '" . $username . "' and password ='" . $password . "'";
			$userRes = mysql_query ( $sql );
			$user = mysql_fetch_assoc ( $userRes );
			if ($user ['position_id'] != null) {
				if ($user ['position_id'] == $minWeightPositionId) {
					$isLocationChange = false;
				} else {
					$isLocationChange = true;
				}
			} else {
				$isLocationChange = true;
			}
			$sql = "UPDATE user SET position_id = '" . $minWeightPositionId . "' WHERE username = '" . $username . "' and password ='" . $password . "'";
			mysql_query ( $sql );
		} else {
		}
		
		$singlePositionRow = mysql_fetch_array ( $res );
		$map_x = $singlePositionRow ['map_x'];
		$map_y = $singlePositionRow ['map_y'];
		$aheadDegree = $singlePositionRow ['ahead_degree'];
		if ($positionIdCursor != 'none') {
			$referenceInfo = knn ( $referenceInfoArray );
			$isQualify = $referenceInfo ['isQualify'];
			if ($isQualify == 1) {
				$map_x = $referenceInfo ['map_x'];
				$map_y = $referenceInfo ['map_y'];
			}
			// echo json_encode ( $referenceInfoArray );
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'success:' . $minDivation,
					'data' => array (
							'id' => $minWeightPositionId,
							'map_x' => $map_x,
							'map_y' => $map_y,
							'ahead_degree' => $aheadDegree 
					) 
			) );
			if ($isLocationChange) {
				$sql = "SELECT * FROM node WHERE position_id = '" . $minWeightPositionId . "'";
				$nodeRes = mysql_query ( $sql );
				$num = mysql_num_rows ( $nodeRes );
				if ($num != 0) {
					$node = mysql_fetch_assoc ( $nodeRes );
					$node ['images'] = getImages ( $conn, $node ['id'] );
					$node ['visitTime'] = time ();
					
					$sql = "SELECT * FROM position WHERE id = '" . $node['position_id'] . "'";
					$nodePositionRes = mysql_query ( $sql );
					$nodePosition = mysql_fetch_assoc ( $nodePositionRes );
					
					$node ['position'] = $nodePosition;
					
					$extras2 = array (
							'type' => "node",
							'node' => $node 
					);
					$msgResult2 = $client->sendCustomMessage ( "JPUSH-Node", "node", $params2, $extras2 );
				}
			}
			$extras = array (
					'type' => "position",
					'position' => $singlePositionRow 
			);
			if ($isLogin) {
				$msgResult = $client->sendCustomMessage ( "JPUSH-User Location", $map_x . "-" . $map_y, $params, $extras );
			}
		} else {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'no position match' 
			) );
		}
	}
}
function knn($referenceInfoArray) {
	$diviationThreshold = 0.2;
	$referencePositionNum = count ( $referenceInfoArray );
	$referencesInfo = array ();
	$referencesInfo ['isQualify'] = 0;
	if ($referencePositionNum >= 2) {
		$sortArray = array ();
		foreach ( $referenceInfoArray as $referenceItem ) {
			foreach ( $referenceItem as $key => $value ) {
				if (! isset ( $sortArray [$key] )) {
					$sortArray [$key] = array ();
				}
				$sortArray [$key] [] = $value;
			}
		}
		$orderby = "weight"; // change this to whatever key you want from the array
		array_multisort ( $sortArray [$orderby], SORT_ASC, $referenceInfoArray );
		$referencePositionDistance = $referenceInfoArray [0] ['distance'];
		$candidateReferencePositionDistance = $referenceInfoArray [1] ['distance'];
		if (abs ( $referencePositionDistance - $candidateReferencePositionDistance ) < $diviationThreshold) {
			$referencesInfo ['isQualify'] = 1;
			$averageMapX = ($referenceInfoArray [0] ['map_x'] + $referenceInfoArray [1] ['map_x']) / 2;
			$averageMapY = ($referenceInfoArray [0] ['map_y'] + $referenceInfoArray [1] ['map_y']) / 2;
			$referencesInfo ['firstId'] = $referenceInfoArray [0] ['id'];
			$referencesInfo ['secondId'] = $referenceInfoArray [1] ['id'];
			$referencesInfo ['map_x'] = $averageMapX;
			$referencesInfo ['map_y'] = $averageMapY;
		}
	}
	return $referencesInfo;
}
function getAllPositionId() {
	$sql = "SELECT distinct id FROM position";
	$res = mysql_query ( $sql );
	getResult ( $res );
	return $res;
}
function getPositionById($id) {
	$sql = "SELECT * FROM position WHERE id = '" . $id . "'";
	$res = mysql_query ( $sql );
	getResult ( $res );
	return $res;
}

?>