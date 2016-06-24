<?php
function shortest($conn) {
	if (isset ( $_POST ["start_id"] ) && isset ( $_POST ["end_id"] )) {
		$startId = $_POST ["start_id"];
		$endId = $_POST ["end_id"];
		$graph_array = getGraph ( $conn );
		if (count ( $graph_array ) == 0) {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'database does not have edge' 
			) );
		} else {
			$path = dijkstra ( $graph_array, $startId, $endId );
			$positionArray = array ();
			foreach ( $path as $p ) {
				$positionArray [] = getPosition ( $conn, $p );
			}
			echo json_encode ( array (
					'state' => 1,
					'msg' => "path is: " . implode ( ", ", $path ),
					'data' => $positionArray 
			) );
		}
	}
}
function getPosition($conn, $id) {
	$sql = "SELECT * FROM position where id ='" . $id . "'";
	$res = do_query ( $sql, $conn, false );
	$row = mysql_fetch_assoc ( $res );
	return $row;
}
function getGraph($conn) {
	$graph_array = array ();
	$sql = "SELECT * FROM position_neighbour";
	$res = do_query ( $sql, $conn, false );
	while ( $row = mysql_fetch_assoc ( $res ) ) {
		$graph_array [] = array (
				$row ['position_id'],
				$row ['neighbour_id'],
				$row ['distance'] 
		);
	}
	return $graph_array;
}
function dijkstra($graph_array, $source, $target) {
	$vertices = array ();
	$neighbours = array ();
	foreach ( $graph_array as $edge ) {
		array_push ( $vertices, $edge [0], $edge [1] );
		$neighbours [$edge [0]] [] = array (
				"end" => $edge [1],
				"cost" => $edge [2] 
		);
	}
	$vertices = array_unique ( $vertices );
	
	foreach ( $vertices as $vertex ) {
		$dist [$vertex] = INF;
		$previous [$vertex] = NULL;
	}
	
	$dist [$source] = 0;
	$Q = $vertices;
	while ( count ( $Q ) > 0 ) {
		// TODO - Find faster way to get minimum
		$min = INF;
		foreach ( $Q as $vertex ) {
			if ($dist [$vertex] < $min) {
				$min = $dist [$vertex];
				$u = $vertex;
			}
		}
		$Q = array_diff ( $Q, array (
				$u 
		) );
		if ($dist [$u] == INF or $u == $target) {
			break;
		}
		
		if (isset ( $neighbours [$u] )) {
			foreach ( $neighbours [$u] as $arr ) {
				$alt = $dist [$u] + $arr ["cost"];
				if ($alt < $dist [$arr ["end"]]) {
					$dist [$arr ["end"]] = $alt;
					$previous [$arr ["end"]] = $u;
				}
			}
		}
	}
	$path = array ();
	$u = $target;
	while ( isset ( $previous [$u] ) ) {
		array_unshift ( $path, $u );
		$u = $previous [$u];
	}
	array_unshift ( $path, $u );
	return $path;
}
$graph_array = array (
		array (
				"a",
				"b",
				6 
		),
		array (
				"a",
				"c",
				3 
		),
		array (
				"b",
				"c",
				2 
		),
		array (
				"b",
				"d",
				5 
		),
		array (
				"c",
				"d",
				3 
		),
		array (
				"c",
				"e",
				4 
		),
		array (
				"d",
				"e",
				2 
		),
		array (
				"d",
				"f",
				3 
		),
		array (
				"e",
				"f",
				5 
		) 
);

// $graph_array = getGraph ( $conn );
// print_r ( $graph_array );
// $path = dijkstra ( $graph_array, 181, 182 );
// $path = dijkstra ( $graph_array, "a", "e" );

//  echo "path is: " . implode ( ", ", $path ) . "\n";
?>