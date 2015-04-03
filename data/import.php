<?php
require_once("vendor/autoload.php");

use predictionio\EventClient;

$accessKey = 'IRHihGhwtY5iE4RiGcAjyn4e4I8hbk4fBdfSRUu0x2iWzObXMH5IGeu2kd4EcvjI';
$client = new EventClient($accessKey, 'http://localhost:7070');

$i = 1;
$handle = fopen("./train.txt", "r");
if ($handle) {
  while (($line = fgets($handle)) !== false) {
    $line = trim($line);
    $sentiment = substr($line, strlen($line)-1);
    $phrase = substr($line, 0, strlen($line)-2);

    $response = $client->createEvent(array(
      'event' => '$set',
      'entityType' => 'phrase',
      'entityId' => $i,
      'properties' => array(
        'phrase'=> $phrase,
        'sentiment'=> $sentiment
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
