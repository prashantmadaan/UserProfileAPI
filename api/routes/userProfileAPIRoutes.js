'use strict';
module.exports = function(app) {
  var userAPI = require('../controllers/userProfileAPIController');

  // todoList Routes
  app.route('/signup')
    .post(userAPI.create_a_user);


  app.route('/signin')
    .post(userAPI.signin_user);


  app.route('/userprofile/')
    .get(userAPI.fetch_user_profile)
    .put(userAPI.update_user_profile);
};
