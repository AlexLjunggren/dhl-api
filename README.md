## DHL API

**Note:** Register for API key here: https://developer.fedex.com/api/en-us/home.html

## Instantiate ##

```java
DhlApi hdlApi = new DhlApi(environment, String apiKey);
```

Environment (PRODUCTION, SANDBOX)

```java
DhlEnvironment environment = DhlEnvironment.SANDBOX;
```

## Track Package(s) ##

Request

```java
TrackingResponse response = dhlApi.track(trackingNumbers);
```

## Throttling

1 call per second, 250 calls per day