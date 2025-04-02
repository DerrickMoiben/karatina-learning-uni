<?php
// this is for loop in php
$a = 0;

for($a = 0; $a <= 20; $a++)
{
    echo "THe values are $a <br>";
}

// We are going to learn about array in php

$arrayofnames = array("Purity", "Chumba", "Moiben", "Kisumu", "Nairobi");
echo $arrayofnames[1];
echo "<br>";

// this is an associative array in php

$lang = array("kim" => "Python", "isa" => "javascript", "davis" => "php");

echo "Kim is good in ".$lang['kim']."<br/>";
echo "Davis is good in ".$lang['davis']."<br/>";


// This is multidemensional array down here

$marks = array
(
    array(1, "kim", 40000),
    array(2, "isa", 70000),
    array(3, "davis", 60000),
);

for ($row =  0; $row < 3; $row++) {
    for ($col = 0; $col < 3; $col++) {
        echo $marks[$row][$col]." ";
    }
    echo "<br/>";
}

?>
