## CashApp Stocks Take Home Project by Sam Christiansen

### Project Review
- The project took about 5 hours for me to complete.
- I used MVVM for my architecture, to keep the code logic clean and separated. One step that I skipped / thought was unnecessary for this project was to have a repository level that would cache service responses locally, so recalling the same repository function could grab the locally cached values instead of calling the API again.
- The project prompt mentioned things such as "ensure that you feel comfortable modifying, maintaining, and extending this code." and "Show Us Your Strengths", so I used a BaseViewModel that maybe is more complex than needed for this simple project, but it is what I feel most comfortable with.
- To fulfill the requirement to handle the malformed json API response, I used a BaseService that simply has a try/catch block, and returns null on an error. This try / catch was a shortcut, and adding something like an ApiResult object that could be a Success or Fail with additional information could provide better opportunities for error handling.
- While doing the UI in the StocksScreen, I added logic to get the dollar amount and the date object right there in the composable. This was a short cut, as that logic should be in the view model.
- I kept the UI fairly simple. I can follow design files from figma to make more complex views, but since there was no design requirement, I kept it simple.

### Libraries Added
The added dependencies are at the top of the app's build.gradle.kts dependency section:
- NavController
    - androidx.navigation:navigation-compose
- Retrofit & Moshi
    - com.squareup.moshi:moshi-kotlin
    - com.squareup.retrofit2:converter-moshi
- Hilt
    - com.google.dagger:hilt-android
    - com.google.dagger:hilt-android-compiler
    - com.google.dagger:hilt-android-gradle-plugin
    - androidx.hilt:hilt-navigation-compose
- Mockito
    - org.mockito:mockito-core
    - com.nhaarman.mockitokotlin2:mockito-kotlin
    - org.jetbrains.kotlinx:kotlinx-coroutines-test

### How to Run the Project
- Open the project in Android Studios
- Run the project with the "app" run configuration selected
- Tap on any of the 3 options from the dashboard
- Tap the device's "back" button to get back to the dashboard
- Feel free to tap into and back out of each option
### How to Run the Unit Tests
- Open the project in Android Studios
- In the project directory, right click the "tests" directory under "CashAppStocksProject/app/src" and click on "Run 'Test in CashAppSto...'" option.
