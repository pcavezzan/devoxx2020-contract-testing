package contracts

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    request {
        method POST()
        url '/v1/books'
        headers {
            contentType(applicationJson())
        }
        body(
            "name": "Clean code",
            "price": 49.99,
            "stock": 45,
        )
    }
    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body(
                "id": $(anyUuid()),
                "name": "Clean code",
                "price": 49.99,
                "stock": 45,
        )
    }
}