# Ne cassez plus vos consommateurs grâce au contract testing

Livrer un (micro) service pour s'apercevoir que l’on a cassé l’environnement, c’est agaçant.

Dans des architectures de plus en plus orientées (micro) services, et les S.I. complexes, vérifier qu’une nouvelle version de votre service continuera à pouvoir communiquer avec les autres devient de plus en plus important. 

Les tests de contrats proposent une solution pour vérifier les interactions lors de la phase de test. 
Dans cet atelier, nous présenterons comment mettre en place le test de contrat, en particulier guidé par le consommateur de l’API, au sein de votre architecture entre le front, vos backends aussi bien par appel HTTP ou messages. 

Nous utiliserons Spring Cloud Contract, très bien intégré aux applications Spring Boot, ainsi que Pact ayant l’avantage d’être polyglotte. 

## Todo

- Définir les ojectifs de l'atelier
- Définir le scénario de l'atelier
- Découper le scénario en étapes
- Coder les différentes étapes
- Créer un repository git avec une branche par étape
- Faire des slides pour présenter le Contract testing et l'atelier
 

 ## Lab

Discover Contract DSL
https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/getting-started.html#getting-started-introducing-spring-cloud-contract

https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/complex.html




 - Add maven plugin  
 - Add first contract like GET /v1/books
 - Add base contract class to make the tests pass
 - run _spring-cloud-contract:generateTests_ and look at _target/generated-test-sources_/.../ContractVerifierTest.java
 - You can run them directly as a typical java test class
 - Adding [static contract with groovy DSL](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl)
 - You can test only the relevant fields
 - Using [dynamic and regex properties](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl-dynamic-properties)
 - understand the _$(consumer/stub/client(...), producer/test/server(...))_
 - You can also [reference the parameters from the request](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl-referencing-request-from-response)

 - mvn install  for making stub accessible for consumers
 - using Contract WireMock stub on the consumer "Checkout" side [see AutoConfigureStubRunner](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#features-stub-runner-retrieving)
 - fixing missing config to make WireMock request pass with stub runner 
 - Add configuration in yml for stub runner to avoid duplication
 - Add a contract (and baseclass) for /v1/checkouts
 - Adding a contract for a message which is created in method [see Output Triggered by a Method](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl-output-triggered-method)

 
 - mvn install  for making stub accessible for consumer
 - Complete test in delivery service to [trigger stub message](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#features-messaging-consumer)


## Part 2

Init a local distant repo:
- git init --bare test-repo.git
- git clone test-repo.git/ test-clone
- touch README.md
- git add .
- git commit -m "add README"
- git push origin master

Run the spring-cloud-contract convert and generateStubs then
Add maven configuration to push to repo
                <configuration>
                    <contractsMode>REMOTE</contractsMode>
                    <contractsRepositoryUrl>
                        git://file:///Users/ygrenzinger/git/devoxx-contracts
                    </contractsRepositoryUrl>
                    ...
                </configuration>
Run the spring-cloud-contract:pushStubsToScm in maven inside the inventory project
Use stubs mode remote and add repository root




 