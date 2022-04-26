# Android App: Online Shopping

**Dorset College Dublin**  
**BSc in Science in Computing & Multimedia**  
**Mobile Applications 1 - BSC20921**  
**Stage 2, Semester 2**  
**Project**

**Lecturer name:** Saravanabalagi Ramachandran  
**Lecturer email:** saravanabalagi.ramachandran@faculty.dorset-college.ie

**Student Name:** Mateus Fonseca Campos  
**Student Number:** 24088  
**Student Email:** 24088@student.dorset-college.ie

**Submission date:** 8 May 2022

This repository contains an "Online Shopping" Android app developed for my Project at Dorset College BSc in Computing, Year 2, Semester 2.

## Part 1: Requirements Checklist

- [x] 1. Authentication
    - [x] 1.1. Allow User to Signup
    - [x] 1.2. Log In using username and password
    - [x] 1.3. Store userID once logged in to keep the user logged in (even after restarting the app)
- [x] 2. Product Listing
    - [x] 2.1. List Product Categories
    - [x] 2.2. On clicking a Category, list Products in that Category
    - [x] 2.3. On clicking a Product, show Product description, show buy button and controls to change quantity.
- [x] 3. Cart
    - [x] 3.1. Show cart summary
    - [x] 3.2. Show total amount
    - [x] 3.3. Purchase button to place an order, show order notification
- [x] 4. Show order history
    - [x] 4.1. List users orders
    - [x] 4.2. On clicking an Order, show Order details and Products ordered
    - [x] 4.3. On clicking a Product, take them to Product description page created for 3.3
- [x] 5. Show User details
    - [x] 5.1. Use the stored userID to show user details
    - [x] 5.2. Show a random circular profile image from https://thispersondoesnotexist.com/
    - [x] 5.3. Show Logout button, on click take back to Signup / Log In page (Restart should not auto login after logout)
- [x] 6. UI/Implementational Requirements
    - [x] 6.1. RecyclerView used for all Lists: Categories, Products, Orders
    - [x] 6.2. If logged in, attach authentication token to all requests until logout
    - [x] 6.3. Add a small "About this app" button in the profile page, that shows a page on click with your copyright details and credits
- [ ] 7. Bonus
    - [x] 7.1. ViewPager2 with bottom TabLayout for: Shop, Cart, Orders, Profile icons
    - [ ] 7.2. Show a map fragment based on the GPS co-ordinates in the user profile

## Part 2: Extra features implemented

- **1. [View Binding](https://developer.android.com/topic/libraries/view-binding)**  
- **2. [Data Binding](https://developer.android.com/topic/libraries/data-binding)**  
- **3. [Navigation](https://developer.android.com/guide/navigation)**  
- **4. [Fragments](https://developer.android.com/guide/fragments)**  
- **5. [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)**  
- **6. [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**  

## Part 3: Report

For the last assessment of this module, I was tasked with developing an app for an online store based on the JSON files served by the **[Fake Store API](https://fakestoreapi.com/)**. The core requirements of this project were a coming together of everything covered in the previous assignments: [About Me](https://github.com/mateusfonseca/mobileApp1_About_Me.git), [Movie Booking](https://github.com/mateusfonseca/mobileApp1_Movie_Booking.git) and [GitHub Repos](https://github.com/mateusfonseca/mobileApp1_GitHub_Repos.git).

Having, by now, a much clearer vision of the bigger picture of Android development than at the start of the module, I was able to further explore some of its deeper concepts (see [extra features implemented](#part-2-extra-features-implemented)), some of which were truly challenging (*"hello, data binding!?"*). Implementation of LiveData, ViewModel and Coroutines allow for a reliable handling of deferred data that depends on networking, something that I struggled with in previous works.

Once again, it must be said, the visual side of my app might feel somewhat disappointing, which I am well aware of. With the end of the semester approaching and a lot of work to do, I spent only the minimally necessary amount of time and effort on making the application aesthetically acceptable.

**P.S.: Kotlin is beautiful!** :heart:
**P.S.2: Have I already said that?!**

## Part 4: References

Conceptually, every line of code in this project was written based on official documentation:

- **[Android Developers Docs](https://developer.android.com/docs)**  
- **[Kotlin Docs](https://kotlinlang.org/docs/home.html)**  
- **[Material Design](https://material.io/)**  
- **[OkHttp](https://square.github.io/okhttp/)**  
- **[Retrofit](https://square.github.io/retrofit/)**  
- **[Coil](https://coil-kt.github.io/coil/)**  

Visits to our most beloved **[StackOverflow](https://stackoverflow.com/)** certainly happened, for insight and understanding.

This app fetches its data from the **[Fake Store API](https://fakestoreapi.com/)**.

## Part 5: Copyright Disclaimer

This project may feature content that is copyright protected. Please, keep in mind this is an student's project and has no commercial purpose whatsoever. Having said that, if you are the owner of any content featured here and would like for it to be removed, please, contact me and I will do so promptly.

Thank you very much,  
Mateus Campos.