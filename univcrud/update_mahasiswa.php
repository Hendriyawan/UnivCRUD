<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$id_mahasiswa = $_POST['id_mahasiswa'];
	$npm = $_POST['npm'];
	$nama = $_POST['nama'];
	$jenis_kelamin = $_POST['jenis_kelamin'];
 	$nidn = $_POST['nidn'];
	$photo = $_POST['photo'];

	$random = random_word(20);
	$path = "images/".$random.".png";
	$actualPath = "http://192.168.43.42/univcrud/$path";

	$query = "UPDATE tabel_mahasiswa SET npm='$npm', nama='$nama', jenis_kelamin='$jenis_kelamin', nidn='$nidn', photo='$actualPath' WHERE id_mahasiswa='$id_mahasiswa'";
	$sql = mysqli_query($connect, $query);

	if($sql){
		file_put_contents($path, base64_decode($photo));
		$response["success"] = true;
		$response["message"] = "Data berhasil diupdate !";
		echo json_encode($response);
	} else {
		$response["success"] = false;
		$response["message"] = "Data gagal diupdate !";
		echo json_encode($response);
	}

	mysqli_close($connect);
}

function random_word($id = 20){
	$pool = '1234567890abcdefghijklmnopqrstuvwxyz';
	$word = '';

	for ($i = 0; $i < $id; $i++){
		$word = substr($pool, mt_rand(0, strlen($pool) -1), 1);
	}
	return $word;
}
?>
