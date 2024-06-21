<?php
$item_tier_rarity = [1, 2, 3, 4, 5]; // 1 = common, 5 = legendary
$vip_rank = ['v1', 'v2', 'v3', 'v4', 'v5']; // v1 = lowest rank

function roll_item($vipRank, $item_tier_rarity, $vip_rank) {
    $vip_index = array_search($vipRank, $vip_rank);
    $weights = [];

    // Adjust weights based on VIP rank
    // VIP 1 = [10, 10, 1, 1, 1] & VIP 2 = [10, 10, 10, 1, 1] & VIP 3 = [10, 10, 10, 10, 1] & ...
    foreach ($item_tier_rarity as $index => $item) {
        if ($index <= $vip_index + 1) {
            $weights[] = 10; // Higher weight for items within VIP's range
        } else {
            $weights[] = 1; // Lower weight for items outside VIP's range
        }
    }

    $totalWeight = array_sum($weights);
    $rand = mt_rand(1, $totalWeight); // Find random number between 1 and total weight

    // Calculate which item to select
    foreach ($item_tier_rarity as $index => $item) {
        // Minus the random number (rand) with weight index (10 or 1), until 0 or negative, the item is chosen
        $rand -= $weights[$index]; 
        if ($rand <= 0) {
            return $item;
        }
    }
    return end($item_tier_rarity); // Default return the rarest item
}

function test($item_tier_rarity, $vip_rank) {
    $distributions = [];
    foreach ($vip_rank as $rank) {
        $distributions[$rank] = array_fill_keys($item_tier_rarity, 0);
    }

    foreach ($vip_rank as $rank) {
        for ($i = 0; $i < 100; $i++) {
            $item = roll_item($rank, $item_tier_rarity, $vip_rank);
            $distributions[$rank][$item]++;
        }
    }

    foreach ($distributions as $rank => $distribution) {
        echo "VIP ($rank):<br>";
        foreach ($distribution as $item => $count) {
            echo "Item $item: $count times<br>";
        }
        echo "<br>";
    }
}

// Test for all rank
//test($item_tier_rarity, $vip_rank);

// Specific vip ranking according to the user
$test_vip = ['v1', 'v2', 'v3'];
test($item_tier_rarity, $test_vip);
?>
