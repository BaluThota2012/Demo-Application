# Demo-Application
Demo Application is a Android native application to enrol and display users. There are two swipeable pages in this application. First page shows the list of saved users and the second page is to add users.Both the pages are scrollable.



#First Page

  Contains a recycler view which has a list of users that were already added to the database. The details of the users are retrived from the firebase realtime-database and profile photo is taken from the storage.
  The users are added in the descending order i.e. last added user is shown at the top.
  
  
#Second Page
  
    Contains text fields for user to enter details like firstname, secondname, date of birth, gender,country,state,home town, phone number and telephone number. It aslo contains a imageview to upload profile photo from the device.
  Each field is validated by using regular expressions.
  This details are atomatically added to the database and are shown in the users tab once the user is successfully added.
   
