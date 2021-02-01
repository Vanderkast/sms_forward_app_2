## SMS Forward Application
### The Android application that is about reading sms history and sending it to external service.

This project has grown from my job's internal needs.

### Main logic is:  
-> User input (like a phone number, date, etc.)  
--> Reading and handling sms history by entered parameters (filtering by date, specific formatting, etc.)  
---> Sending result to external service (email service, remote server)

### Current supported sending features:
1. Email via gmail
2. Email via gmail with .csv file
3. Server via http post with authorization and content-type _plain/text_

### R(D)IY  "run (do) it yourself"

1. Clone git repository
2. Create `class Settings` with `public static final fields`:  
   2.1. `EMAIL_ADDRESS` - gmail account that will be used to send sms history  
   2.2. `EMAIL_PASSWORD` - password from set email address  
   2.3. `SERVER_ADDRESS` - address that should receive sms history  
   2.4. `AUTHORIZATION` - value for `Authorization` http header  
3. Create file `app/src/main/res/xml/network_security_config.xml` with following content:  
`<?xml version="1.0" encoding="utf-8"?>
   <network-security-config>  
        <domain-config cleartextTrafficPermitted="true">  
            <domain includeSubdomains="true">YOUR_SERVER_ADDRESS</domain>  
        </domain-config>  
   </network-security-config>`  
