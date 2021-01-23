<?php
include "connection.php";
if($_SERVER['REQUEST_METHOD'] == 'GET'){
        $query = "SELECT * FROM tabel_dosen ORDER BY nama ASC";
        $sql = mysqli_query($connect, $query);


        $result = array();

        while($row = mysqli_fetch_array($sql)){
                array_push($result, array(
                        'nidn'=>$row[1]));
        }
        $response["success"] = true;
        $response["data"] = $result;
        echo json_encode($response);
        mysqli_close($connect);
}
?>

