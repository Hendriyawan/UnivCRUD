<?php
include 'connection.php';
if($_SERVER['REQUEST_METHOD'] == 'GET'){

	$result = array();

	if(isset($_GET['nidn'])){
		$nidn = $_GET['nidn'];
		$query = "SELECT * FROM tabel_mahasiswa WHERE nidn LIKE '%".$nidn."%'";
	} else {
		$query = "SELECT * FROM tabel_mahasiswa ORDER BY nama ASC";
	}

	$sql = mysqli_query($connect, $query);
	while($row = mysqli_fetch_array($sql)){
		array_push($result, array(
			'id_mahasiswa' => $row[0],
			'npm' => $row[1],
			'nama' => $row[2],
			'jenis_kelamin' => $row[3],
			'nodn' => $row[4],
			'photo' => $row[5]
		));
	}

	$response["success"] = true;
	$response["data"] = $result;
	echo json_encode($response);
	mysqli_close($connect);
	
}
?>
