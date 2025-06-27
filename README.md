# Weight Tracker Android Application

- Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

The Weight Tracker app was designed to help users track their weight over time.
This app is tailored towards the serious athlete with a strict weight regiment, the health-concious individual with underlying medical conditions, 
and the casual user looking for an easy and convenient way to track their weight.

- What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

In order to create a user-centric user interface, several screens were required. There needed to be login and register screens allowing current
and new users to sign up to the app. Additionally, there needed to be a screen which showed the user's current weight, target weight, and weight
entries over time. I made sure to use intuitive and easy-to-understand iconography for buttons and inputs. I also tried to keep
the number of elements displayed on a page to a minimum in order to reduce cognitive load and overall busyness.
Overall, I believe I was successfull in designing and app with an acceptable and approachable UI/UX.

- How did you approach the process of coding your app? What techniques or strategies did you use? How could those techniques or strategies be applied in the future?

While coding the application, I tried to break down each unit of funcitonality into managable chunks. After implementing and testing
one portion of the software, I then moved on to the next one with the assurance each component was tested individually.
I will probably use the same approach in future projects. I think this is a reasonable way to create software.

- How did you test to ensure your code was functional? Why is this process important, and what did it reveal?

For each unit of functionality, I tested it in isolation to ensure it was working. For example, when creating the register feature,
I wrote all the logic to create the tables and the back-end interactions between the UI, activity, and database. After doing this,
I spun up the device emulator in Android Studio and used the application monitor feature to query the SQLite database
as I interacted with the register screen. This enabled me to debug and iterate, ultimaletly leading to a working component.
This iterative process was paramount in ensuring each piece of functionality worked in isolation before adding other features.

- Consider the full app design and development process from initial planning to finalization. Where did you have to innovate to overcome a challenge?

While developing the application, there was one particular piece of functionality that I found challenging. I needed to figure out how to handle
updating a weight entry vs. creating a new one. Since I was using the same activity class, I needed a way to optionally pass in a weight entry id
if the operation was an update. After some research, I learned there is a feature which allows you to attach additional attributes 
to Intents before passing them to the startActivity() function.
I leveraged this in order to pass in an id if the operation was an update instead of a create.

- In what specific component of your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

In my opinion, the main screen, the weight grid, was the feature which I felt encompassed all of my skills and experience.
It features several different UI components, many of which are interactive. Although I have no previous mobile application
development experience, I felt I was able to tap into my web development knowledge and back-end skills to successfully
complete the feature. Overall, I'm satisfied with the result, although I would like to enhance the styling some more.
