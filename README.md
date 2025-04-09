# CSI5370-HPMS
Project for CSI5370 Software Verification and Testing

# Please Be Advised: There are bugs in the code that are meant to be there. This will cause some unit tests to fail. Please comment them out to run tests successfully

## Software Stack (Need these to run project)
- Jetbrains Intellij
- Docker Desktop
- PgAdmin
  - If you would like to query the DB directly
- Postman
- Python 3.12 (see link to restler-fuzzer for link to get download link: https://github.com/microsoft/restler-fuzzer?tab=readme-ov-file)
- .NET 8 (see link to restler-fuzzer for link to get download link: https://github.com/microsoft/restler-fuzzer?tab=readme-ov-file)

# You will need to clone the repo from GitHub https://github.com/andrewkorte/CSI5370-HPMS


## Creating Database image and Start it
- Start from root of project
- open docker and make sure there are no containers with name 'csi5370-final-db'
  - If there are delete them
- run <docker build -t csi5370-final-db .> command from command line (do not inclue <>, make sure to include period at the end)
- run <docker run -d --name csi5370-final-db -p 5432:5432 csi5370-final-db> from command line (do not include <>)
- To connect in PgAdmin
  - Right click servers
  - select register -> server
  - In general tab give any name
  - In connection tab
    - Hostname/address -> localhost
    - Port -> 5432
    - Username -> csi5370
    - Password -> mypassword


## Running HPMS
- Open at root of project in Intellij
- Open Docker Desktop
- In Command Prompt
  - Go to project root
  - Follow instructions for Creating DB Image above
  - You will need to delete and restart the DB container after running tests so that it always starts from initial state
  - You can do this by opening docker desktop going to contains and deleting
- Go to src/main/java/com/CSI5370/HomePurchaseManagementSystem/HomePurchaseManagementSystemApplication.java
- Click play button next to "public class HomePurchaseMan..."
  - API will now be running at "http://localhost:8080"
- To test to make sure it is running
  - Open Postman and import the "Real Estate API.postman_collection.json" and "HPMSEnv.postman_environment.json" from the Postman folder
  - In the upper right hand corner select the HPMSEnv environment
  - Go to the Collections page and select a test from the collection and click send
- To see Spring generated swagger.json
  - In a browser go to "http://localhost:8080/v3/api-docs"


## Running RESTler
- Follow above instructions to start project
- Open Command Prompt
- Go to restler-fuzzer-main folder from project in command prompt
- For Compile step
  - <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\restler_bin\restler\Restler.exe compile --api_spec <FULL_PATH_TO_PROJECT_ROOT>\swagger.json
  - ex) C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\restler_bin\restler\Restler.exe compile --api_spec C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\swagger.json
  - This creates a folder in restler-fuzzer-main called Compile
- For Test Step
  - <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\restler_bin\restler\restler.exe test --grammar_file <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\grammar.py --dictionary_file <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\dict.json --settings <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\engine_settings.json --no_ssl
  - ex) C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\restler_bin\restler\restler.exe test --grammar_file C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\grammar.py --dictionary_file C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\dict.json --settings C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\engine_settings.json --no_ssl
  - Creates folder in restler-fuzzer-main called Test
- For Fuzz-Lean Command (After Running make sure to delete docker conatiner and remake it so data is reset as well as restart project)
  - <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\restler_bin\restler\restler.exe fuzz-lean --grammar_file <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\grammar.py --dictionary_file <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\dict.json --settings <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\engine_settings.json --no_ssl
  - ex) C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\restler_bin\restler\restler.exe fuzz-lean --grammar_file C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\grammar.py --dictionary_file C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\dict.json --settings C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\engine_settings.json --no_ssl
  - Creates folder in restler-fuzzer-main called FuzzLean
- For Fuzz Command (After Running make sure to delete docker conatiner and remake it so data is reset as well as restart project)
  - <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\restler_bin\restler\restler.exe fuzz --grammar_file <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\grammar.py --dictionary_file <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\dict.json --settings <FULL_PATH_TO_RESTLER_FUZZER_MAIN>\Compile\engine_settings.json --no_ssl --time_budget <PUT NUM HERE 1 = 1hr>
  - Bellow example will attempt run for 1hr (It will stop early if it finds no new information)
  - ex) C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\restler_bin\restler\restler.exe fuzz --grammar_file C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\grammar.py --dictionary_file C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\dict.json --settings C:\Users\Andrew\Desktop\School\CSI5370\CSI5370-HPMS\restler-fuzzer-main\Compile\engine_settings.json --no_ssl --time_budget 1
  - Creates folder in restler-fuzzer-main called Fuzz
