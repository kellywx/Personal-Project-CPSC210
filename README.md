# My Personal Project

## Project Description

My project will be a **shopping wishlist application** in which users can create a list of items they want to buy or receive as gifts. Users can also create to-buy lists and add items they want to buy for others. Additionally, they can enter an amount of money they have and keep track of how much money they have left after buying certain items. Target users include anyone who often keeps track of items they are interested in and enjoy gift shopping for their family and friends. I am interested in developing this idea because it is an application that would be very helpful and convenient for myself to use. I often make note of items to buy for my friends so an application designed specifically for this purpose would be useful.

## User Stories
- As a user, I want to be able to add a shopping item to my wishlist
- As a user, I want to be able to view the item, brand, and price of each shopping item in my wishlist
- As a user, I want to be able to add and remove shopping items in my wishlist
- As a user, I want to be able to create to-buy lists for my friends and family
- As a user, I want to be able to enter the amount of money I have to spend
- As a user, I want to be able to keep track of how much money I have left after buying certain items
- As a user, I want to be able to save my wishlist, friend list, and wallet to file
- As a user, when I start the application, I want to be given the option to load my wishlist, friend list, and wallet from file

## Phase 4: Task 2
- Tue Nov 26 13:14:01 PST 2024
Added wish: Laptop from Apple ($2500.0)
- Tue Nov 26 13:14:14 PST 2024
Added wish: Shirt from Lululemon ($40.99)
- Tue Nov 26 13:14:19 PST 2024
Added 50.0 to wallet
- Tue Nov 26 13:14:21 PST 2024
Spent 40.99
- Tue Nov 26 13:14:29 PST 2024
Added New Friend: Rachel
- Tue Nov 26 13:14:32 PST 2024
Selected Friend: Rachel
- Tue Nov 26 13:14:47 PST 2024
Added wish: Shoes from Bloomingdale ($50.0)

## Phase 4: Task 3
If I had more time to further improve my project, I would refactor and introduce a List interface. Currently, both my WishList class and FriendList class have many of the same operations. These include, adding, removing, and finding wish items or friends. The specific code in these methods are similar and employ most of the same logic. Without an interface, the code can be redundant as both WishList and FriendList are lists being populated with Wish objects or Friend objects. To solve this repetition, I would introduce a List interface that can extract the common methods (add, remove, find) and have a getter method to return the list of items. That way, this would improve the overall design structure of my project by ensuring that there is no redundant code and making the UML diagram more concise. 