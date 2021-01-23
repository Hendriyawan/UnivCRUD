<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$id_dosen = $_POST['id_dosen'];
	$nidn = $_POST['nidn'];
	$nama = $_POST['nama'];
	$tgl_lahir = $_POST['tanggal_lahir'];
	$photo = $_POST['photo'];

	$random = random_word(20);
	$path = "images/".$random.".png";
	$actualPath = "http://192.168.43.42/univcrud/$path";

	$query = "UPDATE tabel_dosen SET nidn='$nidn', nama='$nama', tanggal_lahir='$tgl_lahir', photo='$actualPath' WHERE id_dosen='$id_dosen'";
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
