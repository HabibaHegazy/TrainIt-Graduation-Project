<?php
    
    require_once 'database.php';
    
    $tableName = $_POST["table"];
    $data = $_POST["conditions"];
    $dataDecoded = json_decode($data,true);

    $db = database::getInstance();
    $result = database::select($tableName, $dataDecoded, null);
    
    if($result != null) {
        $response["error"] = false;
        $response["message"] = 'Select successfully';
        $response[$tableName] = $result;
    }
    else {
        $response['error'] = true;   
         if(database::error_msg() == "Error description: "){
            $response['message'] = 'No Data Avaliable';
        }else{
            $response['message'] = database::error_msg();
        }
    }

    echo json_encode($response);

?>
