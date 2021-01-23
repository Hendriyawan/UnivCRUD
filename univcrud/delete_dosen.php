<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$id = $_POST['id_dosen'];
	$query = "DELETE FROM tabel_dosen WHERE id_dosen='$id'";
	$sql = mysqli_query($connect, $query);
	if($sql){
		$response["success"] = true;
		$response["message"] = "Data berhasil dihapus !";
		echo json_encode($response);
	} else {
		$response["success"] = false;
		$response["message"] = "Gagal menghapus data !";
		echo json_encode($response);
	}
	mysqli_close($connect);
}
?>
