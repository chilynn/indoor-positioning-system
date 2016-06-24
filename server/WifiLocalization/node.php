<?php
function search($conn) {
	if (isset ( $_POST ["content"] )) {
		$content = $_POST ["content"];
		$sql = "SELECT node.* FROM node,category WHERE (node.category_id = category.id) AND (node.name like '%" . $content . "%' or node.description like '%" . $content . "%' or category.name like '%" . $content . "%' or category.description like '%" . $content . "%')";
		$res = do_query ( $sql, $conn, false );
		
		$array = array ();
		$i = 0;
		while ( $row = mysql_fetch_assoc ( $res ) ) {
			$sql = "SELECT * FROM position WHERE id ='" . $row ['position_id'] . "'";
			$innerRes = mysql_query ( $sql );
			$innerRow = mysql_fetch_assoc ( $innerRes );
			$row ['position'] = $innerRow;
			$row ['images'] = getImages ( $conn, $row ['id'] );
			$array [$i] = $row;
			$i ++;
		}
		echo json_encode ( $array );
	}
}
function detail($conn) {
	if (isset ( $_POST ["node_id"] )) {
		$nodeId = $_POST ["node_id"];
		$sql = "SELECT * FROM node where id = '" . $nodeId . "'";
		$res = do_query ( $sql, $conn, false );
		$node = mysql_fetch_assoc ( $res );
		
		$sql = "SELECT * FROM image where content_id = '" . $nodeId . "'";
		$res = do_query ( $sql, $conn, false );
		$imageArray = array ();
		while ( $row = mysql_fetch_assoc ( $res ) ) {
			$imageArray [] = $row;
		}
		$node ['images'] = $imageArray;
		echo json_encode ( $node );
	}
}
function getImages($conn, $nodeId) {
	$sql = "SELECT * FROM image where content_id = '" . $nodeId . "'";
	$res = do_query ( $sql, $conn, false );
	$imageArray = array ();
	while ( $row = mysql_fetch_assoc ( $res ) ) {
		$imageArray [] = $row;
	}
	return $imageArray;
}
?>