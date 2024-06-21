<?php
function daysBetweenDates($date1, $date2) {
    $datetime1 = new DateTime($date1);
    $datetime2 = new DateTime($date2);

    // Calculate the difference between the dates & Get the number of days in the interval
    $days = $datetime1->diff($datetime2)->days;

    // Odd or Even?
    $oddEven = ($days % 2 == 0) ? "even" : "odd";

    return "The number of days between $date1 and $date2 is $days, which is $oddEven.";
}

echo "Month-Day-Year <br>";
echo daysBetweenDates('11/5/2023', '5/8/2024');
?>
