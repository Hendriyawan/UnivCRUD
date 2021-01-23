<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$username = $_POST['username'];
	$password = $_POST['password'];
	$level = $_POST['level'];

	$query = "INSERT INTO tabel_user(id_user, username, password, level) VALUES (NULL, '$username', '$password', '$level')";
	if(mysqli_query($connect, $query)){
		$response["success"] = true;
		$response["message"] = "Register Berhasil !";
		echo json_encode($response);
		
	} else {
		$response["success"] = false;
		$response["message"] = "Register Gagal !";
		echo json_encode($response);
	}

	mysqli_close($connect);
}
?>
