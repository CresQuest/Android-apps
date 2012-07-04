<?php

require_once 'AdMobApiException.php';

class AdMobApiClient
{
  const CLIENT_VERSION = 'AdMob-PHP/v2.20100304';
  const CURL_TIMEOUT = 10;
  const DIMENSION_DAY = 'day';
  const DIMENSION_WEEK = 'week';
  const DIMENSION_MONTH = 'month';
  const OBJECT_SITE = 'site';

  private $client_key;
  private $timeout;
  private $token;

  /**
   * @param string $client_key
   * @param int $timeout The maximum number of seconds to allow cURL functions to execute.
   * @param boolean $retry_rate_limited_requests True to have the client retry requests that were rate limited (recommended).
   */
  public function __construct($client_key, $timeout = self::CURL_TIMEOUT, $retry_rate_limited_requests = true)
  {
    $this->client_key = $client_key;
    $this->timeout = $timeout;
    $this->retry_rate_limited_requests = $retry_rate_limited_requests;
  }

  public function __destruct()
  {
    $this->logout();
  }

  public function login($email, $password)
  {
    $params = array(
      'email' => $email,
      'password' => $password
    );

    $data = $this->getData('auth', 'login', $params, true, true);
    if (isset($data['token'])) {
      $this->token = $data['token'];
    }
  }

  public function logout()
  {
    if (isset($this->token)) {
      $params = array('token' => $this->token);
      $this->getData('auth', 'logout', $params, true);
    }
  }

  /**
   * This function will return ALL pages of data for the associated API request.
   * This function will retry a request that was rate limited by the API.
   * This function will throw and AdMobApiException if an unrecoverable error occurs.
   * @param string $object
   * @param string $method
   * @param array $params
   * @param boolean $post
   * @param boolean $https
   */
  public function getData($object, $method, $params, $post, $https = false)
  {
    $response = $this->getResponse($object, $method, $params, $post, $https);
    $data = $response['data'];

    for ($i=2; $i <= $response['page']['total']; $i++) {
      $params['page'] = $i;
      $response = $this->getResponse($object, $method, $params, $post, $https);
      $data = array_merge($data, $response['data']);
    }

    return $data;
  }

  protected function getUrl($object, $method, $https)
  {
    $protocol = $https ? 'https' : 'http';
    return "$protocol://api.admob.com/v2/$object/$method";
  }

  protected function getPort($https)
  {
    return $https ? '443' : '80';
  }

  /**
   * This function will return the API response object.
   * The consumer of this function is responsible for handling pagination.
   * This function will retry a request that was rate limited by the API.
   * This function will throw and AdMobApiException if an unrecoverable error occurs.
   * @param string $object
   * @param string $method
   * @param array $params
   * @param boolean $post
   * @param boolean $https
   */
  protected function getResponse($object, $method, $params, $post, $https)
  {
    foreach ($params as $k => $v) {
      if ($v === null) {
        unset($params[$k]);
      }
    }

    $params['client_key'] = $this->client_key;
    $params['format'] = 'json';

    if (isset($this->token)) {
      $params['token'] = $this->token;
    }

    $url = $this->getUrl($object, $method, $https);
    $data = http_build_query($params);

    $curl = curl_init();
    if ($post) {
      curl_setopt($curl, CURLOPT_POST, true);
      curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
    } else {
      $url = $url . "?" . $data;
    }

    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_PORT, $this->getPort($https));
    curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
    curl_setopt($curl, CURLOPT_USERAGENT, self::CLIENT_VERSION);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

    do {
      $retry = false;
      $json = curl_exec($curl);
      $errno = curl_errno($curl);

      if ($errno !== 0) {
        $e = new AdMobApiException("Curl error $errno");
        $e->setApiUrl($url);
        $e->setApiParameters($params);
        throw $e;
      }

      $response = json_decode($json, true);

      if (empty($response)) {
        $e = new AdMobApiException("Unable to json decode API response [RESPONSE $json]");
        $e->setApiUrl($url);
        $e->setApiParameters($params);
        throw $e;
      }

      foreach ($response['errors'] as $error) {
        if ($this->retry_rate_limited_requests && $error['code'] == 'rate_limit_exceeded') {
          sleep(1);
          $retry = true;
        } else {
          $e = new AdMobApiException("Error occured during AdMob API request");
          $e->setApiUrl($url);
          $e->setApiParameters($params);
          $e->setApiResponse($response);
          throw $e;
        }
      }
    } while ($retry);

    curl_close($curl);
    return $response;
  }
}
