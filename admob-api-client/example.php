<?php

// Step 1: Fill in this information below
$client_key = 'Your AdMob client key';
$email = 'Your AdMob login email';
$password = 'Your AdMob login password';
$site_id_array[] = 'Site id 1';
$site_id_array[] = 'Site id 2';
// Step 2: Execute the php file!

require_once 'AdMobApiClient.php';

try {
  $api = new AdMobApiClient($client_key);
  $api->login($email, $password);

  $start_date = gmdate('Y-m-d', time()-7*24*60*60); // 7 days ago
  $end_date = gmdate('Y-m-d'); // today

  $params = array(
    'site_id' => $site_id_array,
    'start_date' => $start_date,
    'end_date' => $end_date,
    'time_dimension' => AdMobApiClient::DIMENSION_DAY,
    'object_dimension' => AdMobApiClient::OBJECT_SITE
  );

  $data = $api->getData('site', 'stats', $params, false);

  foreach ($data as $row) {
    echo "Site {$row['site_id']} had \${$row['revenue']} of revenue for {$row['date']}\n";
  }
} catch (AdMobApiException $e) {
  echo $e->getMessage();
}
