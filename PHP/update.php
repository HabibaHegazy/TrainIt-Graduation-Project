<?php
    
    require_once 'database.php';

    $tableName = $_POST["table"];
    
    $data = $_POST["data"];
    $dataDecoded = json_decode($data,true);

    $conditions = $_POST["conditions"];
    $conditionsDecoded = json_decode($conditions,true);

    

    $db = database::getInstance();
    $return_update = database::update($tableName, $dataDecoded, $conditionsDecoded);
    
    if($return_update === true) {
        $response["error"] = false;
        $response["message"] = 'Update successfully';
    }
    else {
        $response['error'] = true;
        $response['message'] = database::error_msg();
    }
    
     echo json_encode($response);

?>
