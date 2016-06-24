<?php
function add($conn) {
	if (! file_exists ( "upload" )) {
		mkdir ( "upload" );
	}
	
	$contentId = $_POST ['content_id'];
	$isAllUpload = true;
	foreach ( $_FILES as $field => $field_data ) {
		if ($field_data ["error"] > 0) {
			echo "Return Code: " . $field_data ["error"];
		} else {
			$filePath = "upload/" . md5 ( $field_data ["name"] . time () ) . ".jpg";
			move_uploaded_file ( $field_data ["tmp_name"], $filePath );
			$sql = "INSERT INTO image SET image_url = '" . $filePath . "' , content_id = '" . $contentId . "'";
			$res = do_query ( $sql, $conn, false );
			if (! $res) {
				$isAllUpload = false;
			}
		}
	}
	
	if ($isAllUpload) {
		echo json_encode ( array (
				'state' => 1,
				'msg' => 'upload images success' 
		) );
	} else {
		echo json_encode ( array (
				'state' => 0,
				'msg' => 'upload images error' 
		) );
	}
}
?>
