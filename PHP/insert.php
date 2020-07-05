<?php
    
    require_once 'database.php';
    
    $tableName = $_POST["table"];
    $data = $_POST["data"];
    $dataDecoded = json_decode($data,true);
    
    $db = database::getInstance();
    $return_insert = database::insert($tableName, $dataDecoded);
    
    if($return_insert === true) {
        $response["error"] = false;
        $response["message"] = 'Insert successfully';
        $response["id"] = database::lastId();
    }
    else {
        $response['error'] = true;
        $response['message'] = database::error_msg();
    }
    
     echo json_encode($response);

?>
