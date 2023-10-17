# CogniDAN
**An Android app to manage and conduct cognitive testing for scuba divers created for Divers Alert Network (DAN).**

## Features
### Cognitive Tests 
- Both tasks were created with custom UI components
- Available to practice in practice section of app (does not create trials in database)
- Real trials collect data and store in database
- Screen backpresses and rotation are not allowed during real tests to avoid complications while underwater.

#### N-Back Task - a test for working memory
 - Participants are shown a series of blue squares, each shown for a few seconds. Participants must decide if they saw a given square n-back trials ago (where n = 1,2,3).
 - Algorithm for generating trials with 1-back, 2-back and 3-back, with an identical target-foil ratio and without lures.
 - Collect data on reaction time and click categorization (Hit, Miss, False-Alarm, Correct-Rejection)
 - User feedback for correct and incorrect clicks
   
![NBACK](https://github.com/TaxiKab119/cognidan/assets/115945374/c32c138e-7c19-4ae0-9e4a-527ae1539d19)


#### Balloon Analogue Risk Task - a test for risk taking
- Participants are shown a series of balloons, each with a max inflation randomly selected between 0-19. Participants do not know the max inflation for any balloon. With each balloon inflation, 1$ is added to the reward for a balloon. Participants are instructed to collect as much money as possible without popping the balloons.
- Collect data on user inflations and if balloon popped.
  
![BART](https://github.com/TaxiKab119/cognidan/assets/115945374/abcda7fd-cfe2-4e28-bac3-3bf09785a0fc)


### Participant Management - Add, Modify and Delete Participants
- Participant database stores participants and their associated trial data
#### Add Participants
![AddParticipant](https://github.com/TaxiKab119/cognidan/assets/115945374/8c84ac29-a8fa-49d1-91da-3578a3515a0f)

#### Modify Participants
![ModifyParticipant](https://github.com/TaxiKab119/cognidan/assets/115945374/cbe3f51a-a121-4a6e-ab5b-e65937a64de4)

#### Delete Participants
![DeleteParticipant](https://github.com/TaxiKab119/cognidan/assets/115945374/99a6f36d-01cc-42cc-8384-bc03ac37adf5)


### Trial Data Management and Export
- Users can view BART and N-BACK data for a participant, select a desired number to export or export all
- Export launches Google Sheets allowing them to inspect data in .csv format and save as desired.

#### View and Manage Participant Trial Data
![ExportParticipant](https://github.com/TaxiKab119/cognidan/assets/115945374/9790a5d8-5592-4c71-a56a-46679b670269)

#### Trial Creation
![TrialDetails](https://github.com/TaxiKab119/cognidan/assets/115945374/8e0b4167-71ce-4ba2-9a39-68b087ce550a)

## Technologies, Libraries, Tools
 - Kotlin
 - Jetpack Compose 
 - Fragment Based Navigation
 - Room (SQLite)
 - Coroutines, Flows, UDF Development Pattern
 - Custom UI Components (Canvas)
 - Object Oriented Programming

## Overall Flow Diagram
![FlowDiagram](https://github.com/TaxiKab119/cognidan/assets/115945374/6630c510-0f0d-4ae3-8869-5487b82edcf6)
