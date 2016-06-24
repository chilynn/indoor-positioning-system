<?php
include_once 'HttpPostClient.php';
include_once 'SecretEncode.php';
include_once 'ReceivedVO.php';
/**
 * 鍙戦�閫昏緫
 * @author xinxin
 *
 */
class ReportRecevied
{	
	// receive address
	private $RECEIVE_API_URL = "https://report.jpush.cn/v2/received";

	/**
	 * 鑾峰彇娑堟伅鎺ユ敹鏁伴噺淇℃伅
	 * @param unknown $receivedVO
	 * 澶囨敞锛氬彂閫侀噰鐢╤ttps闇�淇敼php.ini鎵撳紑extension=php_openssl.dll
	 * 
	 */
	public function getReceivedData($receivedVO, $revResult)
	{
	    //echo "1111";
		//鍔犲瘑瀛楃涓�		$secretEncode = new SecretEncode();
		
		$authStr = $secretEncode->getBase64Encode($receivedVO->getAuthStr());
		$receivedVO->setAuth($authStr);
		
		//url
		$url = $this->RECEIVE_API_URL."?".$receivedVO->getParams();
		
		$header = 'Authorization: Basic '.$receivedVO->getAuth();
		
		//璇锋眰澶翠俊鎭�		
		$context = array(
				'http' => array(
						'method' => 'GET',
						'header' => $header)
		);
		$stream_context = stream_context_create($context);
		//echo $stream_context;
		$httpPostClient = new HttpPostClient();
		$code = 200;
		try
		{
		    $rs = $httpPostClient->request_tools($url, $stream_context);
            //echo $rs;		
		}
		catch(Exception $e)
		{	
		    echo $e;
		    $code =404;
		}
		//echo $rs["body"];
		$revResult->setResultStr($rs, $code);
		//echo $revResult->getResultStr();
		return $revResult;
	}

}
?>