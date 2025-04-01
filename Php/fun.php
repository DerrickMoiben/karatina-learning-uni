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

$languages = array("kim" => "Python", "isa" => "javascript", "davis" => "php");

echo "Kim is good in ".$languages["Kim"]."<br/>";
?>