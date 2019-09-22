'use strict';


var mongoose = require('mongoose'),
Login = mongoose.model('login'),
Users = mongose.model('user');
bodyParser = require('body-parser');


const jwt= require('jsonwebtoken')

exports.create_a_user = function(req, res) {
  if (!req.body.email || !req.body.password) {
      res.status(400).send({
      message: "Invalid parameters"
    });
    }

    email_req=req.body.email;
    password_req=req.body.password;
    Login.find({
          {email : email_req}
      },function(error,comments){
        console.log(error);
       console.log("========comments==========", comments);
       if (error) {
          first_name_req=req.body.firstName;
          last_name_req=req.body.lastName;
          LoginUser={
            "":,
            "":,
          }


     }else{
       res.status(400).send({
         message: "Error occured while fetching from user schema"
     });
     }





};


exports.signin_user = function(req, res) {

  var new_login = new Login(req.body);
  new_task.save(function(err, task) {
    if (err)
      res.send(err);

    res.json(task);
  });
};


exports.fetch_user_profile = function(req, res) {
  Task.findById(req.params.taskId, function(err, task) {
    if (err)
      res.send(err);
    res.json(task);
  });
};


exports.update_user_profile = function(req, res) {
  Task.findOneAndUpdate({_id: req.params.taskId}, req.body, {new: true}, function(err, task) {
    if (err)
      res.send(err);
    res.json(task);
  });
};
