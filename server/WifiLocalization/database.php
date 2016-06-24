<?php
function get_db_conn() {
	$conn = mysql_connect ( 'localhost', 'root', '' );
	
	if (! $conn) {
		die ( 'Could not connect: ' . mysql_error () );
	}
	
	mysql_query ( 'SET NAMES UTF8' );
	
	mysql_select_db ( 'ips', $conn );
	
	return $conn;
}
function do_query($sql, $conn, $auto_close_conn = true) {
	$res = mysql_query ( $sql, $conn );
	if (! $res) {
		die ( 'Error: ' . mysql_error () );
	}
	
	if ($auto_close_conn) {
		mysql_close ( $conn );
	}
	
	return $res;
}
function get_last_id() {
	return mysql_insert_id ();
}
function close_conn($conn) {
	mysql_close ( $conn );
}
?>