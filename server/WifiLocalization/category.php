<?php
function get($conn) {
	$array = array ();
	$sql = "SELECT * FROM category";
	$res = mysql_query ( $sql );
	$num = mysql_num_rows ( $res );
	if ($num == 0) {
	}else{
		while ( $row = mysql_fetch_assoc ( $res ) ) {
			$array [] = $row;
		}
	}
	echo json_encode ( $array );

}
?>