<?php
$response = array();
$sv = "localhost";
$user = "man_united";
$pass = "Schneiderlin_28";
$db = "OSAHubEventManagement";
$con = mysql_connect($sv, $user, $pass) or die(mysql_error());
$db = mysql_select_db($db) or die(mysql_error());

if (isset($_POST['course'])) {
	$course = $_POST['course'];

	if ($course == 1) {

		$c_id = $_POST['c_id'];
		$name = $_POST['name'];
		$description = $_POST['description'];
		$category = $_POST['category'];
		$currency = (int)$_POST['currency'];
		$fees = (float)$_POST['fees'];
		$duration = (int)$_POST['duration'];
		$thumb_img = $_POST['thumb_img'];
		$full_img = $_POST['full_img'];
		$ref_bonus = (bool)$_POST['ref_bonus'];


		$sql = "SELECT * FROM users WHERE `c_id`= '$c_id'";
		$res = mysql_query($sql);
		$count = mysql_num_rows($res);
		if ($count == 0) {
			error_log($asd . " main time", 0);

			$query = "INSERT INTO  `courses` (  `COURSE_ID` ,  `COURSE_NAME` ,  `COURSE_DESCRIPTION` ,  `COURSE_CATEGORY` ,  `COURSE_CURRENCY` , `COURSE_FEES` ,  `COURSE_DURATION` ,  `COURSE_THUMBNAIL_PATH` ,  `COURSE_FULL_IMAGE_PATH` ,  `COURSE_REFERRAL_BONUS` )
            VALUES ('$c_id',  '$name',  '$description',  '$category', $currency, $fees, $duration, '$thumb_img', '$full_img', '$ref_bonus')";
			$result = mysql_query($query);

			// echo $result;
			// echo "Done";

			error_log($result . " main function " . $fix, 0);

			$response["success"] = 1;
			$response["message"] = "Course Added Successfully !!";
			echo json_encode($response);
		} else {
			$response["success"] = 0;
			$response["message"] = "Course already exists";
			echo json_encode($response);
		}

	}
} else {

	    // echo "Problem";
	    $response["success"] = 0;
	    $response["message"] = "Could Not Add Course !!";
	    echo json_encode($response);
}

?>