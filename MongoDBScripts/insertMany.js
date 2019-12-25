use test;

db.createCollection("messages");

db.messages.insertMany([
  {
    "sender": "paul",
	"receiver": "george",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-12-21T18:40:49Z")
  },
  {
    "sender": "paul",
	"receiver": "sylvie",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-12-20T18:40:49Z")
  }
  {
    "sender": "george",
	"receiver": "valerie",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-11-18T18:40:49Z")
  },
  {
    "sender": "valerie",
	"receiver": "paul",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-11-15T18:40:49Z")
  }
  {
    "sender": "paul",
	"receiver": "george",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-05-12T18:40:49Z")
  },
  {
    "sender": "paul",
	"receiver": "sylvie",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-04-20T18:40:49Z")
  }
  {
    "sender": "george",
	"receiver": "valerie",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2019-01-15T18:40:49Z")
  },
  {
    "sender": "valerie",
	"receiver": "paul",
	"subject": "test",
	"content": "content",
	"sentDate": ISODate("2018-12-31T18:40:49Z")
  }
]);
db.messages.find().pretty();
