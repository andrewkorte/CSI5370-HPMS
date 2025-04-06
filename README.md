# CSI5370-HPMS
Project for CSI5370 Software Verification and Testing

## Software Stack (Need these to run project)
- Jetbrains Intellij
- Docker Desktop
- Postman
- Python 3.12 (see link to restler-fuzzer for link to get download link: https://github.com/microsoft/restler-fuzzer?tab=readme-ov-file)
- .NET 8 (see link to restler-fuzzer for link to get download link: https://github.com/microsoft/restler-fuzzer?tab=readme-ov-file)



## Creating Database image and Start it
- Start from root of project
- open docker and make sure there are no containers with name 'csi5370-final-db'
  - If there are delete them
- run <docker build -t csi5370-final-db .> command from command line (do not inclue <>)
- run <docker run -d --name csi5370-final-db -p 5432:5432 csi5370-final-db> from command line (do not include <>)


## Running HPMS
- Open at root of project in Intellij
- Open Docker Desktop
- In Command Prompt
  - Go to project root
  - Follow instructions for Creating DB Image above
  - You will need to restart the DB after running tests so that it always starts from initial state
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
- 
