# networkServiceMigrationTool
The application must be able to read configuration files from more devices of different models, then, produce the list of network services that each device is running.


To RUN : 

if you want to input device directory and result output directory and run BATCH process then enable following property
spring.batch.job.enabled = false

(Or) you can trigger a batch job by posting a JSON of below format to endpoint /job/migrateDevices

{
    "deviceConfigurationDirectory":"/var/tmp",
    "networkServicesSolutionDirectory":"/var/tmp",
    "fileName": "" 
}

fileName is optional, all CSV & JSON from mentioned deviceConfigurationDirectory will be picked automatically if its empty.


Assumptions : 

1) Expert user should be able to modify/adapted mapping rules. This migration tool needs to handle configuration files from more devices of different models, order of hundreds, and the number of models a few dozens. 

Drools Rule engine is used to adapt this dynamic nature based on models and during each run. 

2) Migration tool should be able to decide based on the result of the analysis which solution is best suitable.  

below are list of Assumptions done to chose one target solution. 

existing Customer network has several network topology containing several device configuration. To migrate our Customers migrating from their existing network services to a new product based on a new technology, we need to come up with a service model which maps each network device to a particular network service type.

D1 - S2
D2 - S3
D3 - S4 ...so on

each of these devices are capable of supporting higher bandwidth services as well. hence we can order network service hierarchy as below. 

S1 < S2 < S3 < S4 ...

Now optimised solution make sure that each of devices is mapped to a network service according to service model or higher . 




