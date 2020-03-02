# Ne cassez plus vos consommateurs grâce au contract testing

Livrer un (micro) service pour s'apercevoir que l’on a cassé l’environnement, c’est agaçant.

Dans des architectures de plus en plus orientées (micro) services, et les S.I. complexes, vérifier qu’une nouvelle version de votre service continuera à pouvoir communiquer avec les autres devient de plus en plus important. 

Les tests de contrats proposent une solution pour vérifier les interactions lors de la phase de test. 
Dans cet atelier, nous présenterons comment mettre en place le test de contrat, en particulier guidé par le consommateur de l’API, au sein de votre architecture entre le front, vos backends aussi bien par appel HTTP ou messages. 

Nous utiliserons Spring Cloud Contract, très bien intégré aux applications Spring Boot, ainsi que Pact ayant l’avantage d’être polyglotte. 


 ## Lab

 ### Part 1

You can read a bit the [introduction to Spring Cloud Contract](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/getting-started.html#getting-started-introducing-spring-cloud-contract
)

In inventory project:
- The maven plugin [doc here](https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/) is already added in POM.xml spring-cloud-contract-maven-plugin
- You have to configure the location base contract class available here `com.devoxx.inventory.contracts.ContractsBase` in the maven plugin by adding the location of in configuration `<baseClassForTests>` (see here)[https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/junit.html]. This base class create configure ...
- run `mvn clean test` to see the plugin generate tests in this class `_target/generated-test-sources_/.../ContractVerifierTest.java`. You can run it as a typical java test class.
- The contract `shoudRetrieveAllBooks` should pass now. To better understand, the [Groovy DSL documentation is here](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl)
- Uncomment the test `shoudRetrieveBook`. It fails due to wrong url in the controller. You can easily correct it. You can also see the use of [dynamic and regex properties](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl-dynamic-properties)
-  **important** : understand the _$(consumer/stub/client(...), producer/test/server(...))_
- _info_ : You can test only the relevant fields
- _info_ : use the _generateTests_ goal to avoid running everything.
- _info_ : For very complex use case, you can also [reference the parameters from the request](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl-referencing-request-from-response)
- Uncomment the test `shoudCreateBookIntoInventory` and make it test the create POST endpoint. The problem here is the random UUID. You can either control the UUID generator in the test or use matchers.
- Create the contract `shoudReduceStockInInventory` and make it test the reduce stock POST endpoint. You also have to uncomment it to show some ATDD practices :)
- Do mvn install on inventory project for making stub accessible for consumers


In checkout project:
 - using Contract WireMock stub on the consumer "Checkout" side [see AutoConfigureStubRunner](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#features-stub-runner-retrieving)
 - fixing missing config to make WireMock request pass with stub runner 
 - Add configuration in yml for stub runner to avoid duplication
 - Add a contract (and baseclass) for /v1/checkouts
 - Adding a contract for a message which is created in method [see Output Triggered by a Method](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#contract-dsl-output-triggered-method)
 - mvn install  for making stub accessible for consumer


In delivery project:
 - Complete test in delivery service to [trigger stub message](https://cloud.spring.io/spring-cloud-static/spring-cloud-contract/2.2.1.RELEASE/reference/html/project-features.html#features-messaging-consumer)


### Part 2

Init a local distant repo:
- git init --bare test-repo.git
- git clone test-repo.git/ test-clone
- touch README.md
- git add .
- git commit -m "add README"
- git push origin master

Run the spring-cloud-contract convert and generateStubs then
Add maven configuration to push to repo
            <plugin>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-contract-maven-plugin</artifactId>
                <version>2.2.1.RELEASE</version>
                <extensions>true</extensions>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <!-- By default we will not push the stubs back to SCM,
                            you have to explicitly add it as a goal -->
                            <goal>pushStubsToScm</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <contractsMode>LOCAL</contractsMode>
                    <contractsRepositoryUrl>git://file:///Users/ygrenzinger/git/contract.git</contractsRepositoryUrl>
                    <contractDependency>
                        <groupId>${project.groupId}</groupId>
                        <artifactId>${project.artifactId}</artifactId>
                        <version>${project.version}</version>
                    </contractDependency>
                    <testFramework>JUNIT5</testFramework>
                    <baseClassForTests>com.devoxx.checkout.contracts.ContractsBase</baseClassForTests>
                </configuration>
            </plugin>
Run the spring-cloud-contract:pushStubsToScm in maven inside the inventory project
Use stubs mode remote and add repository root


https://github.com/spring-cloud-samples/spring-cloud-contract-samples/blob/master/producer_with_git/pom.xml#L98
https://cloud-samples.spring.io/spring-cloud-contract-samples/workshops.html#_setup


 