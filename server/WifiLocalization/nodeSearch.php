<?php
require 'conn.php';
require 'commonDataBase.php';

if (isset ( $_POST ["content"] )) {
	$content = $_POST ["content"];
	$sqlQuery = "SELECT node.* FROM node,category WHERE (node.category_id = category.id) AND (node.name like '%" . $content . "%' or node.description like '%" . $content . "%' or category.name like '%" . $content . "%' or category.description like '%" . $content . "%')";
	$res = mysql_query ( $sqlQuery );
	getResult ( $res );
	
	$array = array ();
	$i = 0;
	while ( $row = mysql_fetch_assoc ( $res ) ) {
		$sql = "SELECT * FROM position WHERE id ='" . $row ['position_id'] . "'";
		$innerRes = mysql_query ( $sql );
		getResult ( $innerRes );
		$innerRow = mysql_fetch_assoc ( $innerRes );
		$row ['position'] = $innerRow;
		$array [$i] = $row;
		$i ++;
	}
	echo json_encode ( $array );
}

?>