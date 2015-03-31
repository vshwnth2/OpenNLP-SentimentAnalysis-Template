<?php
require_once("vendor/autoload.php");

use predictionio\EventClient;

$accessKey = 'IRHihGhwtY5iE4RiGcAjyn4e4I8hbk4fBdfSRUu0x2iWzObXMH5IGeu2kd4EcvjI';
$client = new EventClient($accessKey, 'http://localhost:7070');

$i = 1;
$handle = fopen("./dev.txt", "r");
if ($handle) {
  while (($line = fgets($handle)) !== false) {
    $response = $client->createEvent(array(
      'event' => '$set',
      'entityType' => 'tree',
      'entityId' => $i,
      'properties' => array(
        'tree'=>rtrim($line),
      )
    ));
    print_r($response);
    $i++;
  }
  fclose($handle);
} else {
  print "Could not open file";
} 

?>
