<?php
if (isset($_REQUEST)) {
	list($c, $f) = explode('_', $_REQUEST['action']);

	require_once('database.php');
	$conn = get_db_conn();

	require_once($c . '.php');
	eval($f . '($conn);');
}

?>