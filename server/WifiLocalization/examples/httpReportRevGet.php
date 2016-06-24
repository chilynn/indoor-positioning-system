<?php
  include_once '../jpush/JPushClient.php';
  
  $master_secret = '7ff6f9d119d9eade68be0485';
  $app_key='4a2df7d2105a4bd84d93e436';
  $platform = '';
  $apnsProduction = false;

  //echo phpinfo();
  $client = new JPushClient($app_key,$master_secret);
  //$msg_ids = '1613113584';
  $msg_ids = $_GET['msg_ids'];
  //echo $msg_ids;
  $revResult = $client->getReportReceiveds($msg_ids);
  //echo $revResult->getResultStr();
  $msgstr = "";
  if($revResult->isOK())
  {
      $msgstr = $revResult->getResultStr();  
  }
  echo $msgstr;

?>
