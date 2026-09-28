# API Testing (Postman + Newman)

Representative API regression collection. Smoke and core-flow checks run before every
production release; collections are also executed headless with Newman in CI.

## Run locally

```bash
# 1. Import sample-api-tests.postman_collection.json into Postman, or run with Newman:
npm install -g newman
newman run sample-api-tests.postman_collection.json \
  --env-var baseUrl=https://staging.api.example.com \
    --env-var testPassword=***
    ```

    ## What is covered

    - **Auth:** login, token capture, reuse across requests
    - **Core resources:** list/create flows with status-code, schema, and response-time assertions
    - **Data integrity:** created objects are read back and verified against the request payload

    Assertions are written so failures are immediately actionable: status, contract, performance, and business-rule checks are separated.
