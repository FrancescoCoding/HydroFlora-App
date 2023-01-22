# HydroFlora🪴
The HydroFlora app is designed to help you track your plants' water intake. It's as easy as taking a picture of your house plant and selecting its type along with how often it should be watered.

![image](https://user-images.githubusercontent.com/64712227/205894827-770b28c0-e245-4b0e-8c24-219d9b388f7f.png)


## Built in Java using Android Studio and utilizing native Android features

# License
The software is released under the MIT License, which means that it is free to use, distribute, and modify. The only requirement is that the original copyright notice and license must be included in any copies or derivatives of the software. This means that you are free to use the software for any purpose, including commercial use, and can distribute and make changes to the code as long as you credit the original authors. However, the software is provided "as is" and the authors are not liable for any damages arising from the use of the software.

# 1. App Plan 📝
**Ideated and developed by**: Gruosso Francesco

**Title and promo**: HydroFlora – Track your plant’s water intake!  

**Description**: The app allows a user to simply snap a picture of their plant and add a watering cycle so they are able to keep track of when it needs to be watered. Find a list of included features below:  
-	Six interlinked pages (three without loading and splash pages)
-	The use of various widgets to obtain inputs from the users:
    - Customised EditText fields to obtain each plant's information and days for each water cycle
    - DatePickerDialog to obtain the first watering date
    - Camera intent to allow the user to snap a picture of their plant
- The use of various widgets to display information to the user
    - Customised labels and components
    - Beautiful animations to give the user proper feedback while waiting
    - User feedback and validation of the input fields
- The use of a RecyclerView to display the plants' schedule, sorted by closest watering date
- The use of the various layout files to structure the components
- Accessible navigation through the pages
- The use of a ROOM SQLite data store for an offline-first approach
- The use of Camera and Notification services

# 2. App Design 🔬
## [Original Figma design link](https://www.figma.com/file/EgFoqFF19Md7IV1kzpgN6p/Plant?node-id=0%3A1&t=NeTlweMbLUGeV23p-1)
 
 I generated the app icon with the use of [DALL·E 2](https://openai.com/dall-e-2/), and further edited it to be more in theme to my app with external tools:  
 ![image](https://user-images.githubusercontent.com/64712227/205891829-7221c81b-a28e-419a-ae02-d825315a830b.png)

## App Overview:
### Navigation Graph:
![image](https://user-images.githubusercontent.com/64712227/205894208-397f1ca6-5369-40b6-b2ca-7b2eb4fc9c9d.png)

- Landing Page:
    - The Landing Page is the first page the user encounters when opening the app. It is meant to be a simple and friendly explanation about what the app wants to accomplish. It features a big action button, which sends the user directly to the core view of the app: the Schedule.
    - All of its methods are implemented in the onCreateView method, since it makes it easier to work with the view.
    - Implemented with a LinearLayout.
- Plant Schedule:
    - Since there are no plants yet, I decided to show a modern linear animation, with a classic circularly enclosed add button.
    - This page will be filled out with the Plant entities, which are added from the Options view to a Room local Database, to be ultimately displayed here in a RecyclerView.
    -  When a plant has to be watered today and the user opens the schedule, they will be notified about it.
    -  ![image](https://user-images.githubusercontent.com/64712227/205901295-bb3e612b-e8ef-4599-977e-b19dc2acab7c.png)
    -  Implemented with a LinearLayout.
- Options (add a plant):
    - The options fragment is the only view that takes user inputs. This page allows a user to take a picture of their plants, select a date through the Calendar Picker, and insert all the needed information in the database through the PlantRepository class.
    - For the camera images to be stored, they were converted with a TypeConverter to ByteArray, and then to Base64.
    - ![image](https://user-images.githubusercontent.com/64712227/205900951-0cfe850b-9e32-456e-a6b6-e36fe18c84c9.png)
    -  Implemented with a LinearLayout.
    -  Now that the Plants exist to be displayed in the RecyclerView, they can also be opened to be seen in detail or be deleted from the database.
- Plant Info:
    - This is where all the Plant information is displayed. It shows the image previously snapped with the camera, its name, type, and watering info.
    - This page also allows the user to delete their plant, or go back to the schedule.
- Plant added/removed views:
    - These fragments are animations for user feedback, so that they know the app is not breaking while they wait for the operations to complete. They do not show on the Navigation image.

# 3. Reflective Statement 🔊
I have had a lot of fun creating the logic for this app, working with XML Layouts as opposed to CSS has had its challenges, but I am satisfied with the overall result of my work. The app is currently only missing the implementation of the HTTP Multipart form-data request to send the images as files to the API, which is already working in my Postman environment.

![image](https://user-images.githubusercontent.com/64712227/205905933-2e017361-11a6-470a-86f2-a2312aedd59e.png)
