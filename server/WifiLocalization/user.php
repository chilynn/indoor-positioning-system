<?php
function validate($conn) {
	$loginUserId = - 1;
	if (isset ( $_POST ["username"] ) && isset ( $_POST ["password"] )) {
		$username = $_POST ["username"];
		$password = $_POST ["password"];
		$sql = "SELECT * FROM user WHERE username = '" . $username . "' and password = '" . $password . "'";
		$res = mysql_query ( $sql );
		$row = mysql_fetch_assoc ( $res );
		$num = mysql_num_rows ( $res );
		if ($num == 0) {
		} else {
			$loginUserId = $row ['id'];
		}
	}
	return $loginUserId;
}
function edit($conn) {
	$userId = validate ( $conn );
	if (isset ( $_POST ["nickname"] )) {
		$nickname = $_POST ["nickname"];
		$sql = "UPDATE user SET nickname = '" . $nickname . "' WHERE id ='" . $userId . "'";
		$res = mysql_query ( $sql );
		if ($res) {
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'edit nickname success' 
			) );
		} else {
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'edit nickname error' 
			) );
		}
	}
	if (isset ( $_POST ["signature"] )) {
		$signature = $_POST ["signature"];
		$sql = "UPDATE user SET signature = '" . $signature . "' WHERE id ='" . $userId . "'";
		$res = mysql_query ( $sql );
		if ($res) {
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'edit signature success' 
			) );
		} else {
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'edit signature error' 
			) );
		}
	}
}
function addFriend($conn) {
	include_once 'jpush/JPushClient.php';
	$master_secret = '7ff6f9d119d9eade68be0485';
	$app_key = '4a2df7d2105a4bd84d93e436';
	$platform = '';
	$apnsProduction = false;
	$client = new JPushClient ( $app_key, $master_secret );
	
	if (isset ( $_POST ["type"] )) {
		$type = $_POST ["type"];
		$id = $_POST ["id"];
		if ($type == 1) {
			if (isset ( $_POST ["user_id"] )) {
				$userId = $_POST ["user_id"];
				$sql = "SELECT * FROM user_relationship WHERE ((user_id = '" . $id . "' AND friend_id = '" . $userId . "') or (user_id = '" . $userId . "' AND friend_id = '" . $id . "')) and status ='0'";
				$res = do_query ( $sql, $conn, false );
				$num = mysql_num_rows ( $res );
				if ($num == 0) {
					$sql = "INSERT INTO user_relationship SET user_id = '" . $id . "', friend_id = '" . $userId . "', status ='0'";
					$res = do_query ( $sql, $conn, false );
				} else {
				}
				$extras = array (
						"type" => "user_friend_apply" 
				);
				$params = array (
						"receiver_type" => 3,
						"receiver_value" => "USER_ID_" . $userId,
						"sendno" => 1,
						"send_description" => "",
						"override_msg_id" => "" 
				);
				$msgResult1 = $client->sendNotification ( "application from " . $_POST ['username'], $params, $extras );
				echo json_encode ( array (
						'state' => 1,
						'msg' => 'application has send' 
				) );
			}
		} else {
			if (isset ( $_POST ["relation_id"] )) {
				$relation_id = $_POST ["relation_id"];
				$sql = "UPDATE user_relationship SET status='1' WHERE id = '" . $relation_id . "'";
				$res = do_query ( $sql, $conn, false );
				if ($res) {
					echo json_encode ( array (
							'state' => 1,
							'msg' => 'accept friend success' 
					) );
					$sql = "SELECT * FROM user_relationship WHERE id = '" . $relation_id . "'";
					$res = do_query ( $sql, $conn, false );
					$row = mysql_fetch_assoc ( $res );
					
					$extras = array (
							"type" => "user_friend_accept",
							"relation_id" => $relation_id,
							"friend_id" => $row ['friend_id'] 
					);
					$params = array (
							"receiver_type" => 3,
							"receiver_value" => "USER_ID_" . $row ['user_id'],
							"sendno" => 1,
							"send_description" => "",
							"override_msg_id" => "" 
					);
					$msgResult1 = $client->sendNotification ( "accpet your application", $params, $extras );
				} else {
					echo json_encode ( array (
							'state' => 0,
							'msg' => 'accept error' 
					) );
				}
			}
		}
	}
}
function friendApply($conn) {
	if (isset ( $_POST ["username"] ) && isset ( $_POST ["password"] )) {
		$username = $_POST ["username"];
		$password = $_POST ["password"];
		$sql = "SELECT * FROM user WHERE username = '" . $username . "' and password = '" . $password . "'";
		$res = do_query ( $sql, $conn, false );
		$user = mysql_fetch_assoc ( $res );
		
		$sql = "SELECT * FROM user_relationship WHERE friend_id = '" . $user ['id'] . "' and status = '0'";
		$res = do_query ( $sql, $conn, false );
		$array = array ();
		$num = mysql_num_rows ( $res );
		if ($num == 0) {
			echo json_encode ( $array );
		} else {
			while ( $row = mysql_fetch_assoc ( $res ) ) {
				$sql = "SELECT * FROM user WHERE id = '" . $row ['user_id'] . "'";
				$res = do_query ( $sql, $conn, false );
				$sender = mysql_fetch_assoc ( $res );
				$row ['sender'] = $sender;
				$array [] = $row;
			}
			echo json_encode ( $array );
		}
	}
}
function info($conn) {
	if (isset ( $_POST ["searchId"] )) {
		$searchId = $_POST ["searchId"];
		$sql = "SELECT * FROM user WHERE id = '" . $searchId . "'";
	} else if (isset ( $_POST ["searchName"] )) {
		$searchName = $_POST ["searchName"];
		$sql = "SELECT * FROM user WHERE username = '" . $searchName . "'";
	}
	$res = do_query ( $sql, $conn, false );
	$user = mysql_fetch_assoc ( $res );
	if ($user ['position_id'] != null) {
		$sql = "SELECT * FROM position WHERE id = '" . $user ['position_id'] . "'";
		$res = do_query ( $sql, $conn, false );
		$positionRow = mysql_fetch_assoc ( $res );
		$user ['position'] = $positionRow;
	}
	$isFriend = false;
	$id = $_POST ['id'];
	$sql = "select * from user_relationship WHERE status = '1' and (('" . $id . "' = user_id and '" . $user ['id'] . "' = friend_id ) or (user_id = '" . $user ['id'] . "' and friend_id = '" . $id . "'))";
	$res = do_query ( $sql, $conn, false );
	$row = mysql_fetch_assoc ( $res );
	if ($row != null) {
		$isFriend = true;
	}
	if ($isFriend) {
		$user ['is_friend'] = "1";
	} else {
		$user ['is_friend'] = "0";
	}
	
	close_conn ( $conn );
	echo json_encode ( $user );
}
function search($conn) {
	if (isset ( $_POST ["search"] )) {
		$search = $_POST ["search"];
		$sql = "SELECT * FROM user WHERE username = '" . $search . "'";
		$res = do_query ( $sql, $conn, false );
		$isExist = false;
		$user = mysql_fetch_assoc ( $res );
		if ($user != null) {
			$isExist = true;
			if ($user ['position_id'] != null) {
				$sql = "SELECT * FROM position WHERE id = '" . $user ['position_id'] . "'";
				$res = do_query ( $sql, $conn, false );
				$positionRow = mysql_fetch_assoc ( $res );
				$user ['position'] = $positionRow;
			}
			$isFriend = false;
			$id = $_POST ["id"];
			$sql = "select * from user_relationship WHERE status = '1' and (('" . $id . "' = user_id and '" . $user ['id'] . "' = friend_id ) or (user_id = '" . $user ['id'] . "' and friend_id = '" . $id . "'))";
			$res = do_query ( $sql, $conn, false );
			$row = mysql_fetch_assoc ( $res );
			if ($row != null) {
				$isFriend = true;
			}
			if ($isFriend) {
				$user ['is_friend'] = "1";
			} else {
				$user ['is_friend'] = "0";
			}
			close_conn ( $conn );
			echo json_encode ( array (
					'state' => 1,
					'msg' => 'get user info success',
					'data' => $user 
			) );
		} else {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'get user info fail' 
			) );
		}
	}
}
function friendList($conn) {
	if (isset ( $_POST ["username"] ) && isset ( $_POST ["password"] )) {
		$username = $_POST ["username"];
		$password = $_POST ["password"];
		
		$sql = "SELECT * FROM user WHERE username = '" . $username . "' and password = '" . $password . "'";
		$res = do_query ( $sql, $conn, false );
		$row = mysql_fetch_assoc ( $res );
		$userId = $row ['id'];
		
		$sql = "select user_id,friend_id from  user_relationship t WHERE status = '1' and ('" . $userId . "' = t.user_id or '" . $userId . "' = t.friend_id )";
		$res = do_query ( $sql, $conn, false );
		
		$num = mysql_num_rows ( $res );
		if ($num == 0) {
			echo json_encode ( array (
					'state' => 0,
					'msg' => 'the user has no friend' 
			) );
		} else {
			$userFriendIdArray = array ();
			while ( $row = mysql_fetch_assoc ( $res ) ) {
				if ($row ['user_id'] == $userId) {
					$userFriendIdArray [] = $row ['friend_id'];
				} else {
					$userFriendIdArray [] = $row ['user_id'];
				}
			}
			
			$userFriendArray = array ();
			
			$sql = "select * from user where id in (";
			$sql .= implode ( ', ', $userFriendIdArray );
			$sql = $sql . ")";
			$res = do_query ( $sql, $conn, false );
			while ( $row = mysql_fetch_assoc ( $res ) ) {
				$userFriendArray [] = $row;
			}
			echo json_encode ( $userFriendArray );
		}
		close_conn ( $conn );
	}
}
?>