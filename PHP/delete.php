<?php
    
    require_once 'database.php';
    
    $table = $_POST["table"];
    $conditions = $_POST["conditions"];
    $conditionsDecoded = json_decode($conditions,true);
    
    $db = database::getInstance();
    $return_delete = database::delete($table, $conditionsDecoded);
    
    if($return_delete === true) {
        $response["error"] = false;
        $response["message"] = 'Delete successfully';
    }
    else {
        $response['error'] = true;
        $response['message'] = database::error_msg();
    }
    
     echo json_encode($response);

?>
