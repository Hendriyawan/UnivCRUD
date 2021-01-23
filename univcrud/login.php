<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$username = $_POST['username'];
	$password = $_POST['password'];

	$query = "SELECT * FROM tabel_user WHERE username='$username' AND password='$password'";
	$login = mysqli_query($connect, $query);
	$validate = mysqli_num_rows($login);

	if($validate > 0){
		while($row = mysqli_fetch_object($login)){
			$response["success"] = true;
			$response["message"] = "Berhasil login";
			$response["username"] = $username;
			$response["level"] = $row->level;
			echo json_encode($response);
		}
	} else {
		$response["success"] = false;
		$response["message"] = "Gagal login";
		echo json_encode($response);
	}
	mysqli_close($connect);
}
?>

