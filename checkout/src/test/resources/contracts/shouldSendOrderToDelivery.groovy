package contracts

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    // Human readable description
    description 'Should send order to delivery'
    // input to the contract
    input {
        // the contract will be triggered by a method
        triggeredBy('sendOrder()')
    }
    // output message of the contract
    outputMessage {
        // destination to which the output message will be sent
        sentTo('output')
        // the body of the output message
        body('''{ "uuid" : "d4d37e73-77a0-4616-8bd2-5ed983d45d14", "quantity": 2, "clientId": "yannick" }''')
        // the headers of the output message
        headers {
            header('BOOK-NAME', 'foo')
        }
    }
}