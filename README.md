# EJB Client Sample Application

## Test

### Singleton
```bash
curl -v localhost:9080/ejb-appclient/singleton
curl -v localhost:9080/ejb-appclient/singleton2
```

### Stateful
```bash
curl localhost:9080/ejb-appclient/stateful
```

### Entity
#### Read
```bash
curl -v localhost:9080/ejb-app/pets?id=1
```
#### Write
```bash
curl -v -XPOST localhost:9080/ejb-appclient/ejbappdb
```
