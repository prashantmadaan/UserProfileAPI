'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var SignUpSchema = new Schema({
  first_name:{
     type:String,
     required: 'Enter your First Name'
   },
  last_name:{
     type:String,
     required:'Enter your Last Name'
   },
  password:{
     type:Password,
     required:true,
     minlength:7
   },
  confirm_password:{
    type:Password,
    required:true,
    minlength:7
  },
  email_address: {
    type:String,
    required:true,
    unique:true,
    validate(value){
      if(!validator.isEmail(value)){                        // npm install validator
        throw new Error('Email is invalid')
      }
    }
  }
});

var UserProfileSchema = new Schema({
  user_id: {
    type: String,
    required: true
  },
  first_name: {
    type: String,
    required: true
  },
  last_name: {
     type: String,
     required: true
   },
  age: {
    type: Number,
    default:0,
    validate(value){
      if(value < 0){
        throw new Error('Age must be a positive number')
      }
    }
  },
  weight: {
    type: Decimal,
    default: 0
  },
  address: {
    type: String
  }
});

var signup = mongoose.model('signup', SignUpSchema);
var user = mongoose.model('user',UserProfileSchema);

module.exports = signup;
module.exports = user;
