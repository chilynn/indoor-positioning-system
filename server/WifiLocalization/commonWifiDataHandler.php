<?php
function caculateDistance($positionRow, $ssidList, $array, $matchQuantityCount) {
	$range = - 10;
	$threshold = - 60;
	$sql = "select * from access_point where bssid in (";
	$sql .= implode ( ', ', $ssidList );
	$sql = $sql . ")" . " and position_id = '" . $positionRow ['id'] . "'";
	$res = mysql_query ( $sql );
	$tempPowTotal = 0;
	$matchQuantityCount = 0;
	$counter = 0;
	while ( $apRow = mysql_fetch_array ( $res ) ) {
		foreach ( $array as $key => $value ) {
			if ($value ['bssid'] == $apRow ['bssid']) {
				$counter = $counter + 1;
				if ($value ['rssi'] >= ($apRow ['rssi'] + $range) && $apRow ['rssi'] >= $threshold) {
					$matchQuantityCount = $matchQuantityCount + 1;
				}
				$tempPowTotal = $tempPowTotal + pow ( abs ( $value ['rssi'] - $apRow ['rssi'] ), 2 );
			}
		}
	}
	if($counter!=0){
		$distance = (sqrt ( $tempPowTotal )) / $counter;
	}else{
		$distance = 9999;
	}
	
	return $distance;
}
?>