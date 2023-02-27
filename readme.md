# ReadMe
This is a simple Java application that allows users to convert currencies from one denomination to another. The app uses real-time currency exchange rates to perform the conversions.
## How to use the app
To use the app, simply download the source code and compile it using a Java compiler. You can do it by running the project in a Java IDE like Eclipse or NetBeans or in a command line using the javac command. To do it in command line you need to navigate to the directory where the project is located ($YourDirectory$/Remitly) and run the following command:

    mvn clean install

After the compilation is done, you can run the app by running the following command:

    mvn exec:java

Please make sure that you have Maven installed on your machine. If you don't have Maven installed, you can download it from [here](https://maven.apache.org/download.cgi) or use a command:

    sudo apt-get install maven

When the app is running you will see two text fields and two lists. You can navigate through the lists to select the currency you want to convert from and to. After you select the currencies, you can enter the amount you want to convert in the first text field. The second text field will be automatically filled with the converted amount. You can also change the amount in the second text field and the first text field will be automatically filled with the converted amount. The app will also show you the exchange rate that was used to perform the conversion.

## API used
The app uses the NBP API to get the exchange rates. The API is available [here](http://api.nbp.pl/). Every time the app is started, it downloads the currencies available for conversion from the API. The app also downloads the exchange rates for the selected currencies every time the user changes the currencies and uses them to calculate the conversion.
