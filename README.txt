	This is my attempt to capture and in a way, fulfill the requirements of The Individual Project, handed for the CodingBootcamp by AFDemp.
	The final form/structure of the project is somewhat far away from how I intended it to be. I almost clearly steer away from the OOP path i should have taken to implement the requirements of the project, but i guess once the CB is over, I should revisit this project and all the previous projects done for the CB. However, here is a layout of what my app does (in a way)...

Consider it to be a messaging type app. It's based on the use of the Java programming language and of a MySQL Database, consisting of two (2) tables (users, messages).

The users are categorized by their respective role. The roles are as shown bellow:
1. User: Has the ability to:
	a. Send (Create) a message.
	b. View (Read) his Inbox or Sent messages.
	c. Edit (Update) a message from his Inbox or Sent messages.
2. Gold User: Has the ability to:
	a. Send (Create) a message.
	b. View (Read) his Inbox or Sent messages.
	c. Edit (Update) a message from his Inbox or Sent messages.
	d. Delete messages from his Inbox or Sent messages.
3. Admin (super admin): Has the ability to:
	a. Send (Create) a message.
	b. View (Read) his Inbox or Sent messages.
	c. Create a New user (also assigns a role to the newly created user).
	d. Edit existing Users.
	e. Delete existing Users.
	
Last but not least, i attempted to "monitor" the user interaction by creating "log".txt files.