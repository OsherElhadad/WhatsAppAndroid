# WhatsAppAndroid

# Getting Started

* Make sure you have anroid studio installed on your computer.
* Start the server on URL - https://github.com/OsherElhadad/WhatsAppApiServerEx3
* Just clone this repository and you are ready to start! <br>
* Open the cloned project on your andorid studio and connect to firebase (Tools -> Firebase -> Connect to firebase with:
  gmail user- osheryossi1@gmail.com, password- 12123344)
In order to open the app, open the cloned project on your andorid studio and click play.



## Log-In Activity

* This is the home activity which allows you to log-in to the system and start chatting with your friends!
* In case you are not already signed, you can click the round button below the log-in button and sign-up.
* When successfully logged-in, you will be navigated to the contact list screen.

## Sign-Up Activity

* This activity allows you to create your account.
* The username, password and verfiy password fields are requierd in order to sign up and profile picture is optional. In case one of the mentioned is empty or invalid(will be explanied in a bit), the system won't allow you to sign-up.
* While typing in the input fields you will notice a dynamic message below the relavent field. In case the current input is invalid, a red border will aprear with a X sign and a message to indicate the specific problem with the input.
* You may also add profile picture by clicking on the "select photo" button. You can take a picture or choose an existing one from your gallery.

Fields requirement in-depth:
- Username must be one word with length greater or equals to 2, without '-' or '_' characters.
- Password must be one word with length greater or equals to 2 and contatin at least one number and one letter characters.
- Verified Password must match the password.

## Contact List Activity

* Once you logged-in, your contacts list will appear.
  As for the chat itself - In order to open a chat just click on the desired contact!
* On the right bottom corener, you will notice an add contact button that will ridrect you yo the Add Contact Activity upon clicking on it.
  
  
## Add Contact Activity
 
 * You can add a contact by filling the desigred contact username, nickname and server.
  
  
## Chat Activity
* You can send text messages by clicking on send button. You can't send an empty message.
