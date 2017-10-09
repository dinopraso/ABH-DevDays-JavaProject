import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),
  actions: {
    authenticate() {
      this.get('ajax').post('/login', {
        xhrFields: {
          withCredentials: true,
        },
        data: {
          email: this.get('email'),
          password: this.get('password'),
        },
      })
      .then(
        (user) =>  this.transitionToRoute(user.isAdmin ? 'admin' : 'index'),
        (error) => {
          this.set('hasError', true);
          this.set('errorMessage', error.errors[0].title);
        }
      );
    },
  },
});
