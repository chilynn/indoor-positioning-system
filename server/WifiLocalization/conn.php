<?php

$mysql_server_name = "localhost"; // 数据库服务器名称
$mysql_username = "root"; // 连接数据库用户名
$mysql_password = ""; // 连接数据库密码
$mysql_database = "ips"; // 数据库的名字

$conn = mysql_connect ( $mysql_server_name, $mysql_username, $mysql_password );
mysql_select_db ( $mysql_database, $conn );
mysql_query ( "set names 'utf8'" );
?>