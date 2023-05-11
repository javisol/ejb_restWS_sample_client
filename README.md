# EJB Client Sample Application

## Test

### Singleton
```bash
curl -v openliberty01:9080/ejb-appclient/singleton
curl -v openliberty01:9080/ejb-appclient/singleton2
```

### Stateful
```bash
curl openliberty01:9080/ejb-appclient/stateful
```

### Entity
#### Read
```bash
curl -v openliberty01:9080/ejb-app/pets?id=1
```
#### Write
```bash
curl -v -XPOST openliberty01:9080/ejb-appclient/ejbappdb
```
