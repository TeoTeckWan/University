<?php
function checkDownload($memberType) {
    $currentTime = time();

    // Initialize the session variables if not set
    if (!isset($_SESSION['lastDownloadTime'])) { // Last download
        $_SESSION['lastDownloadTime'] = 0;
    }
    if (!isset($_SESSION['downloadCount'])) { // Track download count
        $_SESSION['downloadCount'] = 0;
    }

    // Check member type
    if ($memberType == 'nonmember') {
        if ($currentTime - $_SESSION['lastDownloadTime'] < 5) {
            return "Too many downloads";
        }
        // Successful download
        $_SESSION['downloadCount']++;

    } elseif ($memberType == 'member') {
        if ($_SESSION['downloadCount'] >= 2 && $currentTime - $_SESSION['lastDownloadTime'] < 5) {
            return "Too many downloads";
        }
        $_SESSION['downloadCount']++;
    }

    // Update the last download time
    $_SESSION['lastDownloadTime'] = $currentTime;

    return "Your download is starting...";
}

// echo "Non-members Check: <br>";
// // Non-member check
// echo checkDownload('nonmember'); // At 00:00:00
// sleep(5);
// echo checkDownload('nonmember'); // At 00:00:03

echo "Members Check: <br>";
// Member check
echo checkDownload('member'); // At 00:00:00
sleep(3);
echo checkDownload('member'); // At 00:00:03
sleep(2);
echo checkDownload('member'); // At 00:00:05
?>
