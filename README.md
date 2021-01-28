- Test automation done using selenium and java

- Test cases executed on firefox and chrome in parallel. Did not have other test browsers

#### It's a maven testng project. Test cases can be run using "test.xml".

To execute, Right click test.xml file and Run

There are 3 class-
- LoginTests -  To validate login related scenarios
- PrepareCart - For cart management (addition and deletion of items on inventory page)
- BasketTests - To validate scenarios on Basket page (removing items and clearance of cart)

- I have used PageObject model with page factory to manage my scripts
