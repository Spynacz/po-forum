CREATE TABLE user (
	userID integer NOT NULL PRIMARY KEY,
	username text NOT NULL UNIQUE,
	password text NOT NULL,
	post_count integer NOT NULL
);


CREATE TABLE thread (
	threadID integer NOT NULL PRIMARY KEY,
	title text NOT NULL,
	date integer NOT NULL,
	userID integer NULL,
	FOREIGN KEY(userID) REFERENCES user(userID)
);

CREATE TABLE post (
	postID integer NOT NULL PRIMARY KEY,
	body text NOT NULL,
	date integer NOT NULL,
	threadID integer NOT NULL,
	userID integer NULL,
	FOREIGN KEY(threadID) REFERENCES thread(threadID),
	FOREIGN KEY(userID) REFERENCES user(userID)
);

CREATE INDEX threadCreatorIndex on thread(threadID, userID);
CREATE INDEX postThreadDateIndex on post(threadID, date);

CREATE TABLE rank (
	name text NOT NULL PRIMARY KEY
);

CREATE TABLE user_rank (
	userID integer NOT NULL,
	rank text NOT NULL,
	FOREIGN KEY(userID) REFERENCES user(userID),
	FOREIGN KEY(rank) REFERENCES rank(name)
);
