<?php
    
    require_once 'database.php';
    
    $tableName = $_POST["table"];
    $receivedData = $_POST["conditions"];
    $new_array = json_decode($receivedData,true);
        
    $db = database::getInstance();
    
    $query = "";
    
    if($new_array["choice"] == "daily"){
        $playerID = $new_array["playerID"];
        $query = "SELECT performanceRate, DAYOFWEEK(tb_Report.date) as report FROM " . $tableName . " WHERE WEEKOFYEAR(date)=WEEKOFYEAR(NOW()) and playerID = '" . $playerID . "' ORDER BY DAYOFWEEK(NOW())";
    }
    elseif($new_array["choice"] == "weekly"){
        $playerID = $new_array["playerID"];
        $query = "SELECT (SUM(performanceRate) / COUNT(performanceRate)) as performanceRate, WEEK(tb_Report.date) as report FROM " . $tableName . " where playerID = '". $playerID ."' GROUP BY Month(NOW())";
    }
    elseif($new_array["choice"] == "monthly"){
        $playerID = $new_array["playerID"];
        $query = "SELECT (SUM(performanceRate) / COUNT(performanceRate)) as performanceRate, Month(date) as report FROM " . $tableName . " where playerID = '". $playerID ."' GROUP BY Month(tb_Report.date)";
    }
    elseif($new_array["choice"] == "comaprisonYearly"){
        //$query = "SELECT SUM(performanceRate) / COUNT(performanceRate) as performanceRate, Year(tb_Report.date) as report FROM " . $tableName . " where and playerID = '" . $playerID . "' GROUP BY Year(tb_Report.date)";

        $where_sentence = " WHERE ";
        
        $i = 0;
        foreach ($new_array as $key => $value) {
            
            if($key == "playerID".$i)
            {
                $where_sentence .= "tb_Report.playerID = " . $value . " OR ";
                $i = $i + 1;
            }
            
        }

        $where_sentence = substr($where_sentence, 0, -3);

        $query = "SELECT SUM(performanceRate)/COUNT(performanceRate) as performanceRate, tb_Report.playerID as report FROM " . $tableName . $where_sentence . " GROUP BY Year(tb_Report.playerID)";
    }
        
    $result = database::query($query);
    
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