<?php
require 'user.php';
function add($conn) {
	$loginedUserId = validate ( $conn );
	if ($loginedUserId != - 1) {
		if (isset ( $_POST ["content_id"] ) && isset ( $_POST ["content"] )) {
			$contentId = $_POST ["content_id"];
			$content = $_POST ["content"];
			$sql = "INSERT INTO comment SET user_id = '" . $loginedUserId . "' , content_id = '" . $contentId . "' , content = '" . $content . "' , create_time = '" . time () . "'";
			$res = do_query ( $sql, $conn, false );
			if ($res) {
				echo json_encode ( array (
						'state' => 1,
						'msg' => 'add comment success' 
				) );
			} else {
				echo json_encode ( array (
						'state' => 0,
						'msg' => 'add comment error' 
				) );
			}
		}
	}
}
function get($conn) {
	if (isset ( $_POST ["content_id"] ) && isset ( $_POST ["offset"] )) {
		$contentId = $_POST ["content_id"];
		$offset = $_POST ["offset"];
		$sql = "SELECT * FROM comment WHERE content_id = '" . $contentId . "' ORDER BY create_time LIMIT " . $offset . " , " . ($offset + 5);
		$res = do_query ( $sql, $conn, false );
		$array = array ();
		while ( $row = mysql_fetch_assoc ( $res ) ) {
			$sql = "SELECT * FROM user WHERE id = '" . $row ['user_id'] . "'";
			$userRes = do_query ( $sql, $conn, false );
			$commentor = mysql_fetch_assoc ( $userRes );
			$row ['user'] = $commentor;
			$array [] = $row;
		}
		echo json_encode ( $array );
	}
}
?>