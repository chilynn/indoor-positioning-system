<?php

$mysql_server_name = "localhost"; // ���ݿ����������
$mysql_username = "root"; // �������ݿ��û���
$mysql_password = ""; // �������ݿ�����
$mysql_database = "ips"; // ���ݿ������

$conn = mysql_connect ( $mysql_server_name, $mysql_username, $mysql_password );
mysql_select_db ( $mysql_database, $conn );
mysql_query ( "set names 'utf8'" );
?>