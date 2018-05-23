# Required software

This project requires Java 8+ and Gradle 4.0+

# Building and running

Type `gradle bootRun` in the powershell, while in the project directory, with gradle installed

# Required configuration

You must have a `configuration.properties` file set up with values for `user.agent`, `username`, and `password`.

 `user.agent` is your reddit api user.agent.

 `username` is the email you will be using to send text messages with.

 You must have a `phonenumbers.json` set up in this format:

 `{
    "name": "Listing",
    "phoneNumbers": [
      {
        "number": "1234567",
        "carrier": "TMOBILE"
      },
      {
        "number": "1234567@gmail.com",
        "carrier": "NONE"
      }
    ]
  }`