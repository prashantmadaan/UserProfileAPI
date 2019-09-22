var express = require('express'),
  app = express(),
  port = process.env.PORT || 3000,
  mongoose = require('mongoose'),
  Task = require('./api/models/userProfileAPIModels'), //created model loading here
  bodyParser = require('body-parser');

  app.use(bodyParser.json({
   limit: "50mb"
  }));
  app.use(bodyParser.urlencoded({
    limit: "50mb",
    extended: true,
    parameterLimit: 50000
  }));

// mongoose instance connection url connection


mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost/UserProfiledb');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function callback() {
  console.log("Sucessfully connected to Db");
});

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var routes = require('./api/routes/userProfileAPIRoutes'); //importing route
routes(app); //register the route


app.listen(port);


console.log('todo list RESTful API server started on: ' + port);

app.use(function(req, res) {
  res.status(404).send({url: req.originalUrl + ' not found'})
});
