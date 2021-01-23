<?php
include "connection.php";
if($_SERVER['REQUEST_METHOD'] == 'GET'){

	$result = array();

	if(isset($_GET['nidn'])){
		$nidn = $_GET['nidn'];
		$query = "SELECT * FROM tabel_dosen WHERE nidn LIKE '%".$nidn."%'";
	} else {
		$query = "SELECT * FROM tabel_dosen ORDER BY nama ASC";
	}

	$sql = mysqli_query($connect, $query);
	while($row = mysqli_fetch_array($sql)){
		array_push($result, array(
			'id_dosen' => $row[0],
			'nidn' => $row[1],
			'nama' => $row[2],
			'tanggal' => $row[3],
			'photo' => $row[4]
		));
	}

	$response["success"] = true;
	$response["data"] = $result;
	echo json_encode($response);
	mysqli_close($connect);
}
?>
