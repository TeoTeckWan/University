<?php

function checkDiscount($purchaseValue) {
    if ($purchaseValue < 100) {
        return "Purchase Value is $purchaseValue, there are no discount.";
    } else if ($purchaseValue < 500) {
        return "Purchase Value is $purchaseValue, discount is 5%";
    } else {
        return "Purchase Value is $purchaseValue, discount is 10%";
    }
}

echo checkDiscount(300) . "<br>"; 
echo checkDiscount(80);  
?>
