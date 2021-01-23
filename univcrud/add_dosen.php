<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$nidn = $_POST['nidn'];
	$nama = $_POST['nama'];
	$tgl_lahir = $_POST['tanggal_lahir'];
	$photo = $_POST['photo'];

	$random = random_word(20);
	$path = "images/".$random.".png";
	$actualPath = "http://192.168.43.42/univcrud/$path";

	$query = "INSERT INTO tabel_dosen(id_dosen, nidn, nama, tanggal_lahir, photo) VALUES (NULL, '$nidn', '$nama', '$tgl_lahir', '$actualPath')";
	$sql = mysqli_query($connect, $query);

	if($sql){
		file_put_contents($path, base64_decode($photo));
		$response["success"] = true;
		$response["message"] = "Data berhasil ditambahkan.";
		echo json_encode($response);
	} else {
		$response["success"] = false;
		$response["message"] = "Gagal menambahkan data.";
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
