Pinky
============
[![CircleCI](https://circleci.com/gh/AdrianGlasnapp/pinky.svg?style=svg)](https://circleci.com/gh/AdrianGlasnapp/pinky) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=AdrianGlasnapp_pinky&metric=ncloc)](https://sonarcloud.io/dashboard?id=AdrianGlasnapp_pinky) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AdrianGlasnapp_pinky&metric=coverage)](https://sonarcloud.io/dashboard?id=AdrianGlasnapp_pinky)  

Pinky helps to identify a particular device by generating UUID based on hardware data.

Supported Suppliers
-----------------
By default, Pinky requires at least one ``IdSupplierFactory`` to properly generate UUID, otherwise Pinky will throw  ``IllegalStateException`` during initialization. 
### Serial Number
**Required permission**: *android.permission.READ_PHONE_STATE*  
**Availability:**: Smarphone, Tablet   
**Reliability:**: Not guaranteed to be defined  
**Factory**: ``SerialNumberSupplierFactory ``

Since Android 2.3 Gingerbread devices without telephony services must report unique device ID which is obtainable via android.os.Build.SERIAL. Phones having telephony services can also define a serial number, but it is not guaranteed to have it defined.

### Secure ANDROID_ID
**Required permission**: *None*  
**Availability:**: Smarphone, Tablet  
**Reliability:**: May change after factory reset  
**Factory**: ``AndroidIdSupplierFactory ``

Secure ANDROID_ID is randomly generated on a device first boot. It is 64-bit number available for both smartphones and tablets. It may change if a factory reset is performed on the device.

### Unique telephony ID
**Required permission**: *android.permission.READ_PHONE_STATE*  
**Availability:**: Smarphone  
**Reliability:**: Survives factory reset  
**Factory**: ``PhoneIdSupplierFactory ``

This ID generation is based on IMEI or MEID. Value survives factory resets on devices.

Custom Supplier
---------------
If you wish to use custom factor for UUID generation you can easily create your own supplier. Create a class that implements the ``SupplierFactory`` interface and pass in an instance when building Pinky.

Usage
--------
``Pinky.Builder`` is class through which you can define device data factor which will be used for UUID generation. Example usage of all available suppliers:
```java
Pinky pinky = new Pinky.Builder()
                .salt(salt)
                .addIdSupplierFactory(AndroidIdSupplierFactory.create(context))
                .addIdSupplierFactory(PhoneIdSupplierFactory.create(context))
                .addIdSupplierFactory(SerialNumberSupplierFactory.create())
                .build();
```
and to generate UUID:
```java
pinky.generate();
```
Pinky will then use all available IDs to generate UUID.  

Dependency
--------
TODO

License
-------

    Copyright 2019 Adrian Glasnapp

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


